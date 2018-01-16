package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibConnection;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;

public class GeneralTest
{
    private static LinuxGpibConnection connection;

    public static void main(String[] args)
    {
        test1();
    }

    private static void test1()
    {
        connection =
            (LinuxGpibConnection) ConnectionFactory.CreateConnection(new VisaAddress("GPIB0::10::INSTR"));

        connection.open();

        connection.setTimeout(LinuxGpibConnection.T1000S);

        connection.write("*CLS");

        displayOn();
        //displayOff();

        showId();

        //calibrate();

        measureNF1();
    }

    private static void showId()
    {
        connection.write("*IDN?");
        String response = readPrint();
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
        connection.write(":SENSE:CORRECTION:COLLECT:ACQUIRE STANDARD");
        connection.write("*WAI");
    }

    private static void measureNF1()
    {
        String response;

        connection.write(":INITIATE:CONTINUOUS:ALL OFF");
        connection.write(":SENSE:FREQUENCY:MODE SWEEP");
        connection.write(":SENSE:AVERAGE:STATE ON");
        connection.write(":SENSE:AVERAGE:COUNT 2");
        connection.write(":SENSE:SWEEP:POINTS 20");
        connection.write(":SENSE:BANDWIDTH 400000");

        // connection.write("INITIATE:IMMEDIATE");

        connection.write("READ:ARRAY:DATA:CORRECTED:NFIGURE?;");

        //connection.write("*WAI;");

        //connection.write("*OPC;");

        response = readPrint();

        connection.write("*ESR?;");

        response = readPrint();
    }

    private static String readPrint()
    {
        String data = connection.read();
        print(data);
        return data;
    }

    private static void print(String text)
    {
        System.out.println(text);
    }
}
