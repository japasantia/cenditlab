package ve.gob.cendit.cenditlab.control;

import ve.gob.cendit.cenditlab.data.DataContainer;

public abstract class Task extends Component
{
    private TaskState state = TaskState.NULL;
    private TaskState requestedState = TaskState.NULL;

    public Task(ComponentDescriptor descriptor)
    {
        super(descriptor);
    }

    public abstract DataContainer getSetupData();

    public abstract void run(TaskContext context);

    public void pause()
    {
        requestedState =  TaskState.PAUSED;
    }

    public void stop()
    {
        requestedState = TaskState.STOPPED;
    }

    public void resume()
    {
        requestedState = TaskState.RUNNING;
    }

    protected void setState(TaskState value)
    {
        state = value;
    }

    public TaskState getState()
    {
        return state;
    }

    public final boolean isRunning()
    {
        return state == TaskState.RUNNING;
    }

    public final boolean isStopped()
    {
        return  state == TaskState.STOPPED;
    }

    public final boolean isPaused()
    {
        return state == TaskState.PAUSED;
    }

    protected final boolean isPauseRequested()
    {
        return requestedState == TaskState.PAUSED;
    }

    protected final boolean isStopRequested()
    {
        return requestedState == TaskState.STOPPED;
    }

    protected final boolean isResumeRequested()
    {
        return requestedState == TaskState.RUNNING;
    }

    protected void enterPause()
    {
        state = TaskState.PAUSED;
        requestedState = TaskState.NULL;

        // while (isPaused());
    }

    protected void exitPause()
    {
        state = TaskState.RUNNING;
        requestedState = TaskState.NULL;
    }

    protected void enterRun()
    {
        state = TaskState.RUNNING;
        requestedState = TaskState.NULL;
    }

    protected void exitRun()
    {
        state = TaskState.STOPPED;
        requestedState = TaskState.NULL;
    }
}
