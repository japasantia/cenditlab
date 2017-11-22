package ve.gob.cendit.cenditlab.io.tests;

import oracle.jrockit.jfr.StringConstantPool;
import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.LinuxVxi11Connection;
import ve.gob.cendit.cenditlab.io.VisaAddress;

/**
 * Created by jarias on 06/09/17.
 */
public class LinuxVxi11ConnectionTest
{
    public static void main(String[] args)
    {
        IConnection connection =
            ConnectionFactory.CreateConnection(
                    new VisaAddress("TCPIP0::192.168.151.100::gpib0,10::INSTR"));

        connection.open();

        int bytesWritten = connection.write("*IDN?");
        String response = connection.read();

        System.out.printf("IDN: %s\n", response);

        connection.close();

        try
        {
            connection.write("*IDN?");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
