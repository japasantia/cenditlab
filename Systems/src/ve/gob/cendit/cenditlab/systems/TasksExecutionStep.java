package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.TasksExecutionStepView;

import java.util.Arrays;


public class TasksExecutionStep extends MeasurementStep
{
    private boolean blocked = false;

    private TasksExecutionStepView tasksExecutionStepView;

    private System[] systemsArray;

    public TasksExecutionStep(String name, System... systems)
    {
        super(name);

        systemsArray = systems;
    }

    @Override
    public Node getView()
    {
        return  tasksExecutionStepView;
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
       tasksExecutionStepView = new TasksExecutionStepView();
    }

    @Override
    public void load()
    {
        Arrays.stream(systemsArray)
                .forEach(system -> tasksExecutionStepView.addTasks(system.getTasks()));
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
