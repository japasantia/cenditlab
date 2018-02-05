package ve.gob.cendit.cenditlab.io.scpi;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.Objects;

public class ScpiCommand
{
    private String rawCommand;
    private String formattedCommand;

    private Data[] argsArrayData;
    private Data resultData;

    private long responseDelay = 0L;

    public ScpiCommand(String command)
    {
        this(command, null, null);
    }

    public ScpiCommand(String command, Data... args)
    {
        this(command, null, args);
    }

    public ScpiCommand(String command, long responseWaitMillis, Data result)
    {
        this(command, responseWaitMillis, result, null);
    }

    public ScpiCommand(String command, long responseWaitMillis, Data result, Data... args)
    {
        Objects.requireNonNull(command, "command must be not null");

        rawCommand = formattedCommand = clean(command);

        resultData = result;
        argsArrayData = args;

        responseDelay = responseWaitMillis;
    }

    public ScpiCommand(String command, Data result)
    {
        this(command, 0L, result);
    }

    public ScpiCommand(String command, Data result, Data... args)
    {
        this(command, 0L, result, args);
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
        argsArrayData = args;
    }

    public Data[] getArguments()
    {
        return argsArrayData;
    }

    public void setResult(Data result)
    {
        resultData = result;
    }

    public Data getResult()
    {
        return resultData;
    }

    public String applyArgs(String... args)
    {
        formattedCommand = ScpiCommand.format(rawCommand, args);
        return formattedCommand;
    }

    public String applyArgs(Data... args)
    {
        formattedCommand = ScpiCommand.format(rawCommand, args);
        return formattedCommand;
    }

    public Data execute(IConnection connection)
    {
        if (hasArguments())
        {
            applyArgs(argsArrayData);
        }

        connection.write(formattedCommand);

        waitForResponse();

        if (hasResult())
        {
            String result = connection.read();
            resultData.setValue(result);
        }

        return resultData;
    }

    public boolean hasArguments()
    {
        return argsArrayData != null;
    }

    public boolean hasResult()
    {
        return resultData != null;
    }

    private void waitForResponse()
    {
        if (responseDelay <= 0)
            return;

        try
        {
            Thread.sleep(responseDelay);
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
