package ve.gob.cendit.cenditlab.io.vxi11;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
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
        int bytesRead = 0;
        long ret = 0;

        try
        {
            // TODO: revisar este proceso de lectura
            //while (ret != 0)
            // {
                ret = library.vxi11_receive(clink,
                        buffer, buffer.length,
                        IVxi11.VXI11_READ_TIMEOUT);

                if (ret != 0 || ret == IVxi11.VXI11_NULL_READ_RESP)
                {
                    // TODO: lanzar excepcion
                }

                byteStream.write(buffer,
                        byteStream.size(),
                        buffer.length);
            // }

            dataRead = byteStream.toString();
            byteStream.close();
        }
        catch (IOException ex)
        { }

        return dataRead;
    }

    @Override
    public int read(byte[] buffer)
    {
        long ret = library.vxi11_receive(clink, buffer, buffer.length,
                IVxi11.VXI11_READ_TIMEOUT);

        /* TODO: revisar valor de retorno se deberia lanzar excepcion acorde */
        if (ret != 0)
        {

        }

        return buffer.length;
    }

    @Override
    public int read(byte[] buffer, int offset, int length)
    {
        long ret;
        byte[] data = new byte[length];

        ret  = library.vxi11_receive(clink, data, (long)length,
                IVxi11.VXI11_READ_TIMEOUT);

        if (ret != 0 || ret != IVxi11.VXI11_NULL_READ_RESP)
        {
            // TODO: lanzar excepcion
        }

        System.arraycopy(data, 0, buffer, offset, length);

        return length;
    }

    @Override
    public int write(String data)
    {
        long ret;
        byte[] buffer = data.getBytes();

        ret = library.vxi11_send(clink, buffer, buffer.length);

        if (ret != 0 || ret == IVxi11.VXI11_NULL_WRITE_RESP)
        {
            // TODO: Lanzar excepcion
        }

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

        /* TODO: revisar valor de retorno se deberia lanzar excepcion acorde */

        if (ret != 0)
        {

        }

        return length;
    }

    @Override
    public void open()
    {
        // Verifica que se direccione una interfaz TCPIP
        if ( ! visaAddress.isTcpIp() )
        {
            /* TODO: lanzar excepcion mas acorde */
            throw new IllegalArgumentException("Visa address does not correspond to a GPIB-VXI device");
        }

        // Genera direccion de dispositivo
        String device = (visaAddress.hasField(VisaAddressFields.RESOURCE) ?
            visaAddress.getResource() : "gpib0,0");

        int ret = library.vxi11_open_device(visaAddress.getHostAddress(), /* direccion ip */
                clink, /* manejador / handler */
                visaAddress.getResource() /* direccion gpib instrumento */);

        /* TODO: revisar valor de retorno se deberia lanzar excepcion acorde */

        if (ret != 0)
        {

        }
    }

    @Override
    public void close()
    {
        int ret = library.vxi11_close_device(visaAddress.getHostAddress(), clink);

        /* TODO: revisar valor de retorno se deberia lanzar excepcion acorde */

        if (ret != 0)
        {

        }
    }
}
