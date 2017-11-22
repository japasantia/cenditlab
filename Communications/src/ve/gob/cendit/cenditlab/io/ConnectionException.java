package ve.gob.cendit.cenditlab.io;

/**
 * Created by jarias on 31/08/17.
 */
public class ConnectionException extends RuntimeException    
{
    public ConnectionException()
    {
        super();
    }

    public ConnectionException(String message)
    {
        super(message);
    }

    public ConnectionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ConnectionException(Throwable cause)
    {
        super(cause);
    }
}
