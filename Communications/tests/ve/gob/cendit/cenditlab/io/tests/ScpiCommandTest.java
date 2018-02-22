package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibConnection;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;
import ve.gob.cendit.cenditlab.io.vxi11.LinuxVxi11Connection;
import ve.gob.cendit.cenditlab.scpi.ICommand;
import ve.gob.cendit.cenditlab.scpi.Scpi;
import ve.gob.cendit.cenditlab.scpi.ScpiBuilder;
import ve.gob.cendit.cenditlab.scpi.ScpiExecutor;

import java.util.Arrays;

public class ScpiCommandTest
{
    public static void main(String[] args)
    {
        // IConnection connection = getLinuxGpibConnection("GPIB0::10::INSTR");
        // IConnection connection = getTcpIpConnection("TCPIP0::192.168.151.100::gpib0,10::INSTR");
        IConnection connection = getDummyConnection("Dummy");

        // basicTest();
        commandsTest1(connection);
        // commandsTest2(connection);
        // commandsTest3(connection);
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

    private static IConnection getDummyConnection(String address)
    {
        return new DummyConnection();
    }

    public static void basicTest()
    {
        String command;
        boolean ret;
        /*
        ret = Scpi.isValid(":SENSE:BANDWIDTH {0}");
        ret = Scpi.isValid(":SENSE:{1}:COUNT {0}");
        command = Scpi.applyArguments(":SENSE:BANDWIDTH {0}", "400000");
        command = Scpi.applyArguments(":SENSE:{1}:COUNT {0}", "2", "AVERAGE");
        */
        Data unitData = new Data("unit", "DB");
        Data minFrequencyData = new Data("min freq", "0");
        Data response = new Data("response", "10.0");
        Scpi scpi = new Scpi(":FETCH:ARRAY:CORRECTED:NFIGURE? {unit} {min freq} {response}");
        scpi.setArguments(unitData, minFrequencyData, response);
        scpi.applyArguments();

        command = scpi.getCommand();
        command = scpi.getFormattedCommand();
    }


    private static Data okData = new Data();
    private static Data nfData = new Data("Noise Figure");
    private static Data gainData = new Data("Gain");
    private static Data photData = new Data("Phot");
    private static Data pcoldData = new Data("Pcold");
    private static Data teffData = new Data("teffData");

    private static Data[] resultsData =
    {
        okData, nfData, gainData, photData, pcoldData, teffData
    };

    private static ICommand[] commandsSet1 =
    {
        ScpiBuilder.create("*CLS").get(),
        ScpiBuilder.create(":INITIATE:CONTINUOUS:ALL OFF").get(),
        ScpiBuilder.create("*OPC").get(),
        ScpiBuilder.create(":INITIATE:IMMEDIATE").get(),
        // ScpiBuilder.create("*WAI").get(),
        ScpiBuilder.create("*OPC?").out(okData).delay(3000).get(),
        ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:NFIGURE? DB").out(nfData).delay(1000).get(),
        ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:GAIN? DB").out(gainData).delay(1000).get(),
        ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:PHOT?").out(photData).delay(1000).get(),
        ScpiBuilder.create(":FETCH:ARRAY:CORRECTED:PCOLD?").out(pcoldData).delay(1000).get()
    };

    private static ICommand[] commandsSet2 =
    {
        ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:GAIN?").out(gainData).get(),
        ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:NFIGURE?").out(nfData).get(),
        ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:PCOLD?").out(pcoldData).get(),
        ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:PHOT?").out(photData).get(),
        ScpiBuilder.create(":READ:ARRAY:DATA:CORRECTED:TEFFECTIVE?").out(teffData).get()
    };

    private static void commandsTest1(IConnection connection)
    {
        ScpiExecutor scpiExecutor = new ScpiExecutor(commandsSet1);

        print("Scpi and ScpiExecutor test [synchronous mode]");

        scpiExecutor.setOnSuccessCommand(con ->  showData("Test 1 success", resultsData));
        scpiExecutor.setOnErrorCommand(con -> print("Test 1 fails"));

        scpiExecutor.execute(connection);

        print("Scpi and ScpiExecutor test [asynchronous mode]");
        scpiExecutor.setThreadEnabled(true);
        scpiExecutor.execute(connection);

        scpiExecutor.waitCompletion();

        scpiExecutor.execute(connection);

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex)
        {}

        scpiExecutor.stop();
        scpiExecutor.waitCompletion();
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

    private static void print(String message)
    {
        System.out.println(message);
    }

    private static void showData(String message, Data... argsData)
    {
        print(message);

        Arrays.stream(argsData)
            .forEach( data -> System.out.println(String.format("%s: %s", data.getName(), data.getValue())) );
    }

    private static class DummyConnection implements IConnection
    {
        @Override
        public void open()
        {

        }

        @Override
        public void close()
        {

        }

        @Override
        public String read()
        {
            return "1, 2, 3, 4, 5, 6, 7, 8, 9";
        }

        @Override
        public int read(byte[] buffer)
        {
            return read(buffer, 0, buffer.length);
        }

        @Override
        public int read(byte[] buffer, int offset, int length)
        {
            byte[] source = read().getBytes();
            int len = Math.min(length, source.length);

            System.arraycopy(source, 0, buffer, offset, len);

            return len;
        }

        @Override
        public int write(String data)
        {
            return data.length();
        }

        @Override
        public int write(byte[] buffer)
        {
            return buffer.length;
        }

        @Override
        public int write(byte[] buffer, int offset, int length)
        {
            return length;
        }

        @Override
        public void waitForCompletion()
        {

        }
    }
}
