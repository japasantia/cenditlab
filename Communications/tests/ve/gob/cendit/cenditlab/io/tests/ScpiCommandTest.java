package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibConnection;
import ve.gob.cendit.cenditlab.io.scpi.Scpi;
import ve.gob.cendit.cenditlab.io.scpi.ScpiBuilder;
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

        connection.disableTimeout();

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

        ret = Scpi.isValid(":SENSE:BANDWIDTH {0}");
        ret = Scpi.isValid(":SENSE:{1}:COUNT {0}");
        command = Scpi.format(":SENSE:BANDWIDTH {0}", "400000");
        command = Scpi.format(":SENSE:{1}:COUNT {0}", "2", "AVERAGE");
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

        Scpi[] commands =
        {
            ScpiBuilder.create("*CLS").get(),
            ScpiBuilder.create(":INITIATE:CONTINUOUS:ALL OFF").get(),
            ScpiBuilder.create("*OPC").get(),
            ScpiBuilder.create(":INITIATE:IMMEDIATE").get(),
            // ScpiBuilder.create("*WAI").get(),
            ScpiBuilder.create("*OPC?").out(okData).delay(30000).get(),
            ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:NFIGURE? DB").out(nfData).delay(1000).get(),
            ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:GAIN? DB").out(gainData).delay(1000).get(),
            ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:PHOT?").out(photData).delay(1000).get(),
            ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:PCOLD?").out(pcoldData).delay(1000).get()
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

        Scpi[] commands =
        {
            ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:GAIN?").out(gainData).get(),
            ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:NFIGURE?").out(nfData).get(),
            ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:PCOLD?").out(pcoldData).get(),
            ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:PHOT?").out(photData).get(),
            ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:TEFFECTIVE?").out(teffData).get()
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

        Scpi[] commands =
        {
            ScpiBuilder.create(":SENSE:FREQUENCY:MODE FIXED").get(),
            ScpiBuilder.create(":SENSE:FREQUENCY:FIXED 7GHZ").get(),
            ScpiBuilder.create("*OPC").get(),
            ScpiBuilder.create(":SENSE:CORRECTION:COLLECT:ACQUIRE STANDARD").get(),
            ScpiBuilder.create("*OPC?").out(opcData).get(),
            ScpiBuilder.create(":FETCH:SCALAR:DATA:CORRECTED:GAIN?").out(gainData).get(),
            ScpiBuilder.create(":FETCH:SCALAR:DATA:UNCORRECTED:NFIGURE?").out(nfData).get(),
            ScpiBuilder.create(":FETCH:SCALAR:DATA:CORRECTED:PCOLD?").out(pcoldData).get(),
            ScpiBuilder.create(":FETCH:SCALAR:DATA:CORRECTED:PHOT?").out(photData).get(),
            ScpiBuilder.create(":FETCH:SCALAR:DATA:UNCORRECTED:TEFFECTIVE?").out(teffData).get()
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

        Scpi[] commands =
        {
            ScpiBuilder.create("*OPC").get(),
            ScpiBuilder.create(":SENSE:CORRECTION:COLLECT:ACQUIRE STANDARD").get(),
            ScpiBuilder.create("*OPC?").out(opcData).get()
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
