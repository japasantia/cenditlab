package ve.gob.cendit.cenditlab.io.scpi;

import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.Objects;

public class ScpiCommand
{
    private String rawCommand;
    private String formattedCommand;

    public ScpiCommand(String command)
    {
        rawCommand = command;
        formattedCommand = command;
    }



    public String applyArgs(String... args)
    {
        formattedCommand = ScpiCommand.format(rawCommand, args);
        return formattedCommand;
    }

    public void send(IConnection connection)
    {
        connection.write(formattedCommand);
    }

    public String receive(IConnection connection)
    {
        return connection.read();
    }

    public String sendReceive(IConnection connection)
    {
        send(connection);
        return receive(connection);
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
                String placeholder = String.format(ARG_FORMAT, i);
                formattedCommand = formattedCommand.replace(placeholder, args[i]);
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
