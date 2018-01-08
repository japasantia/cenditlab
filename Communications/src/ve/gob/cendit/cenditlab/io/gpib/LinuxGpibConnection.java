package ve.gob.cendit.cenditlab.io.gpib;


import ve.gob.cendit.cenditlab.io.*;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;
import ve.gob.cendit.cenditlab.io.visa.VisaAddressFields;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class LinuxGpibConnection implements IGpibConnection
{
    private static int ERR = 0x8000;

    private static ILinuxGpib library = LinuxGpibLibrary.getLibrary();

    private VisaAddress visaAddress;
    private int deviceDescriptor;

    public LinuxGpibConnection(String address)
    {
        this(new VisaAddress(address));
    }

    public LinuxGpibConnection(VisaAddress address)
    {
        deviceDescriptor = -1;
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
            throw new LinuxGpibConnectionException("Unable to open connection");
        }
    }

    @Override
    public void close()
    {
        int status = library.ibonl(deviceDescriptor, 0);

        checkStatusThrowIfError(status, "Unable to close connection");
    }

    @Override
    public int write(byte[] buffer, int offset, int length)
    {
        byte[] data = Arrays.copyOfRange(buffer, offset, offset + length - 1);

        return write(data);
    }

    @Override
    public int write(byte[] buffer)
    {
        int  status = library.ibwrt(deviceDescriptor,
                buffer, buffer.length);

        // Revisar status retornado en busca de error
        checkStatusThrowIfError(status, "Failed to write byte array to GPIB device");

        return library.ThreadIbcnt();
    }

    @Override
    public int write(String buffer)
    {
        int status = library.ibwrt(deviceDescriptor, buffer, buffer.length());

        // Revisar status retornado en busca de error
        checkStatusThrowIfError(status, "Failed to write string to GPIB device");

        return library.ThreadIbcnt();
    }

    @Override
    public int read(byte[] buffer, int offset, int length)
    {
        if  (length == 0)
        {
            return 0;
        }

        byte[] data = new byte[length];
        int status = library.ibrd(deviceDescriptor, data, length);

        // Revisar status retornado en busca de error
        checkStatusThrowIfError(status, "Failed to read byte array on GPIB device");

        System.arraycopy(data, 0, buffer, offset, length);

        return library.ThreadIbcnt();
    }

    @Override
    public int read(byte[] buffer)
    {
        int status = library.ibrd(deviceDescriptor, buffer, buffer.length);

        // Revisar status retornado en busca de error
        checkStatusThrowIfError(status, "Failed to read byte array on GPIB device");

        return library.ThreadIbcnt();
    }

    @Override
    public String read()
    {
        ByteArrayOutputStream byteStream =
                new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead = 0;

        String dataRead = null;

        try
        {
            do
            {
                bytesRead = read(buffer);
                byteStream.write(buffer,
                        byteStream.size(),
                        bytesRead);
            }
            while(bytesRead >= 1024);

            dataRead = byteStream.toString();

            byteStream.close();
        }
        catch (IOException ex)
        {
            throw new ConnectionException("Java IO exception", ex);
        }
        catch (LinuxGpibConnectionException ex)
        {
            throw new LinuxGpibConnectionException("Failed to read string on GPIB device", ex);
        }

        return dataRead;
    }

    private void checkStatusThrowIfError(int ibsta, String errorMessage)
    {
        if ((ibsta & ERR) != 0)
        {
            throw new LinuxGpibConnectionException(errorMessage, ibsta);
        }
    }
}
