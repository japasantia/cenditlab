package ve.gob.cendit.cenditlab.scpi;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scpi implements ICommand
{
    /**
     * Read response modes
     * */
    public static final int NORMAL_READ = 0;
    public static final int DELAY_FOR_READ = 1;
    public static final int WAIT_FOR_READ = 2;
    public static final int RETRY_UNTIL_READ = 3;

    private String rawCommand;
    private String formattedCommand;

    private Data[] argumentsData;
    private Data resultData;

    private Set<String> argumentNamesSet;

    private int readMode = NORMAL_READ;
    private long delay = 0L;


    public Scpi(String command)
    {
        Objects.requireNonNull(command);

        formattedCommand = rawCommand = clean(command);

        extractInputArgumentNames();
    }

    public String getFormattedCommand()
    {
        return formattedCommand;
    }

    public String getCommand()
    {
        return rawCommand;
    }

    public void setArguments(Data... args)
    {
        argumentsData = args;
    }

    public Data[] getArguments()
    {
        return argumentsData;
    }

    public void setResult(Data result)
    {
        resultData = result;
    }

    public Data getResults()
    {
        return resultData;
    }

    public void setDelay(long value)
    {
        delay = value;
    }

    public long getDelay()
    {
        return delay;
    }

    public void setReadMode(int value)
    {
        readMode = value;
    }

    public int getReadMode()
    {
        return readMode;
    }

    @Override
    public void execute(IConnection connection)
    {
        send(connection);

        delay();

        read(connection);
    }

    private void send(IConnection connection)
    {
        formattedCommand = applyArguments(rawCommand);
        connection.write(formattedCommand);
    }

    private void read(IConnection connection)
    {
        if (! hasResult())
            return;

        switch (readMode)
        {
            case Scpi.NORMAL_READ:
                String response = connection.read();
                resultData.setValue(response);
                break;

            case Scpi.DELAY_FOR_READ:
                delayForCompletionRead(connection, resultData);
                break;

            case Scpi.WAIT_FOR_READ:
                waitForCompletionRead(connection, resultData);
                break;

            case Scpi.RETRY_UNTIL_READ:
                retryUntilReponseRead(connection, resultData);
                break;
        }
    }

    private void delayForCompletionRead(IConnection connection, Data result)
    {
        delay();

        String response = connection.read();
        result.setValue(response);
    }

    private void waitForCompletionRead(IConnection connection, Data result)
    {
        connection.waitForCompletion();

        String response = connection.read();
        result.setValue(response);
    }

    private void retryUntilReponseRead(IConnection connection, Data result)
    {
        int retry = 3;
        String response = null;

        while (--retry > 0)
        {
            try
            {
                response = connection.read();
                result.setValue(response);
            }
            catch (Exception ex)
            { }

            delay();
        }
    }

    public boolean hasArguments()
    {
        return argumentsData != null;
    }

    public boolean hasResult()
    {
        return resultData != null;
    }

    private boolean canApplyArguments()
    {
        return argumentsData != null && argumentNamesSet != null;
    }

    private void extractInputArgumentNames()
    {
        if (rawCommand.matches(COMMAND_WITH_ARGUMENTS_REGEX) == false)
            return;

        argumentNamesSet = new HashSet<>();

        Pattern pattern = Pattern.compile(INPUT_PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(rawCommand);
        String name;

        // Extrae variables de entrada (argumentos del comando)
        while (matcher.find())
        {
            try
            {
                name = matcher.group("varin");
                if (name != null)
                {
                    argumentNamesSet.add(name);
                }
            }
            catch (Exception ex)
            { }
        }
    }

    public void applyArguments()
    {
        formattedCommand = applyArguments(rawCommand);
    }

    private String applyArguments(String command)
    {
        if ( canApplyArguments() )
        {
            for (String name : argumentNamesSet)
            {
                Data data = getDataByName(name);

                if (data != null)
                {
                    String pattern = String.format(INPUT_ARG_FORMAT, name);

                    command = command.replace(pattern, data.getValue());
                }
            }
        }

        return command;
    }

    private Data getDataByName(String name)
    {
        return Arrays.stream(argumentsData)
                .filter(data -> data.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private void delay()
    {
        if (delay <= 0)
            return;

        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    private static final String INPUT_ARG_FORMAT = "{%s}";

    private static final String INPUT_PLACEHOLDER_REGEX =
        "[^{}]*(\\{(?<varin>[^{}]+)\\})[^{}]*";

    private static final String COMMAND_WITH_ARGUMENTS_REGEX =
            "^([^{}]*\\{[^{}]+\\}[^{}]*)+$";

    private static final String VALID_COMMAND_REGEX_1 =
        "^\\s*:?([\\d\\w]|\\{\\d+\\})+(:([\\d\\w]|\\{\\d+\\})+)*(\\s+([\\d\\w]|\\{\\d+\\})*)*;?$";

    private static final String VALID_COMMAND_REGEX_2 =
            "(^\\s*\\*([\\d\\w]|\\{\\d+\\})+\\?;?\\s*$)";

    private static final String VALID_COMMAND_REGEX_3 =
            "^\\s*\\*([\\d\\w]|\\{\\d+\\})+(\\s+([\\d\\w]|\\{\\d+\\})+)?;?";


    public static String applyArguments(String command, String... args)
    {
        String formattedCommand = clean(command);

        if (Objects.nonNull(command) && Objects.nonNull(args))
        {
            for (int i = 0; i < args.length; i++)
            {
                String arg = args[i];

                if (Objects.nonNull(arg))
                {
                    String placeholder = String.format(INPUT_ARG_FORMAT, i);
                    formattedCommand = formattedCommand.replace(placeholder, args[i]);
                }
            }
        }

        return formattedCommand;
    }

    public static String applyArguments(String command, Data... args)
    {
        String formattedCommand = clean(command);

        if (Objects.nonNull(command) && Objects.nonNull(args))
        {
            for (int i = 0; i < args.length; ++i)
            {
                Data data = args[i];

                if (Objects.nonNull(data))
                {
                    String placeholder = String.format(INPUT_ARG_FORMAT, i);
                    formattedCommand = formattedCommand.replace(placeholder, data.toString());
                }
            }
        }

        return formattedCommand;
    }

    public static String clean(String command)
    {
        return command.trim();
    }

    public static boolean isValid(String command)
    {
        return Objects.nonNull(command) &&
                (command.matches(VALID_COMMAND_REGEX_1) ||
                command.matches(VALID_COMMAND_REGEX_2) ||
                command.matches(VALID_COMMAND_REGEX_3));

    }
}
