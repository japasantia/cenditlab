package ve.gob.cendit.cenditlab.io.scpi;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.Arrays;
import java.util.Objects;

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
    private Data[] resultsData;

    private int readMode = NORMAL_READ;
    private long delay = 0L;


    public Scpi(String command)
    {
        this(command, null, null);
    }

    public Scpi(String command, Data[] args, Data[] results)
    {
        formattedCommand = rawCommand = clean(command);

        setArguments(args);
        setResults(results);
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

    public void setResults(Data... results)
    {
        resultsData = results;
    }

    public Data[] getResults()
    {
        return resultsData;
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

    public String applyArgs(String... args)
    {
        formattedCommand = Scpi.format(rawCommand, args);
        return formattedCommand;
    }

    public String applyArgs(Data... args)
    {
        formattedCommand = Scpi.format(rawCommand, args);
        return formattedCommand;
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
        if (hasArguments())
        {
            applyArgs(argumentsData);
        }

        connection.write(formattedCommand);
    }

    private void read(IConnection connection)
    {
        if (hasResult())
        {
            Arrays.stream(resultsData)
                .forEach(result -> read(connection, result));
        }
    }

    private void read(IConnection connection, Data result)
    {
        switch (readMode)
        {
            case Scpi.NORMAL_READ:
                String response = connection.read();
                result.setValue(response);
                break;

            case Scpi.DELAY_FOR_READ:
                delayForCompletionRead(connection, result);
                break;

            case Scpi.WAIT_FOR_READ:
                waitForCompletionRead(connection, result);
                break;

            case Scpi.RETRY_UNTIL_READ:
                retryUntilReponseRead(connection, result);
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
        return resultsData != null;
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

    private static final String ARG_FORMAT = "{%d}";

    private static final String COMMAND_REGEX =
        "^\\s*:?([\\d\\w]|\\{\\d+\\})+(:([\\d\\w]|\\{\\d+\\})+)*(\\s+([\\d\\w]|\\{\\d+\\})*)*;?$";

    private static final String COMMON_COMMAND_REGEX_1 =
            "(^\\s*\\*([\\d\\w]|\\{\\d+\\})+\\?;?\\s*$)";

    private static final String COMMON_COMMAND_REGEX_2 =
            "^\\s*\\*([\\d\\w]|\\{\\d+\\})+(\\s+([\\d\\w]|\\{\\d+\\})+)?;?";


    public static String format(String command, String... args)
    {
        String formattedCommand = clean(command);

        if (Objects.nonNull(command) && Objects.nonNull(args))
        {
            for (int i = 0; i < args.length; i++)
            {
                String arg = args[i];

                if (Objects.nonNull(arg))
                {
                    String placeholder = String.format(ARG_FORMAT, i);
                    formattedCommand = formattedCommand.replace(placeholder, args[i]);
                }
            }
        }

        return formattedCommand;
    }

    public static String format(String command, Data... args)
    {
        String formattedCommand = clean(command);

        if (Objects.nonNull(command) && Objects.nonNull(args))
        {
            for (int i = 0; i < args.length; ++i)
            {
                Data data = args[i];

                if (Objects.nonNull(data))
                {
                    String placeholder = String.format(ARG_FORMAT, i);
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
                (command.matches(COMMAND_REGEX) ||
                command.matches(COMMON_COMMAND_REGEX_1) ||
                command.matches(COMMON_COMMAND_REGEX_2));

    }
}
