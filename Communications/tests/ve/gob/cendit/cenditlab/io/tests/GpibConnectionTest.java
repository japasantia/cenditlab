package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.io.gpib.GpibConnection;
import ve.gob.cendit.cenditlab.io.gpib.IGpibConnection;

public class GpibConnectionTest
{
    public static void main(String[] args)
    {
        try
        {
            IGpibConnection gpibConnection =
                    GpibConnection.CreateConnection("GPIB0::10::INSTR");

            gpibConnection.open();
            gpibConnection.write("*IDN?\n");
            String response = gpibConnection.read();
            System.out.printf("Instr response: %s\n", response);

            gpibConnection.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
