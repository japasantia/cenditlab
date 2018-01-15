package ve.gob.cendit.cenditlab.control;

import javafx.scene.Node;

public abstract class Activity
{
    private static final int BEGIN = 0;
    private static final int INITIALIZED = 1;
    private static final int LOADED = 2;
    private static final int RUNNING = 4;
    private static final int UNLOADED = 8;

    private String name;
    private ActivityFlow ownerActivityFlow;
    private IApplicationController applicationController;

    private int status = BEGIN;

    public Activity(String name)
    {
        setName(name);
    }

    public Activity(String name, ActivityFlow ownerActivityFlow)
    {
        setName(name);
        setActivityFlow(ownerActivityFlow);
    }

    public Activity(String name, IApplicationController controller)
    {
        setName(name);
        setApplicationController(controller);
    }


    protected void setName(String value)
    {
        name = value;
    }

    public String getName()
    {
        return name;
    }

    public void setApplicationController(IApplicationController controller)
    {
        if (controller == null)
        {
            throw new IllegalArgumentException("controller must not be null");
        }

        applicationController = controller;
    }

    public IApplicationController getApplicationController()
    {
        return applicationController;
    }

    public void setActivityFlow(ActivityFlow ownerActivityFlow)
    {
        if (this.ownerActivityFlow != null)
        {
            ownerActivityFlow.removeActivity(this);
        }

        this.ownerActivityFlow = ownerActivityFlow;
    }

    public ActivityFlow getActivityFlow()
    {
        return ownerActivityFlow;
    }

    void setStatus(int value)
    {
        status = value;
    }

    public boolean isInitialized()
    {
        return status == INITIALIZED || status == LOADED || status == RUNNING || status == UNLOADED;
    }

    public boolean isLoaded()
    {
        return status == LOADED || status == RUNNING;
    }

    public boolean isRunning()
    {
        return status == RUNNING;
    }

    public boolean isUnloaded()
    {
        return status == UNLOADED;
    }

    private boolean checkStatus(int value)
    {
        return (status & value) != 0;
    }

    public void executeInitialize()
    {
        if (isInitialized())
            return;

        initialize();
        setStatus(INITIALIZED);
    }

    public void executeLoad()
    {
        if (isLoaded())
            return;

        load();
        setStatus(LOADED);
    }

    public void executeRun()
    {
        if (isRunning())
            return;

        run();
        setStatus(RUNNING);
    }

    public void executeUnload()
    {
        if (isUnloaded())
            return;

        unload();
        setStatus(UNLOADED);
    }

    public abstract boolean canEnterFromActivity(Activity activity);
    public abstract boolean canExitToActivity(Activity activity);

    public abstract Node getView();

    protected abstract void initialize();

    protected abstract void load();
    
    protected abstract void run();
    
    protected abstract void unload();

    @Override
    public String toString()
    {
        return String.format("Activity: %s", getName());
    }
}
