package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import ve.gob.cendit.cenditlab.data.*;
import ve.gob.cendit.cenditlab.data.ValueData;

public class FrequencyRangePane extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-range-pane.fxml";

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

    public FrequencyRangePane()
    {
        this(new FrequencySetup());
    }

    public FrequencyRangePane(FrequencySetup setup)
    {
        viewLoader.load(this, this);

        setFrequencySetup(setup);

        initialize();
    }

    private void initialize()
    {
        setUpdateEnabled(false);

        minFrequencyValueView.addUpdateListener(source -> minMaxFrequenciesUpdate());
        maxFrequencyValueView.addUpdateListener(source -> minMaxFrequenciesUpdate());

        centralFrequencyValueView.addUpdateListener(source -> centralSpanFrequenciesUpdate());
        spanFrequencyValueView.addUpdateListener(source -> centralSpanFrequenciesUpdate());

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

    private void minMaxFrequenciesUpdate()
    {
        if ( ! isUpdateEnabled() )
        {
            return;
        }

        setUpdateEnabled(false);

        FrequencyData maxFrequencyData = frequencySetup.getMaxFrequencyData();
        FrequencyData minFrequencyData = frequencySetup.getMinFrequencyData();
        FrequencyData centralFrequencyData = frequencySetup.getCentralFrequencyData();
        FrequencyData spanFrequencyData = frequencySetup.getSpanFrequencyData();

        float centralFrequency =
                (maxFrequencyData.getMagnitude() + minFrequencyData.getMagnitude()) / 2.0f;

        float spanFrequency =
                (maxFrequencyData.getMagnitude() - minFrequencyData.getMagnitude());

        centralFrequencyData.setMagnitude(centralFrequency);
        spanFrequencyData.setMagnitude(spanFrequency);

        centralFrequencyValueView.setUpdateEnabled(false);
        spanFrequencyValueView.setUpdateEnabled(false);

        centralFrequencyValueView.setData(centralFrequencyData);
        spanFrequencyValueView.setData(spanFrequencyData);

        centralFrequencyValueView.setUpdateEnabled(true);
        spanFrequencyValueView.setUpdateEnabled(true);

        setUpdateEnabled(true);
    }

    private void centralSpanFrequenciesUpdate()
    {
        if ( ! isUpdateEnabled() )
        {
            return;
        }

        setUpdateEnabled(false);

        FrequencyData maxFrequencyData = frequencySetup.getMaxFrequencyData();
        FrequencyData minFrequencyData = frequencySetup.getMinFrequencyData();
        FrequencyData centralFrequencyData = frequencySetup.getCentralFrequencyData();
        FrequencyData spanFrequencyData = frequencySetup.getSpanFrequencyData();

        float maxFrequency =
                centralFrequencyData.getMagnitude() + spanFrequencyData.getMagnitude() / 2.0f;
        float minFrequency =
                centralFrequencyData.getMagnitude() - spanFrequencyData.getMagnitude() / 2.0f;

        maxFrequencyData.setMagnitude(maxFrequency);
        minFrequencyData.setMagnitude(minFrequency);

        minFrequencyValueView.setUpdateEnabled(false);
        maxFrequencyValueView.setUpdateEnabled(false);

        maxFrequencyValueView.setData(maxFrequencyData);
        minFrequencyValueView.setData(minFrequencyData);

        minFrequencyValueView.setUpdateEnabled(true);
        maxFrequencyValueView.setUpdateEnabled(true);

        setUpdateEnabled(true);
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
