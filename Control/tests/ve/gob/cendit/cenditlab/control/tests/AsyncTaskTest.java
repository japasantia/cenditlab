package ve.gob.cendit.cenditlab.control.tests;

import javafx.scene.Node;
import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.control.AsyncTask;
import ve.gob.cendit.cenditlab.control.TaskContext;
import ve.gob.cendit.cenditlab.control.TaskState;
import ve.gob.cendit.cenditlab.data.FieldsContainer;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.util.Random;

public class AsyncTaskTest
{
    public static void main(String[] args)
    {
        try
        {
            TestAsyncTask task = new TestAsyncTask();

            task.callRun(TaskContext.LOAD, () -> print("Load finished"));

            while (task.isRunning()) ;

            if (task.isPaused())
            {
                task.resume();
            }

            while (task.isPaused()) ;

            task.stop();

            task.stop();

            while (!task.isStopped());
        }
        catch (Exception ex)
        {
            print(ex.getMessage());
        }
    }

    private static void print(String message)
    {
        System.out.println(message);
    }
}

class TestAsyncTask extends AsyncTask
{
    private static Random random = new Random();

    public TestAsyncTask()
    {
        super("TestAsyncTask", "Derived class of AsyncTask", null);
    }

    @Override
    public FieldsContainer getSetupFields() { return null; }

    @Override
    public Node getView(ViewType viewType) { return null; }

    @Override
    public Node getView(String viewId) { return null; }

    @Override
    public void run(TaskContext context)
    {
        try
        {
            setState(TaskState.RUNNING);

            this.enterPause();

            while (!isStopRequested())
            {
                long time = (long) random.nextInt(5000);
                System.out.printf("Sleep for %d seconds", time);
                Thread.sleep(time);
            }
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        finally
        {
            setState(TaskState.STOPPED);
        }
    }
}
