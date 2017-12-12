package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.TasksSetupStepView;

public class TasksSetupStep extends MeasurementStep
{
    private boolean blocked = false;

    private TasksSetupStepView tasksSetupView;
    private System[] systemsArray;

    public TasksSetupStep(String name, System... systems)
    {
        super(name);

        systemsArray = systems;
    }

    @Override
    public Node getView()
    {
        return tasksSetupView;
    }

    @Override
    public boolean canEnterFromStep(MeasurementStep step)
    {
        return ! isBlocked();
    }

    @Override
    public boolean canExitToStep(MeasurementStep step)
    {
        return ! isBlocked();
    }

    @Override
    public void initialize()
    {
        tasksSetupView = new TasksSetupStepView();
    }

    @Override
    public void load()
    {
        tasksSetupView.loadSystems(systemsArray);
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
