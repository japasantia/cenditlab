package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.Activity;
import ve.gob.cendit.cenditlab.control.IApplicationController;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.TasksSetupActivityView;

public class TasksSetupActivity extends Activity
{
    private boolean blocked = false;

    private TasksSetupActivityView tasksSetupView;
    private System[] systemsArray;

    public TasksSetupActivity(String name, IApplicationController applicationController, System... systems)
    {
        super(name, applicationController);

        systemsArray = systems;
    }

    @Override
    public Node getView()
    {
        return tasksSetupView;
    }

    @Override
    public boolean canEnterFromActivity(Activity activity)
    {
        return ! isBlocked();
    }

    @Override
    public boolean canExitToActivity(Activity activity)
    {
        return ! isBlocked();
    }

    @Override
    public void initialize()
    {
        tasksSetupView = new TasksSetupActivityView();
    }

    @Override
    public void load()
    {
        tasksSetupView.loadSystems(systemsArray);
        getApplicationController().setMainView(tasksSetupView);
    }

    @Override
    public void run()
    {

    }

    @Override
    public void unload()
    {
        tasksSetupView.unloadSystems();
    }

    public void setBlocked(boolean value)
    {
        blocked = value;
    }

    public boolean isBlocked()
    {
        return blocked;
    }
}
