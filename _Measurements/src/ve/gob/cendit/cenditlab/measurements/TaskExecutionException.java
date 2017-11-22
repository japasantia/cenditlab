package ve.gob.cendit.cenditlab.measurements;

/**
 * Created by jarias on 19/07/17.
 */
public class TaskExecutionException extends Exception
{
    public TaskExecutionException()
    {
        super();
    }

    public TaskExecutionException(String message)
    {
        super(message);
    }

    public TaskExecutionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public TaskExecutionException(Throwable cause)
    {
        super(cause);
    }
}
