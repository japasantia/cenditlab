package ve.gob.cendit.cenditlab.tasks;

import ve.gob.cendit.cenditlab.data.DataSet;

public interface ITask
{
    String INPUT_DATA = "Input Data";
    String OUTPUT_DATA = "Output Data";
    String SETUP_DATA = "Setup Data";

    String getName();
    DataSet getDataSet(String category);

    void execute();
}
