package ve.gob.cendit.cenditlab.measurements;

/**
 * Created by jarias on 17/07/17.
 */
public interface TaskListener
{
    void onEntry(Task task);
    void onExit(Task task);
    void onError(Task task);
    void executed(Task task);
}
