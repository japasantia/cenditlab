package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.*;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.TasksExecutionActivityView;

import java.util.Arrays;

public class TasksExecutionActivity extends Activity
{
    private boolean blocked = false;

    private TasksExecutionActivityView tasksExecutionView;

    private System[] systemsArray;

    public TasksExecutionActivity(String name, IApplicationController applicationController, System... systems)
    {
        super(name, applicationController);

        systemsArray = systems;
    }

    @Override
    public Node getView()
    {
        return tasksExecutionView;
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
       tasksExecutionView = new TasksExecutionActivityView();
    }

    @Override
    public void load()
    {
        for (System system : systemsArray)
        {
            ComponentDescriptor[] taskDescriptors = system.getTaskDescriptors();

            for (ComponentDescriptor taskDescriptor : taskDescriptors)
            {
                tasksExecutionView.addTasks((Task) taskDescriptor.create(null));
            }
        }

        getApplicationController().setMainView(tasksExecutionView);
    }

    @Override
    public void run()
    {

    }

    @Override
    public void unload()
    {
        tasksExecutionView.unloadTasks();
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
