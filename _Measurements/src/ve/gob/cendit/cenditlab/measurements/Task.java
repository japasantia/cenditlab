package ve.gob.cendit.cenditlab.measurements;

import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.List;

/**
 * Created by jarias on 17/07/17.
 */
public class Task implements ITask
{
    @Override
    public VariablesBundle variables()
    {
        return null;

    }

    @Override
    public List<ITask> subTasks()
    {
        return null;
    }

    @Override
    public void addTaskListener(ITaskListener listener)
    {

    }

    @Override
    public void execute()
    {

    }
}
