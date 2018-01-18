package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibConnection;
import ve.gob.cendit.cenditlab.io.scpi.ScpiCommand;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;

public class ScpiCommandTest
{
    public static void main(String[] args)
    {
        // basicTest();
        communicationTest();
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

    public static void communicationTest()
    {
        String response;

        LinuxGpibConnection connection =
                (LinuxGpibConnection) ConnectionFactory.CreateConnection(new VisaAddress("GPIB0::10::INSTR"));
        connection.setTimeout(LinuxGpibConnection.TNONE);

        connection.open();

        ScpiCommand c0 = new ScpiCommand("*CLS");
        ScpiCommand c1 = new ScpiCommand(":INITIATE:CONTINUOUS:ALL OFF");
        ScpiCommand c2 = new ScpiCommand("*OPC");
        ScpiCommand c3 = new ScpiCommand(":INITIATE:IMMEDIATE");
        ScpiCommand c4 = new ScpiCommand("*OPC?");
        ScpiCommand c5 = new ScpiCommand(":FETCH:ARRAY:CORRECTED:NFIGURE?");
        ScpiCommand c6 = new ScpiCommand(":FETCH:ARRAY:CORRECTED:GAIN?");
        ScpiCommand c7 = new ScpiCommand(":FETCH:ARRAY:UNCORRECTED:PHOT?");
        ScpiCommand c8 = new ScpiCommand(":FETCH:ARRAY:CORRECTED:PCOLD?");

        c0.send(connection);
        c1.send(connection);
        c2.send(connection);
        c3.send(connection);
        response = c4.sendReceive(connection);
        //c4.send(connection);
        response = c5.sendReceive(connection);
        response = c6.sendReceive(connection);
        response = c7.sendReceive(connection);
        response = c8.sendReceive(connection);
    }
}
