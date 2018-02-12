package ve.gob.cendit.cenditlab.io.serial;

import jssc.SerialPort;
import ve.gob.cendit.cenditlab.io.visa.VisaAddress;

public class SerialConnection implements ISerialConnection
{
    private SerialPort serialPort;
    private VisaAddress visaAddress;

    public SerialConnection(VisaAddress address)
    {
        if (address == null)
        {
            throw new IllegalArgumentException("Visa address must not be null");
        }
    }

    @Override
    public void open()
    {
        if (serialPort != null)
        {
            return;
        }

        // serialPort = new SerialPort();
    }

    @Override
    public void close()
    {

    }

    @Override
    public String read()
    {
        return null;
    }

    @Override
    public int read(byte[] buffer)
    {
        return 0;
    }

    @Override
    public int read(byte[] buffer, int offset, int length)
    {
        return 0;
    }

    @Override
    public int write(String data)
    {
        return 0;
    }

    @Override
    public int write(byte[] buffer)
    {
        return 0;
    }

    @Override
    public int write(byte[] buffer, int offset, int length)
    {
        return 0;
    }

    @Override
    public void waitForCompletion()
    {}
}
