package ve.gob.cendit.cenditlab.control;

import javafx.scene.Node;
import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.data.DataContainer;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class AsyncTaskProto1 extends Task
{
    private Thread thread;
    private Task task;

    public AsyncTaskProto1(Task task)
    {
        super(task.getComponentDescriptor());

        if (task == null)
        {
            throw new IllegalArgumentException("task must not be null");
        }

        this.task = task;
    }

    @Override
    public DataContainer getSetupData()
    {
        return task.getSetupData();
    }

    @Override
    public Node getView(ViewType viewType)
    {
        return task.getView(viewType);
    }

    @Override
    public void run(TaskContext context)
    {
        if (thread != null && thread.isAlive())
            return;

        thread = new Thread(() -> runProc(context));
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
                task.enterPause();

                try
                {
                    while (task.isPaused() && ! task.isResumeRequested())
                    {
                        wait();
                    }
                }
                catch (InterruptedException ex)
                {  }
                finally
                {
                    task.exitPause();
                }
            }
        }
    }

    @Override
    protected void exitPause()
    {
        task.exitPause();
    }

    @Override
    protected void enterRun()
    {
        task.enterRun();
    }

    @Override
    protected void exitRun()
    {
        task.exitRun();
    }

    @Override
    public void resume()
    {
        synchronized(this)
        {
            if (thread != null && thread.isAlive())
            {
                task.resume();

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
                setState(TaskState.STOPPED);
            }
            else
            {
                task.stop();
                thread.interrupt();
            }
        }
    }

    private void runProc(TaskContext context)
    {
        run(context);

        setState(TaskState.STOPPED);
    }
}
