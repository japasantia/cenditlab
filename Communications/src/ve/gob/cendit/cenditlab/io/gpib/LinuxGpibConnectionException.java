package ve.gob.cendit.cenditlab.io.gpib;

import ve.gob.cendit.cenditlab.io.ConnectionException;

/**
 * Created by jarias on 31/08/17.
 */
class LinuxGpibConnectionException extends ConnectionException
{
    private static final String[] ERROR_MESSAGES =
    {
        "A system call has failed. ibcnt/ibcntl will be set to the value of errno.",
        "Your interface board needs to be controller-in-charge, but is not. ",
        "You have attempted to write data or command bytes, but there are no listeners currently addressed.",
        "The interface board has failed to address itself properly before starting an io operation.",
        "One or more arguments to the function call were invalid.",
        "The interface board needs to be system controller, but is not.",
        "A read or write of data bytes has been aborted, possibly due to a timeout  or reception of a device clear command.",
        "The GPIB interface board does not exist, its driver is not loaded, or it is not configured properly.",
        "Not used (DMA error), included for compatibility purposes.",
        "Function call can not proceed due to an asynchronous IO operation (ibrda(), ibwrta(), or ibcmda()) in progress.",
        "Incapable of executing function call, due the capability, or the capability being disabled in software.",
        "File system error. ibcnt/ibcntl will be set to the value of errno.",
        "An attempt to write command bytes to the bus has timed out.",
        "One or more serial poll status bytes have been lost. This can occur due to too many status bytes accumulating" +
            "(through automatic serial polling) without being read.",
        "The serial poll request service line is stuck on. This can occur if a physical device on the bus" +
            "requests service, but its GPIB address has not been opened (via ibdev() for example) by any process." +
            "Thus the automatic serial polling routines are unaware of the device’s existence and will never" +
            "serial poll it.",
        "",
        "",
        "",
        "This error can be returned by ibevent(), FindLstn(), or FindRQS(). See their descriptions for more information."
    };

    public static final int EDVR = 0;
    public static final int ECIC = 1;
    public static final int ENOL = 2;
    public static final int EADR = 3;
    public static final int EARG = 4;
    public static final int ESAC = 5;
    public static final int EABO = 6;
    public static final int ENEB = 7;
    public static final int EDMA = 8;
    public static final int EOIP = 10;
    public static final int ECAP = 11;
    public static final int EFSO = 12;
    public static final int EBUS = 14;
    public static final int ESTB = 15;
    public static final int ESRQ = 16;
    public static final int ETAB = 20;

    public LinuxGpibConnectionException(String message)
    {
        super(makeMessage(message));
    }

    public LinuxGpibConnectionException(String message, Exception ex)
    {
        super(makeMessage(message), ex);
    }

    public LinuxGpibConnectionException(String message, int ibsta)
    {
        super(makeMessage(message, ibsta));
    }

    private static String makeMessage(String message)
    {
        return makeMessage(message,
                LinuxGpibLibrary.getLibrary().ThreadIbsta());
    }

    private static String makeMessage(String message, int ibsta)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(message);

        int iberr = LinuxGpibLibrary.getLibrary().ThreadIberr();

        if (iberr >= 0 && iberr <= 20)
        {
            sb.append(String.format(" (%s)", ERROR_MESSAGES[iberr]));
        }

        sb.append(String.format(" (ibsta: %s)", Integer.toBinaryString(ibsta)));

        return sb.toString();
    }
}
