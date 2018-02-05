package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibConnection;
import ve.gob.cendit.cenditlab.io.scpi.ScpiCommand;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;
import ve.gob.cendit.cenditlab.io.vxi11.LinuxVxi11Connection;

import java.util.Arrays;

public class ScpiCommandTest
{
    public static void main(String[] args)
    {
        // IConnection connection = getLinuxGpibConnection("GPIB0::10::INSTR");
        IConnection connection = getTcpIpConnection("TCPIP0::192.168.151.100::gpib0,10::INSTR");

        // basicTest();
        commandsTest1(connection);
        commandsTest2(connection);
        commandsTest3(connection);
    }

    private static IConnection getLinuxGpibConnection(String address)
    {
        LinuxGpibConnection connection =
                (LinuxGpibConnection) ConnectionFactory.CreateConnection(new VisaAddress("GPIB0::10::INSTR"));

        connection.open();

        connection.setTimeout(LinuxGpibConnection.TNONE);

        return connection;
    }

    private static IConnection getTcpIpConnection(String address)
    {
        LinuxVxi11Connection connection =
                (LinuxVxi11Connection) ConnectionFactory.CreateConnection(new VisaAddress(address));

        connection.open();

        return connection;
    }

    public static void basicTest()
    {
        String command;
        boolean ret;

        ret = ScpiCommand.isValid(":SENSE:BANDWIDTH {0}");
        ret = ScpiCommand.isValid(":SENSE:{1}:COUNT {0}");
        command = ScpiCommand.format(":SENSE:BANDWIDTH {0}", "400000");
        command = ScpiCommand.format(":SENSE:{1}:COUNT {0}", "2", "AVERAGE");
    }

    private static void commandsTest1(IConnection connection)
    {
        Data nfData = new Data("Noise Figure");
        Data gainData = new Data("Gain");
        Data photData = new Data("Phot");
        Data pcoldData = new Data("Pcold");

        Data okData = new Data();

        Data[] resultsData =
        {
            nfData,
            gainData,
            photData,
            pcoldData
        };

        ScpiCommand[] commands =
        {
            new ScpiCommand("*CLS"),
            new ScpiCommand(":INITIATE:CONTINUOUS:ALL OFF"),
            new ScpiCommand("*OPC"),
            new ScpiCommand(":INITIATE:IMMEDIATE"),
            //new ScpiCommand("*WAI"),
            new ScpiCommand("*OPC?", 30000, okData),
            new ScpiCommand(":FETCH:ARRAY:CORRECTED:NFIGURE? DB", 1000, nfData),
            new ScpiCommand(":FETCH:ARRAY:CORRECTED:GAIN? DB", 1000, gainData),
            new ScpiCommand(":FETCH:ARRAY:CORRECTED:PHOT?", 1000, photData),
            new ScpiCommand(":FETCH:ARRAY:CORRECTED:PCOLD?", 1000, pcoldData)
        };


        Arrays.stream(commands)
            .forEach(command ->
                {
                    String message;

                    try
                    {
                        message = String.format("Executing: %s", command.getFormattedCommand());
                        System.out.println(message);
                        command.execute(connection);
                    }
                    catch (Exception ex)
                    {
                        message = String.format("[Exception: %s] command: %s",
                                ex.getMessage(), command.getFormattedCommand());
                        System.out.println(message);
                    }
                });

        Arrays.stream(resultsData)
            .forEach(data ->
                {
                    System.out.println(String.format("%s: %s", data.getName(), data.getValue()));
                });
    }

    private static void commandsTest2(IConnection connection)
    {
        Data gainData = new Data("Gain");
        Data nfData = new Data("Noise Figure");
        Data pcoldData = new Data("Pcold");
        Data photData = new Data("Phot");
        Data teffData = new Data("Teff");

        Data[] resultsData =
        {
            gainData,
            nfData,
            pcoldData,
            photData,
            teffData
        };

        ScpiCommand[] commands =
        {
            new ScpiCommand(":READ:ARRAY:DATA:CORRECTED:GAIN?", gainData),
            new ScpiCommand(":READ:ARRAY:DATA:CORRECTED:NFIGURE?", nfData),
            new ScpiCommand(":READ:ARRAY:DATA:CORRECTED:PCOLD?", pcoldData),
            new ScpiCommand(":READ:ARRAY:DATA:CORRECTED:PHOT?", photData),
            new ScpiCommand(":READ:ARRAY:DATA:CORRECTED:TEFFECTIVE?", teffData),
        };

        Arrays.stream(commands)
            .forEach(command ->
            {
                try
                {
                    command.execute(connection);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            });

        Arrays.stream(resultsData)
            .forEach(data ->
            {
                System.out.println(String.format("%s: %s", data.getName(), data.getValue()));
            });
    }

    private static void commandsTest3(IConnection connection)
    {

        Data opcData = new Data("Op completed");
        Data gainData = new Data("Gain");
        Data nfData = new Data("Noise Figure");
        Data pcoldData = new Data("Pcold");
        Data photData = new Data("Phot");
        Data teffData = new Data("Teff");

        Data[] resultsData =
        {
                opcData,
                gainData,
                nfData,
                pcoldData,
                photData,
                teffData
        };

        // calibrate(connection);

        ScpiCommand[] commands =
        {
            new ScpiCommand(":SENSE:FREQUENCY:MODE FIXED"),
            new ScpiCommand(":SENSE:FREQUENCY:FIXED 7GHZ"),
            new ScpiCommand("*OPC"),
            new ScpiCommand(":SENSE:CORRECTION:COLLECT:ACQUIRE STANDARD"),
            new ScpiCommand("*OPC?", opcData),
            new ScpiCommand(":FETCH:SCALAR:DATA:CORRECTED:GAIN?", gainData),
            new ScpiCommand(":FETCH:SCALAR:DATA:UNCORRECTED:NFIGURE?", nfData),
            new ScpiCommand(":FETCH:SCALAR:DATA:CORRECTED:PCOLD?", pcoldData),
            new ScpiCommand(":FETCH:SCALAR:DATA:CORRECTED:PHOT?", photData),
            new ScpiCommand(":FETCH:SCALAR:DATA:UNCORRECTED:TEFFECTIVE?", teffData)
        };

        Arrays.stream(commands)
            .forEach(command ->
            {
                try
                {
                    command.execute(connection);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            });

        Arrays.stream(resultsData)
            .forEach(data ->
            {
                System.out.println(String.format("%s: %s", data.getName(), data.getValue()));
            });
    }

    private static void calibrate(IConnection connection)
    {
        Data opcData = new Data("Op completed");

        ScpiCommand[] commands =
        {
            new ScpiCommand("*OPC"),
            new ScpiCommand(":SENSE:CORRECTION:COLLECT:ACQUIRE STANDARD"),
            new ScpiCommand("*OPC?", opcData)
        };

        try
        {
            Arrays.stream(commands)
                    .forEach(command -> command.execute(connection));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
