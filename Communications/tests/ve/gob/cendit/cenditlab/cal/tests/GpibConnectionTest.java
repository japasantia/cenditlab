package ve.gob.cendit.cenditlab.cal.tests;

import ve.gob.cendit.cenditlab.io.GpibConnection;
import ve.gob.cendit.cenditlab.io.IGpibConnection;

/**
 * Created by jarias on 21/06/17.
 */
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
