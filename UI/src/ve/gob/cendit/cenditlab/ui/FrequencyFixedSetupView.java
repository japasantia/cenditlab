package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import ve.gob.cendit.cenditlab.data.FrequencyData;



public class FrequencyFixedSetupView extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-fixed-setup-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    ValueView fixedFrequencyValueView;

    FrequencyData fixedFrequencyData;

    public FrequencyFixedSetupView()
    {
        viewLoader.load(this, this);

        initialize();
    }

    private void initialize()
    {

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
