package ve.gob.cendit.cenditlab.measurements;

/**
 * Created by jarias on 17/07/17.
 */
public interface ITaskListener
{
    void onEntry(ITask task);
    void onExit(ITask task);
    void onError(ITask task);
    void executed(Task task);
}
