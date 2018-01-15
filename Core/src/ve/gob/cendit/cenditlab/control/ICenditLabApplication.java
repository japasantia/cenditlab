package ve.gob.cendit.cenditlab.control;

import javafx.scene.Node;

public interface ICenditLabApplication
{
    void setMainView(Node mainView);
    void setTopToolbar(Node toolbar);

    void registerMeasurementManager(MeasurementManager measurementManager);
}
