package ve.gob.cendit.cenditlab.io;

/**
 * Created by jarias on 20/06/17.
 */
public class InvalidVisaAddressException extends RuntimeException
{
    public InvalidVisaAddressException()
    {
        super();
    }

    public InvalidVisaAddressException(String message)
    {
        super(message);
    }

    public InvalidVisaAddressException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidVisaAddressException(Throwable cause)
    {
        super(cause);
    }
}
