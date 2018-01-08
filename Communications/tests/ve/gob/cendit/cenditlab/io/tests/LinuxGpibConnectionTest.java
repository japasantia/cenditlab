package ve.gob.cendit.cenditlab.io.tests;

import ve.gob.cendit.cenditlab.io.ConnectionFactory;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibConnection;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;

/**
 * Created by jarias on 21/06/17.
 */
public class LinuxGpibConnectionTest
{
    public static void main(String[] args)
    {
        try
        {
            //GPIB[board]::primary address[::secondary address][::INSTR]
            VisaAddress visaAddress = new VisaAddress("GPIB0::10");
            IConnection gpibConnection = ConnectionFactory.CreateConnection(visaAddress);

            gpibConnection.open();

            String command = "*IDN?";
            int ret = gpibConnection.write(command);
            printf("Enviado: %s (%d caracteres)",
                    command, ret);

            String response = gpibConnection.read();
            printf("Recibido: %s", response);

            byte[] sendData = command.getBytes();
            byte[] receiveData = new byte[256];
            ret = gpibConnection.write(sendData);
            printf("Enviado: %s (%d caracteres)",
                    sendData.toString(), ret);

            ret = gpibConnection.read(receiveData);
            printf("Recibido: %s (%d caracteres)",
                    receiveData.toString(), ret);

            try
            {
                gpibConnection = new LinuxGpibConnection(new VisaAddress("GPIB1::10"));
                gpibConnection.open();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            try
            {
                gpibConnection = new LinuxGpibConnection(new VisaAddress("GPIB0::10"));
                gpibConnection.write("*IDN?");
                response = gpibConnection.read();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    private static void print(String message)
    {
        System.out.println(message);
    }

    private static void printf(String format, Object... args)
    {
        System.out.printf(format, args);
    }
}
