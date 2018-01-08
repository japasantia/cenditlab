package ve.gob.cendit.cenditlab.io.gpib;


import com.sun.jna.Platform;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;
import ve.gob.cendit.cenditlab.io.visa.VisaAddressFields;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class GpibConnection implements IGpibConnection
{
    private VisaAddress visaAddress;
    private ILinuxGpib library;
    private int deviceDescriptor;

    public static IGpibConnection CreateConnection(String address)
            throws Exception
    {
        // TODO: revisar generacion exceptciones. Crear excepcion GpibConnectionException
        VisaAddress va = VisaAddress.parseAddress(address);

        if (Platform.isWindows())
        {
            throw new NotImplementedException();
        }
        else if (Platform.isLinux())
        {
            return new GpibConnection(va);
        }
        else
        {
            throw new Exception("Not supported operating system");
        }
    }

    private GpibConnection(String address)
    {

    }

    private GpibConnection(VisaAddress address)
    {
        library = LinuxGpibLibrary.getLibrary();
        visaAddress = address;
    }

    @Override
    public void open()
    {
        int board = visaAddress.getBoard();
        int primaryAddress = visaAddress.getPrimaryAddress();
        int secondaryAddress = 0;

        if (visaAddress.hasField(VisaAddressFields.SECONDARY_ADDRESS))
        {
            secondaryAddress = visaAddress.getSecondaryAddress();
        }

        deviceDescriptor = library.ibdev(board, primaryAddress, secondaryAddress,
                ILinuxGpib.T3s, 1, 0);

        if (deviceDescriptor < 0)
        {
            throw new RuntimeException("Unable to open connection");
        }
    }

    @Override
    public void close()
    {
        int status = library.ibonl(deviceDescriptor, 0);
    }

    @Override
    public int write(byte[] buffer)
    {
        return library.ibwrt(deviceDescriptor, buffer, buffer.length);
    }

    @Override
    public int write(String buffer)
    {
        return library.ibwrt(deviceDescriptor, buffer, buffer.length());
    }

    @Override
    public int write(byte[] buffer, int offset, int length)
    {
        return 0;
    }

    @Override
    public int read(byte[] buffer)
    {
        return library.ibrd(deviceDescriptor, buffer, buffer.length);
    }

    @Override
    public int read(byte[] buffer, int offset, int length)
    {
        return 0;
    }

    @Override
    public String read()
    {
        ByteArrayOutputStream byteStream =
                new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        int status;
        String dataRead = null;

        try
        {
            do
            {
                status = read(buffer);
                bytesRead = library.ThreadIbcnt();
                byteStream.write(buffer,
                        byteStream.size(),
                        bytesRead);
            }
            while(bytesRead >= 1024);

            dataRead = byteStream.toString();

            byteStream.close();
        }
        catch (IOException ex)
        { }

        return dataRead;
    }
}
