package ve.gob.cendit.cenditlab.measurements;

import java.util.List;

/**
 * Created by jarias on 17/07/17.
 */
public interface ITask
{
    VariablesBundle variables();
    List<ITask> subTasks();
    void addTaskListener(ITaskListener listener);
    void execute();
}
