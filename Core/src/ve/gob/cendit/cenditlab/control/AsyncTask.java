package ve.gob.cendit.cenditlab.control;

public abstract class AsyncTask extends Task
{
    private Thread thread;

    public AsyncTask(ComponentDescriptor descriptor)
    {
        super(descriptor);
    }

    public void callRun(TaskContext context, Runnable onFinish)
    {
        if (thread != null && thread.isAlive())
            return;

        thread = new Thread(() -> runProc(context, onFinish));
        thread.setName(getName());
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected void enterPause()
    {
        synchronized (this)
        {
            if (thread != null && thread.isAlive() && Thread.currentThread() == thread)
            {
                super.enterPause();

                try
                {
                    while (isPaused() && ! isResumeRequested())
                    {
                        wait();
                    }
                }
                catch (InterruptedException ex)
                {  }
                finally
                {
                    exitPause();
                }
            }
        }
    }

    @Override
    public void resume()
    {
        synchronized(this)
        {
            if (thread != null && thread.isAlive())
            {
                super.resume();

                notifyAll();
            }
        }
    }

    @Override
    public void stop()
    {
        if (thread != null && thread.isAlive())
        {
            if (isStopRequested())
            {
                thread.stop();
            }
            else
            {
                super.stop();
                thread.interrupt();
                setState(TaskState.STOPPED);
            }
        }
    }

    private void runProc(TaskContext context, Runnable onFinish)
    {
        run(context);

        setState(TaskState.STOPPED);

        if (onFinish != null)
        {
            onFinish.run();
        }
    }
}

