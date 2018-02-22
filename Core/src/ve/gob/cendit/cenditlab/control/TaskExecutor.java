package ve.gob.cendit.cenditlab.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TaskExecutor
{
    private List<Task> tasksList = new ArrayList<>();

    private Thread thread;

    private Runnable onSuccessRunnable;
    private Runnable onErrorRunnable;

    private boolean success;

    public TaskExecutor()
    {

    }

    public TaskExecutor(Task... taskArgs)
    {
        setTasks(taskArgs);
    }

    public void setTasks(Task... taskArgs)
    {
        tasksList.clear();

        addTasks(taskArgs);
    }

    public void addTasks(Task... taskArgs)
    {
        Objects.requireNonNull(taskArgs);

        Arrays.stream(taskArgs)
                .forEach(t -> tasksList.add(t));
    }

    public void execute()
    {
        if (isRunning())
            return;

        thread = new Thread(this::executeAllTasks);
        thread.setName("ThreadExecutor");

        thread.start();
    }

    private void executeAllTasks()
    {
        try
        {
            tasksList.stream()
                .forEach(this::executeOneTask);

            setSuccess(true);
        }
        catch (Exception ex)
        {
            setSuccess(false);
        }
        finally
        {
            onExecutionTermination();
        }
    }

    private void executeOneTask(Task task)
    {
        task.run(TaskContext.RUN);
    }


    private void setSuccess(boolean value)
    {
        success = value;
    }

    public boolean hasSucceeded()
    {
        return success;
    }

    public boolean isRunning()
    {
        return thread != null && thread.isAlive();
    }

    public void setOnSuccessHandler(Runnable runnable)
    {
        onSuccessRunnable = runnable;
    }

    public void setOnErrorHandler(Runnable runnable)
    {
        onErrorRunnable = runnable;
    }

    private void onExecutionTermination()
    {
        if (hasSucceeded())
            onExecutionSuccess();
        else
            onExecutionError();
    }

    private void onExecutionSuccess()
    {
        if (onSuccessRunnable != null)
            onSuccessRunnable.run();
    }

    private void onExecutionError()
    {
        if (onErrorRunnable != null)
            onErrorRunnable.run();
    }
}
