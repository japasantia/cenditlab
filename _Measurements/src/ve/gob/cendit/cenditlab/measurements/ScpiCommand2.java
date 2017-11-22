package ve.gob.cendit.cenditlab.measurements;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScpiCommand2
{
    /*
     Pruebas de expresiones regulares
     https://regex101.com/
     http://myregexp.com/
     https://www.debuggex.com/
     http://myregexp.com/g
    */

    // TODO: Revisar regexp para extraer parametros y validar comando
    private static final String SCPI_COMAND_WITH_ARGUMENTS_REGEX =
            "^([^$]*\\$([^$]+)\\$[^$]*)+$";
    private static final String SCPI_COMMAND_REGEX =
            "([^$]*(\\$(?<argument>[^$]+)\\$)+[^$]*)";
    // "([^$]*(\\$(?<argument>[^$]*)\\$)*[^$]*)";

    private String rawCommand;
    private String preparedCommand;

    private Set<String> argumentsSet;
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

    public ScpiCommand2(String command)
    {
        if (command == null)
        {
            // TODO: excepcion ante argumentos null
            throw new IllegalArgumentException("Arguments must not be null");
        }

        if (hasArguments(command))
        {
            /* TODO: Elegir la forma mas conveniente para almacenar
               los nombres de los argumentos
            */
            argumentsSet = new HashSet<String>();
            // variablesBundle = new VariablesBundle();

            extractArgumentNames(command);
            wellFormed = false;
        }
        else
        {
            wellFormed = true;
        }

        needUpdate = true;
        rawCommand = command;
        preparedCommand = command;
    }

    public ScpiCommand2(String command,
                        VariablesBundle bundle)
    {
        if (command == null || bundle == null)
        {
            // TODO: excepcion ante argumentos null
            throw new IllegalArgumentException("Arguments must not be null");
        }

        if (hasArguments(command))
        {
            argumentsSet = new HashSet<String>();
            variablesBundle = bundle;
            
            extractArgumentNames(command);
            attachChangeListenerToVariables();
            wellFormed = checkArgumentsAvailability();
        }
        else
        {
            wellFormed = true;
        }

        needUpdate = true;
        rawCommand = command;
        preparedCommand = command;
    }

    private static boolean hasArguments(String command)
    {
        return command.matches(SCPI_COMAND_WITH_ARGUMENTS_REGEX);
    }

    public boolean hasArguments()
    {
        return argumentsSet != null;
    }

    public boolean isWellFormed()
    {
        return wellFormed;
    }

    private void extractArgumentNames(String command)
    {
        Pattern pattern = Pattern.compile(SCPI_COMMAND_REGEX);
        Matcher matcher = pattern.matcher(command);

        while (matcher.find())
        {
            try
            {
                String argumentName = matcher.group("argument");
                if (argumentName != null)
                {
                    argumentsSet.add(argumentName);
                }
            }
            catch (Exception ex)
            {}
        }
    }

    private boolean checkArgumentsAvailability()
    {
        return argumentsSet.stream()
            .noneMatch(argument -> variablesBundle.get(argument) == null);
    }

    private void attachChangeListenerToVariables()
    {
        argumentsSet.stream()
            .forEach(argument ->
                {
                    Variable variable = variablesBundle.get(argument);
                    if (variable != null)
                    {
                        variable.addChangeListener(changeListener);
                    }
                });
    }

    private String replaceArgumentsWithVariables(VariablesBundle bundle)
    {
        if (argumentsSet == null)
        {
            return rawCommand;
        }

        String command = rawCommand;

        for (String argumentName : argumentsSet)
        {
            String pattern = String.format("$%s$", argumentName);
            Variable variable = bundle.get(argumentName);
            command = command.replace(pattern,
                variable.getValue().toString());
        }

        return command;
    }

    public String getCommand()
    {
        if (variablesBundle != null && hasArguments() && needUpdate)
        {
            preparedCommand =
                replaceArgumentsWithVariables(variablesBundle);
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

        variablesBundle = bundle;

        if (hasArguments())
        {
            attachChangeListenerToVariables();
            wellFormed = checkArgumentsAvailability();
        }
        else
        {
            wellFormed = true;
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

        return replaceArgumentsWithVariables(bundle);
    }
}
