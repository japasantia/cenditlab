package ve.gob.cendit.cenditlab.io.gpib;


import ve.gob.cendit.cenditlab.io.*;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;
import ve.gob.cendit.cenditlab.io.visa.VisaAddressFields;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class LinuxGpibConnection implements IGpibConnection
{
    public static int ERR = 0x8000;

    public static int TNONE    = 0;
    public static int T10US    = 1;
    public static int T30US    = 2;
    public static int T100US   = 3;
    public static int T300US   = 4;
    public static int T1MS     = 5;
    public static int T3MS     = 6;
    public static int T10MS    = 7;
    public static int T30MS    = 8;
    public static int T100MS   = 9;
    public static int T300MS   = 10;
    public static int T1S      = 11;
    public static int T3S      = 12;
    public static int T10S     = 13;
    public static int T30S     = 14;
    public static int T100S    = 15;
    public static int T300S    = 16;
    public static int T1000S   = 17;

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

    public void wait(int status)
    {
        library.ibwait(deviceDescriptor, status);
    }

    public void waitCompletion()
    {
        int status = 0;

        do
        {
            status = library.ThreadIbsta();
        }
        while ( (status & 0x100) != 1  && (status & 0x8000) != 0);
    }

    public void disableTimeout()
    {
        library.ibtmo(deviceDescriptor, TNONE);
    }

    public void setTimeout(int timeout)
    {
        library.ibtmo(deviceDescriptor, timeout);
    }

    private void checkStatusThrowIfError(int ibsta, String errorMessage)
    {
        if ((ibsta & ERR) != 0)
        {
            throw new LinuxGpibConnectionException(errorMessage, ibsta);
        }
    }
}