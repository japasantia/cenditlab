package ve.gob.cendit.cenditlab.control;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.data.FieldsContainer;

import java.lang.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Task extends Component
{
    private Thread thread;
    private TaskState taskState = TaskState.UNLOADED;

    private TaskProcedure currentProcedure;

    public Task(String name, String description, Image iconImage)
    {
        super(name, description, iconImage);
    }

    public void load()
    {
        if (isUnloaded())
        {
            currentProcedure = new TaskProcedure(() -> onLoad());

            currentProcedure.setOnTerminate(
                    success -> setState(success ? TaskState.LOADED : TaskState.UNLOADED));
        }
    }

    public void run()
    {
        if (isReady())
        {
            currentProcedure = new TaskProcedure(() -> onRun());

            currentProcedure.setOnTerminate(success -> setState(TaskState.TERMINATED));

            currentProcedure.run();

            setState(TaskState.RUNNING);
        }
    }

    public void unload()
    {
        if (isFinalized())
        {
            currentProcedure = new TaskProcedure(this::onUnload);
            currentProcedure.setOnTerminate(success -> setState(TaskState.UNLOADED));
        }
    }

    public void pause()
    {
        currentProcedure.pause();
    }

    public void resume()
    {
        currentProcedure.resume();
    }

    public void stop()
    {
        currentProcedure.stop();
    }

    public final boolean isUnloaded()
    {
        return taskState == TaskState.UNLOADED;
    }

    public final boolean isLoaded()
    {
        return taskState == TaskState.LOADED;
    }

    public final boolean isRunning()
    {
        return taskState == TaskState.RUNNING;
    }

    public final boolean isTerminated()
    {
        return taskState == TaskState.TERMINATED;
    }

    public boolean isPaused()
    {
        return currentProcedure.isPaused();
    }

    public final boolean isStopped()
    {
        return currentProcedure.isStopped();
    }

    public final boolean isFinalized()
    {
        return isStopped() || isTerminated();
    }

    public final boolean isReady()
    {
        return isLoaded() || isFinalized();
    }

    protected final void enterPause()
    {
        currentProcedure.enterPause();
    }

    protected final boolean isPauseRequested()
    {
        return currentProcedure.isPauseRequested();
    }

    protected final boolean isStopRequested()
    {
        return currentProcedure.isStopRequested();
    }

    public abstract FieldsContainer getSetupFields();

    protected boolean onLoad()
    {
        return true;
    }

    protected boolean onRun()
    {
        return true;
    }

    protected boolean onUnload()
    {
        return true;
    }

    protected abstract void execute();

    private void setState(TaskState state)
    {
        taskState = state;
    }

    private class TaskProcedure
    {
        private static final int BEGIN = 0;
        private static final int ACTIVE = 1;
        private static final int PAUSED = 2;
        private static final int STOPPED = 3;
        private static final int TERMINATED = 4;

        private Thread thread;
        private boolean procedureReturn = false;

        private Supplier<Boolean> procedure;
        private Consumer<Boolean> onTerminate;

        private volatile int state = BEGIN;
        private volatile int requestState = BEGIN;

        public TaskProcedure(Supplier<Boolean> procedure)
        {
            procedureReturn = false;
            this.procedure = procedure;
        }

        public void run()
        {
            state = ACTIVE;
            requestState = ACTIVE;

            thread = new Thread(this::callThreadProcedure);
            thread.setDaemon(true);

            thread.start();
        }

        public void pause()
        {
            requestState = PAUSED;
        }

        public void resume()
        {
            state = ACTIVE;
            requestState = ACTIVE;

            notify();
        }

        public void stop()
        {
            if (state == ACTIVE)
            {
                if (requestState == ACTIVE)
                {
                    thread.interrupt();
                    requestState = STOPPED;
                }
                else if (requestState == STOPPED)
                {
                    thread.stop();
                    state = STOPPED;
                }
            }
        }

        public boolean isPauseRequested()
        {
            return requestState == PAUSED;
        }

        public boolean isStopRequested()
        {
            return requestState == STOPPED;
        }

        public boolean isReady()
        {
            return state == BEGIN || isStopped() || isTerminated();
        }

        public boolean isPaused()
        {
            return state == PAUSED;
        }

        public boolean isActive()
        {
            return state == ACTIVE;
        }

        public boolean isTerminated()
        {
            return state == TERMINATED;
        }

        public boolean isStopped()
        {
            return state == STOPPED;
        }

        public boolean isFinalized()
        {
            return isTerminated() || isStopped();
        }

        public void enterPause()
        {
            // FIXME: revisar si es necesario synchronized
            try
            {
                state = PAUSED;

                while (isPaused())
                {
                    wait();
                }
            }
            catch (InterruptedException ex)
            {
                requestState = STOPPED;
                Thread.currentThread().interrupt();
            }
        }

        public boolean success()
        {
            return procedureReturn;
        }

        public void setOnTerminate(Consumer<Boolean> handler)
        {
            onTerminate = handler;
        }

        private void callTerminationHandler(boolean procedureReturn)
        {
            if (onTerminate != null)
            {
                onTerminate.accept(procedureReturn);
            }
        }

        private void callThreadProcedure()
        {
            procedureReturn = procedure.get();

            state = TERMINATED;
            requestState = BEGIN;

            callTerminationHandler(procedureReturn);
        }
    }
}
