package ve.gob.cendit.cenditlab.io.scpi;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScpiCommand3
{
    /*
     Pruebas de expresiones regulares
     https://regex101.com/
     http://myregexp.com/
     https://www.debuggex.com/
     http://myregexp.com/g
    */

    // TODO: Revisar regexp para extraer parametros y validar comando
    /*
    private static final String SCPI_COMMAND_WITH_ARGUMENTS_REGEX =
            "[^()\\[\\]]*(\\((?<varin>[^()\\[\\]]+)\\))+[^()\\[\\]]*(\\[(?<varout>[^\\[\\]]+)\\])?";
    */

    private static final String SCPI_COMMAND_INPUT_PLACEHOLDER_REGEX =
            "[^()\\[\\]]*(\\((?<varin>[^()\\[\\]]+)\\))+[^()\\[\\]]*";

    private static final String SCPI_COMMAND_OUTPUT_PLACEHOLDER_REGEX =
            "^[^\\[\\]]*(\\[(?<varout>[^\\[\\]]+)\\]){1}[^\\[\\]]*$";

    private static final String SCPI_COMMAND_INPUT_VARS_PATTERN =
            "^([^()]*(\\([^()\\[\\]]+\\))+[^()]*)+$";

    private static final String SCPI_COMMAND_OUTPUT_VARS_PATTERN =
            "[^\\[\\]]*(\\[[^\\[\\]]+\\]){1}";


    private static final String SCPI_COMMAND_OUTPUT_PLACEHOLDER_PATTERN =
            "(\\[[^\\[\\]]+\\]){1}";

    private static final String SCPI_COMMAND_INPUT_FORMAT =
            "(%s)";

    private String rawCommand;
    private String preparedCommand;

    private Set<String> inVariableNamesSet;
    private String outVariableName;
    private VariablesBundle variablesBundle;

    private boolean needUpdate;
    private boolean wellFormed;

    private VariableChangeListener changeListener =
            new VariableChangeListener()
            {
                @Override
                public void changed(Variable variable, Object oldValue, Object newValue)
                {
                    needUpdate = true;
                }
            };

    public ScpiCommand3(String command)
    {
        if (command == null)
        {
            // TODO: excepcion ante argumentos null
            throw new IllegalArgumentException("Arguments must not be null");
        }

        wellFormed = true;
        needUpdate = false;
        rawCommand = command;

        if (hasInputVariables(rawCommand))
        {
            /* TODO: Elegir la forma mas conveniente para almacenar
               los nombres de los argumentos
            */
            inVariableNamesSet = new HashSet<String>();

            extractInputVariableNames(rawCommand);
            wellFormed = false;
            needUpdate = true;
        }

        if (hasOutputVariable(rawCommand))
        {
            extractOutputVariableName(rawCommand);
            rawCommand = removeCommandOutputPlaceholder(rawCommand);
            wellFormed = false;
        }

        preparedCommand = rawCommand;
    }

    public ScpiCommand3(String command,
                        VariablesBundle bundle)
    {
        if (command == null || bundle == null)
        {
            // TODO: excepcion ante argumentos null
            throw new IllegalArgumentException("Arguments must not be null");
        }

        wellFormed = true;
        needUpdate = false;
        rawCommand = command;

        if (hasInputVariables(rawCommand))
        {
            inVariableNamesSet = new HashSet<String>();
            variablesBundle = bundle;

            extractInputVariableNames(rawCommand);
            enableInputVariablesChangeListener(true);
            wellFormed = checkInputVariablesAvailability();
            needUpdate = true;
        }

        if (hasOutputVariable(command))
        {
            extractOutputVariableName(rawCommand);
            rawCommand = removeCommandOutputPlaceholder(rawCommand);
        }

        preparedCommand = rawCommand;
    }

    private static boolean hasInputVariables(String command)
    {
        return command.matches(SCPI_COMMAND_INPUT_VARS_PATTERN);
        // return command.matches(SCPI_COMMAND_INPUT_PLACEHOLDER_REGEX);
    }

    private static boolean hasOutputVariable(String command)
    {
        return command.matches(SCPI_COMMAND_OUTPUT_VARS_PATTERN);
        // return command.matches(SCPI_COMMAND_OUTPUT_PLACEHOLDER_REGEX);
    }

    public boolean hasInputVariables()
    {
        return inVariableNamesSet != null;
    }

    public boolean hasOutputVariable()
    {
        return outVariableName != null;
    }

    public boolean isWellFormed()
    {
        return wellFormed;
    }

    public Set<String> getInputVariableNames()
    {
        return inVariableNamesSet;
    }

    public String getOutputVariableName()
    {
        return outVariableName;
    }

    public Variable getOutputVariable()
    {
        return variablesBundle.get(outVariableName);
    }

    private void extractInputVariableNames(String command)
    {
        // Pattern pattern = Pattern.compile(SCPI_COMMAND_WITH_ARGUMENTS_REGEX);
        Pattern pattern = Pattern.compile(SCPI_COMMAND_INPUT_PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(command);
        String variableName;

        // Extrae variables de entrada (argumentos del comando)
        while (matcher.find())
        {
            try
            {
                variableName = matcher.group("varin");
                if (variableName != null)
                {
                    inVariableNamesSet.add(variableName);
                }
            }
            catch (Exception ex)
            { }
        }
    }

    private void extractOutputVariableName(String command)
    {
        // Extrae variable de salida (unica variable de salida)
        Pattern pattern = Pattern.compile(SCPI_COMMAND_OUTPUT_PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(command);

        try
        {
            if (matcher.find())
            {
                outVariableName = matcher.group("varout");
            }
        }
        catch (Exception ex)
        { }
    }

    private boolean checkInputVariablesAvailability()
    {
        if (inVariableNamesSet == null || variablesBundle == null)
        {
           return false;
        }

        return inVariableNamesSet.stream()
                            .noneMatch(name -> variablesBundle.get(name) == null);
    }

    private void enableInputVariablesChangeListener(boolean enable)
    {
        if (inVariableNamesSet == null || variablesBundle == null)
        {
            return;
        }

        inVariableNamesSet.stream()
                .forEach(name ->
                {
                    Variable variable = variablesBundle.get(name);
                    if (variable == null) return;

                    if (enable)
                    {
                        variable.addChangeListener(changeListener);
                    }
                    else
                    {
                        variable.removeChangeListener(changeListener);
                    }
                });
    }


    private String replaceInputVariablesWithValues(VariablesBundle bundle)
    {
        if ( ! hasInputVariables() )
        {
            return preparedCommand;
        }

        if (bundle == null)
        {
            // TODO: unicamente debug
            throw new NullPointerException("bundle must not be null");
        }

        String command = rawCommand;

        for (String variableName : inVariableNamesSet)
        {
            String pattern = String.format(SCPI_COMMAND_INPUT_FORMAT, variableName);
            Variable variable = bundle.get(variableName);
            command = command.replace( pattern,
                    variable.getValue().toString() );
        }

        return command;
    }

    private String removeCommandOutputPlaceholder(String command)
    {
        return command.replaceFirst(SCPI_COMMAND_OUTPUT_PLACEHOLDER_PATTERN, "");
    }

    public String getCommand()
    {
        if (variablesBundle != null && hasInputVariables() && needUpdate)
        {
            preparedCommand =
                    replaceInputVariablesWithValues(variablesBundle);
            needUpdate = false;
        }

        return preparedCommand;
    }

    public void setVariables(VariablesBundle bundle)
    {
        if (bundle == null)
        {
            throw new IllegalArgumentException("bundle must not be null");
        }

        needUpdate = false;
        wellFormed = true;

        if (hasInputVariables())
        {
            enableInputVariablesChangeListener(false);
            variablesBundle = bundle;
            enableInputVariablesChangeListener(true);

            needUpdate = true;
            wellFormed = checkInputVariablesAvailability();
        }
    }

    public VariablesBundle getVariables()
    {
        return variablesBundle;
    }

    public String applyVariables(VariablesBundle bundle)
    {
        if (bundle == null)
        {
            throw new IllegalArgumentException("bundle must not be null");
        }

        return replaceInputVariablesWithValues(bundle);
    }
}

