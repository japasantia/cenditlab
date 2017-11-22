package ve.gob.cendit.cenditlab.measurements;

/**
 * Created by jarias on 17/08/17.
 */
public interface ITask2
{
    String getName();

    TaskData getInputData();
    TaskData getOutputData();

    void Execute();
}
