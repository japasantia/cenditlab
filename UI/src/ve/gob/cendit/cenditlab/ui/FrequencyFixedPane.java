package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import ve.gob.cendit.cenditlab.data.FrequencyData;

import java.io.IOException;

public class FrequencyFixedPane extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-fixed-pane.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    ValueView fixedFrequencyValueView;

    FrequencyData fixedFrequencyData;

    public FrequencyFixedPane()
    {
        viewLoader.load(this, this);

        initialize();
    }

    private void initialize()
    {
        fixedFrequencyValueView.setChoiceUnits(FrequencyData.FIELD_UNITS);
    }

    public void setFixedFrequency(FrequencyData value)
    {
        fixedFrequencyData = value;

        fixedFrequencyValueView.setData(value);
    }

    public FrequencyData getFixedFrequency()
    {
        return fixedFrequencyData;
    }
}
