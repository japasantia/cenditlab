package ve.gob.cendit.cenditlab.io.gpib;


import com.sun.jna.Native;
import com.sun.jna.Pointer;
import ve.gob.cendit.cenditlab.io.*;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;
import ve.gob.cendit.cenditlab.io.visa.VisaAddressFields;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class LinuxGpibConnection implements IGpibConnection
{
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
                ILinuxGpib.T3S, 1, 0);

        if (deviceDescriptor < 0)
        {
            throw new LinuxGpibConnectionException("Unable to open connection");
        }
    }

    @Override
    public void close()
    {
        int status = library.ibonl(deviceDescriptor, 0);

        checkStatusForException(status, "Unable to close connection");
    }

    @Override
    public synchronized int write(byte[] buffer, int offset, int length)
    {
        byte[] data = Arrays.copyOfRange(buffer, offset, offset + length - 1);

        return write(data);
    }

    @Override
    public synchronized int write(byte[] buffer)
    {
        int  status = library.ibwrt(deviceDescriptor,
                buffer, buffer.length);

        // Revisar status retornado en busca de error
        checkStatusForException(status, "Failed to write byte array to GPIB device");

        return library.ThreadIbcnt();
    }

    @Override
    public synchronized int write(String buffer)
    {
        int status = library.ibwrt(deviceDescriptor, buffer, buffer.length());

        // Revisar status retornado en busca de error
        checkStatusForException(status, "Failed to write string to GPIB device");

        return library.ThreadIbcnt();
    }

    @Override
    public synchronized int read(byte[] buffer, int offset, int length)
    {
        if  (length == 0)
        {
            return 0;
        }

        byte[] data = new byte[length];
        int status = library.ibrd(deviceDescriptor, data, length);

        // Revisar status retornado en busca de error
        checkStatusForException(status, "Failed to read byte array on GPIB device");

        System.arraycopy(data, 0, buffer, offset, length);

        return library.ThreadIbcnt();
    }

    @Override
    public synchronized int read(byte[] buffer)
    {
        int status = library.ibrd(deviceDescriptor, buffer, buffer.length);

        // Revisar status retornado en busca de error
        checkStatusForException(status, "Failed to read byte array on GPIB device");

        return library.ThreadIbcnt();
    }

    @Override
    public synchronized String read()
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

    public void deviceClear()
    {
        int ibsta = library.ibclr(deviceDescriptor);

        checkStatusForException(ibsta, "Error executing device clear");
    }

    public void interfaceClear()
    {
        int ibsta = library.ibsic(deviceDescriptor);

        checkStatusForException(ibsta, "Error executing interface clear");
    }

    public enum GpibLines
    {
        DAV(0x0100),
        NDAC(0x0200),
        NRFD(0x0400),
        IFC(0x0800),
        REN(0x1000),
        ATN(0x4000),
        EOI(0x8000);

        private int value;

        private GpibLines(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    public int getLine(GpibLines line)
    {
        short status = lineStatus();

        int validMask = line.getValue() >> 8;

        if ((status & validMask) == 0)
            return -1;

        return (status & line.getValue()) ;
    }

    public short lineStatus()
    {
        long memory = Native.malloc(2);
        Pointer pointer = new Pointer(memory);

        int ibsta = library.iblines(deviceDescriptor, pointer);

        return pointer.getShort(0);
    }

    public byte serialPoll()
    {
        long memory = Native.malloc(1);
        Pointer pointer = new Pointer(memory);

        //byte[] result = {0};

        int ibsta = library.ibrsp(deviceDescriptor, pointer);

        return pointer.getByte(0);
    }

    public void waitForStatus(int status)
    {
        library.ibwait(deviceDescriptor, status);
    }

    public void waitForCompletion()
    {
        // Espera culminacion de operacion IO
        // la funcion opera en dispositivo y tarjeta
        // a diferencia de espera a RQS que solo opera en tarjeta
        library.ibwait(deviceDescriptor, ILinuxGpib.CMPL);
    }

    public void waitForServiceRequest()
    {
        //TODO: probar opciones, hay dos

        // Espera por bit RQS en ibsta.
        // Funciona unicamente si se ha establecido serial poll automatico
        // con ibconfig
        library.ibconfig(deviceDescriptor, ILinuxGpib.AUTOPOLL, 1);
        library.ibconfig(deviceDescriptor, ILinuxGpib.SPOLLBIT, 1);
        library.ibwait(deviceDescriptor, ILinuxGpib.RQS);

        /*
        // Espera por medio de llamada a funcion
        long memory = Native.malloc(2);
        Pointer pointer = new Pointer(memory);

        library.WaitSRQ(deviceDescriptor, pointer);
        */
    }

    public void disableTimeout()
    {
        library.ibtmo(deviceDescriptor, ILinuxGpib.TNONE);
    }

    public void setTimeout(int millis)
    {
        millis = (millis > 0 ? millis : ILinuxGpib.T1MS);

        int timeout = (int)(5.02134F + 0.86856F * Math.log(millis));
        timeout = Math.min(timeout, ILinuxGpib.T1000S);

        int ibsta = library.ibtmo(deviceDescriptor, timeout);

        checkStatusForException(ibsta, "error setting timeout");
    }

    private void checkStatusForException(int ibsta, String errorMessage)
    {
        if ((ibsta & ILinuxGpib.ERR) != 0 || (ibsta & ILinuxGpib.TIMO) != 0)
        {
            throw new LinuxGpibConnectionException(errorMessage, ibsta);
        }
    }
}
