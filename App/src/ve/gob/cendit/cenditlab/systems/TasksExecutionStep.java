package ve.gob.cendit.cenditlab.systems;

import ve.gob.cendit.cenditlab.app.CenditLabApplication;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.ui.*;

import java.util.Arrays;


public class TasksExecutionStep extends MeasurementStep
{
    private boolean blocked = false;

    private TasksExecutionStepViewProto1 tasksExecutionStepView;

    private System[] systemsArray;

    public TasksExecutionStep(String name, System... systems)
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
       tasksExecutionStepView = new TasksExecutionStepViewProto1();
    }

    @Override
    public void load()
    {
        Arrays.stream(systemsArray)
                .forEach(system -> tasksExecutionStepView.addTasks(system.getTasks()));

        CenditLabApplication.getApp().setCenterContainer(tasksExecutionStepView);

    }

    @Override
    public void run()
    {

    }

    @Override
    public void unload()
    {
        tasksExecutionStepView.unloadTasks();
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
