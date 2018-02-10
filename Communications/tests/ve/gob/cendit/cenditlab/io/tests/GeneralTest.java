package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibConnection;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;

import java.util.Timer;
import java.util.TimerTask;

public class GeneralTest
{
    private static LinuxGpibConnection connection;
    private static LinuxGpibConnection adapterConnection;
    private static Timer timer;

    public static void main(String[] args)
    {
        test1();
    }

    private static void test1()
    {
        connection =
            (LinuxGpibConnection) ConnectionFactory.CreateConnection(new VisaAddress("GPIB0::10::INSTR"));

        adapterConnection =
            (LinuxGpibConnection) ConnectionFactory.CreateConnection(new VisaAddress("GPIB0::11::INSTR"));

        connection.open();
        adapterConnection.open();

        connection.setTimeout(1000);

        connection.write("*CLS");

        //serialPoll();
        //lineStatus();

        startMonitor();

        displayOn();
        // displayOff();

        showId();

        calibrate();

        measureNF1();

        // stopMonitor();
    }

    private static String showId()
    {
        connection.write("*IDN?");
        String response = readPrint();
        return response;
    }

    private static String showEsr()
    {
        connection.write("*ESR?");
        String response = readPrint();
        return response;
    }

    private static String showOpc()
    {
        connection.write("*OPC?");
        String response = readPrint();
        return response;
    }

    private static String showOperationConditionRegister()
    {
        connection.write(":STATUS:OPERATION:CONDITION?");
        String response = readPrint();
        return response;
    }

    private static void displayOn()
    {
        connection.write("DISPLAY:ENABLE:STATE ON");
    }

    private static void displayOff()
    {
        connection.write("DISPLAY:ENABLE:STATE OFF");
    }

    private static void calibrate()
    {
        connection.write("*OPC");
        connection.write(":SENSE:CORRECTION:COLLECT:ACQUIRE STANDARD");

        showOperationConditionRegister();
        showOpc();
        showOperationConditionRegister();

        /*
        try
        {
            Thread.sleep(30000);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
        */
    }

    private static void measureNF1()
    {
        String response;

        showOperationConditionRegister();

        connection.write(":INITIATE:CONTINUOUS:ALL ON");
        connection.write(":SENSE:FREQUENCY:MODE SWEEP");
        connection.write(":SENSE:AVERAGE:STATE ON");
        connection.write(":SENSE:AVERAGE:COUNT 2");
        connection.write(":SENSE:SWEEP:POINTS 20");
        connection.write(":SENSE:BANDWIDTH 400000");

        // connection.write("INITIATE:IMMEDIATE");

        showOperationConditionRegister();
        connection.write("*OPC;");
        connection.write(":READ:ARRAY:DATA:CORRECTED:NFIGURE?;");
        response = readPrint();

        showEsr();
        showOperationConditionRegister();
        showOpc();
        showOperationConditionRegister();
        showEsr();
    }

    private static void startMonitor()
    {
        timer = new Timer("Timer monitor", true);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                serialPoll();
                lineStatus();
            }
        }, 0L, 500L);
    }

    private static void stopMonitor()
    {
        timer.cancel();
    }

    private static void serialPoll()
    {
        print(String.format("Device serial poll: %s", Integer.toBinaryString(connection.serialPoll())));
    }

    private static void lineStatus()
    {
        print(String.format("Adapter line status: %s", Integer.toBinaryString(adapterConnection.lineStatus())));
    }

    private static String readPrint()
    {
        String data = connection.read();
        print(data);
        return data;
    }

    private synchronized static void print(String text)
    {
        System.out.println(text);
    }
}
