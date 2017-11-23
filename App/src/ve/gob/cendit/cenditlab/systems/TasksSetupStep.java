package ve.gob.cendit.cenditlab.systems;

import ve.gob.cendit.cenditlab.app.CenditLabApplication;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.TasksSetupStepView;
import ve.gob.cendit.cenditlab.ui.TasksSetupStepViewProto1;

public class TasksSetupStep extends MeasurementStep
{
    private boolean blocked = false;

    private TasksSetupStepViewProto1 tasksSetupView;
    private System[] systemsArray;

    public TasksSetupStep(String name, System... systems)
    {
        super(name);

        systemsArray = systems;
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
        tasksSetupView = new TasksSetupStepViewProto1();
    }

    @Override
    public void load()
    {
        CenditLabApplication.getApp().setCenterContainer(tasksSetupView);
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
