package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import ve.gob.cendit.cenditlab.data.*;

public class FrequencyRangeSetupView extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-range-setup-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private ValueView minFrequencyValueView;

    @FXML
    private ValueView maxFrequencyValueView;

    @FXML
    private ValueView centralFrequencyValueView;

    @FXML
    private ValueView spanFrequencyValueView;

    @FXML
    private ValueView pointsValueView;

    private static final IValueValidator valueValidator =
            value -> FrequencyData.isValid(value);

    private FrequencySetup frequencySetup;

    private Boolean updateEnabled;

    public FrequencyRangeSetupView()
    {
        this(new FrequencySetup());
    }

    public FrequencyRangeSetupView(FrequencySetup setup)
    {
        viewLoader.load(this, this);

        setFrequencySetup(setup);

        initialize();
    }

    private void initialize()
    {
        setUpdateEnabled(false);

        minFrequencyValueView.setData(frequencySetup.getMinFrequencyData());
        maxFrequencyValueView.setData(frequencySetup.getMaxFrequencyData());
        centralFrequencyValueView.setData(frequencySetup.getCentralFrequencyData());
        spanFrequencyValueView.setData(frequencySetup.getSpanFrequencyData());
        pointsValueView.setData(frequencySetup.getAveragePointsNumericData());

        setUpdateEnabled(true);
    }

    public void setUpdateEnabled(boolean value)
    {
        updateEnabled = value;
    }

    public boolean isUpdateEnabled()
    {
        return updateEnabled;
    }

    public void setFrequencySetup(FrequencySetup setup)
    {
        if (setup != null)
        {
            frequencySetup = setup;
        }
    }

    public FrequencySetup getFrequencySetup()
    {
        return frequencySetup;
    }

    public boolean validate()
    {
        // TODO: revisar proceso de validación
        return minFrequencyValueView.validate(valueValidator) &&
                maxFrequencyValueView.validate(valueValidator) &&
                centralFrequencyValueView.validate(valueValidator) &&
                spanFrequencyValueView.validate(valueValidator);
    }
}
