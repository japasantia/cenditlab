package ve.gob.cendit.cenditlab.scpi;

import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.Arrays;
import java.util.Objects;

public class ScpiExecutor implements ICommand
{
    private ICommand[] commands;

    private boolean threadEnabled;
    private boolean executing;
    private boolean success;
    private boolean stopRequested;

    private Thread executionThread;

    private ICommand onSuccessCommand;
    private ICommand onErrorCommand;

    private Exception lastException;

    public ScpiExecutor(ICommand... commands)
    {
        this.commands = Objects.requireNonNull(commands);

        threadEnabled = false;
    }

    @Override
    public void execute(IConnection connection)
    {
        if ( ! isExecuting() )
        {
            setExecuting(true);

            success = false;
            stopRequested = false;
            lastException = null;

            if (isThreadEnabled())
                threadExecute(connection);
            else
                commandsExecute(connection);
        }
    }

    private boolean singleCommandExecute(IConnection connection, ICommand command)
    {
        try
        {
            command.execute(connection);
        }
        catch (Exception ex)
        {
            // TODO: se ejecuta proceso de error
            return false;
        }

        return true;
    }

    private void commandsExecute(IConnection connection)
    {
        success = true;

        try
        {
            for (ICommand command : commands)
            {
                if (isStopRequested())
                {
                    success = false;
                    break;
                }

                command.execute(connection);
            }
        }
        catch (Exception ex)
        {
            // TODO: se ejecuta proceso de error
            lastException = ex;
        }

        setExecuting(false);

        if (success)
            onExecutionSuccess(connection);
        else
            onExecutionError(connection);

        /*
        for (ICommand command : commands)
        {
            boolean ret = singleCommandExecute(connection, command);

            if (! ret )
            {
                onExecutionError(connection, ex);
                return;
            }
        }

        setExecuting(false);
        onExecutionSuccess(connection);
        */
    }

    private void threadExecute(IConnection connection)
    {
        executionThread = new Thread(() -> commandsExecute(connection));
        executionThread.start();
    }

    public void setThreadEnabled(boolean value)
    {
        threadEnabled = value;
    }

    public boolean isThreadEnabled()
    {
        return threadEnabled;
    }

    protected void setExecuting(boolean value)
    {
        executing = value;
    }

    public boolean isExecuting()
    {
        return executing;
    }

    public void stop()
    {
        stopRequested = true;
    }

    public void waitCompletion()
    {
        // TODO: mejorar la espera de culminacion

        if (isThreadEnabled())
        {
            try
            {
                executionThread.join();
            }
            catch (InterruptedException ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }

    public boolean isStopRequested()
    {
        return stopRequested;
    }

    public boolean hasSuccess()
    {
        return success;
    }

    public Exception getLastException()
    {
        return lastException;
    }

    public void setOnSuccessCommand(ICommand command)
    {
        onSuccessCommand = command;
    }

    public void setOnErrorCommand(ICommand command)
    {
        onErrorCommand = command;
    }

    private void onExecutionSuccess(IConnection connection)
    {
        if (onSuccessCommand != null)
            onSuccessCommand.execute(connection);
    }

    private void onExecutionError(IConnection connection)
    {
        if (onErrorCommand != null)
            onErrorCommand.execute(connection);
    }

    public static void execute(IConnection connection, Scpi... commands)
    {
        try
        {
            Arrays.stream(commands)
                    .forEach(command -> command.execute(connection));
        }
        catch (Exception ex)
        {
            // TODO: preparar excepcion custom ScpiException
            throw ex;
        }
    }
}
