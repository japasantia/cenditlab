package ve.gob.cendit.cenditlab.io.vxi11;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import ve.gob.cendit.cenditlab.io.ConnectionException;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;
import ve.gob.cendit.cenditlab.io.visa.VisaAddressFields;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class LinuxVxi11Connection implements IConnection
{
    private static final IVxi11 library;
    private static final int CLINK_SIZE = 2;

    static
    {
        library = LinuxVxi11Library.getLibrary();
    }

    private VisaAddress visaAddress;
    private Pointer clink;

    public LinuxVxi11Connection(VisaAddress address)
    {
        visaAddress = address;
        long memory = Native.malloc(CLINK_SIZE);
        clink = new Pointer(memory);
    }

    @Override
    public String read()
    {
        ByteArrayOutputStream byteStream =
                new ByteArrayOutputStream();

        String dataRead = null;
        byte[] buffer = new byte[1024];
        long bytesReceived = 0;

        try
        {
            // TODO: revisar este proceso de lectura
            do
            {
                bytesReceived = read(buffer);

                checkReturnForException(bytesReceived, "Error reading from device",
                        true);

                byteStream.write(buffer,
                        byteStream.size(),
                        (int)bytesReceived);
            }
            while (bytesReceived >= buffer.length);

            dataRead = byteStream.toString();
            byteStream.close();
        }
        catch (IOException ex)
        {
            throw new ConnectionException("IO exception on read operation", ex);
        }

        return dataRead;
    }

    @Override
    public int read(byte[] buffer)
    {
        int bytesReceived = (int)library.vxi11_receive(clink, buffer, (long)buffer.length,
                IVxi11.VXI11_READ_TIMEOUT);

        checkReturnForException(bytesReceived, "Error reading from device",
                true);

        return bytesReceived;
    }

    @Override
    public int read(byte[] buffer, int offset, int length)
    {
        int bytesReceived;
        byte[] data = new byte[length];

        bytesReceived  = (int) library.vxi11_receive(clink, data, (long)length,
                IVxi11.VXI11_READ_TIMEOUT);

        checkReturnForException(bytesReceived, "Error reading from device",
                true);

        System.arraycopy(data, 0, buffer, offset, length);

        return (int)bytesReceived;
    }

    @Override
    public int write(String data)
    {
        int ret;
        byte[] buffer = data.getBytes();

        ret = library.vxi11_send(clink, buffer, buffer.length);

        checkReturnForException(ret, "Error writing to device");

        return data.length();
    }

    @Override
    public int write(byte[] buffer)
    {
        return write(buffer, 0, buffer.length);
    }

    @Override
    public int write(byte[] buffer, int offset, int length)
    {
        if (length == 0)
        {
            return 0;
        }

        byte[] data = Arrays.copyOfRange(buffer, offset, offset + length - 1);

        int ret = library.vxi11_send(clink, data, length);

        checkReturnForException(ret, "Error writing to device");

        return length;
    }

    @Override
    public void open()
    {
        // Para interfaz TCPIP
        if ( visaAddress.isTcpIp() )
        {
            if (visaAddress.hasField(VisaAddressFields.HOST_ADDRESS) &&
                    visaAddress.hasField(VisaAddressFields.DEVICE_NAME))
            {

                String hostAddress = visaAddress.getHostAddress();
                String deviceName = visaAddress.getDeviceName();

                int ret = library.vxi11_open_device(
                        hostAddress, /* direccion ip */
                        clink, /* manejador / handler */
                        deviceName /* direccion gpib instrumento */);

                // Revisa apertura exitosa
                checkReturnForException(ret, "Error opening device");
            }
        }
        else
        {
            // El dispositivo no puede ser abierto con esta libreria
            throw new ConnectionException("Visa address does not correspond to a TCPIP device");
        }
    }

    @Override
    public void close()
    {
        int ret = library.vxi11_close_device(visaAddress.getHostAddress(), clink);

        // Revisa cierre exitoso
        checkReturnForException(ret, "Error closing device");
    }

    @Override
    public void waitForCompletion()
    {

    }

    private static void checkReturnForException(long ret, String errorMessage)
    {
        checkReturnForException(ret, errorMessage, false);
    }

    private static void checkReturnForException(long ret, String errorMessage, boolean wasReading)
    {
        if (ret == IVxi11.VXI11_NULL_WRITE_RESP)
        {
            throw new ConnectionException(String.format("Null write response. %s", errorMessage));
        }
        else if (ret == IVxi11.VXI11_NULL_READ_RESP)
        {
            throw new ConnectionException(String.format("Null read response. %s", errorMessage));
        }
        else if (wasReading && ret ==  IVxi11.VXI11_NOTHING_RECEIVED)
        {
            throw new ConnectionException(String.format("Nothing received. %s", errorMessage));
        }
        else if (ret < 0 /*&& ret != IVxi11.VXI11_NOTHING_RECEIVED*/)
        {
            throw new ConnectionException(errorMessage);
        }
    }

}
