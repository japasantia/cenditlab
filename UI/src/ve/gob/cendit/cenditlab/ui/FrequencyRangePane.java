package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import ve.gob.cendit.cenditlab.data.ValueData;
import ve.gob.cendit.cenditlab.data.FrequencyData;
import ve.gob.cendit.cenditlab.data.IValueValidator;
import ve.gob.cendit.cenditlab.data.ValueData;

import java.io.IOException;

public class FrequencyRangePane extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-range-pane.fxml";

    @FXML
    private ValueView minFrequencyDataInput;

    @FXML
    private ValueView maxFrequencyDataInput;

    @FXML
    private ValueView centralFrequencyDataInput;

    @FXML
    private ValueView spanFrequencyDataInput;

    @FXML
    private ValueView pointsValueView;

    private static final IValueValidator valueValidator =
            value -> FrequencyData.isValid(value);

    private FrequencyData minFrequencyData;
    private FrequencyData maxFrequencyData;
    private FrequencyData centralFrequencyData;
    private FrequencyData spanFrequencyData;
    private ValueData pointsData;

    private Boolean blockUpdate;

    public FrequencyRangePane()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_URL));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try
        {
            fxmlLoader.load();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        initialize();
    }

    private void initialize()
    {
        blockUpdate = true;

        minFrequencyData = new FrequencyData();
        maxFrequencyData = new FrequencyData();
        centralFrequencyData = new FrequencyData();
        spanFrequencyData = new FrequencyData();
        pointsData = new ValueData();

        minFrequencyDataInput.addUpdateListener(source -> minMaxFrequenciesUpdate());
        maxFrequencyDataInput.addUpdateListener(source -> minMaxFrequenciesUpdate());

        centralFrequencyDataInput.addUpdateListener(source -> centralSpanFrequenciesUpdate());
        spanFrequencyDataInput.addUpdateListener(source -> centralSpanFrequenciesUpdate());

        minFrequencyDataInput.setData(minFrequencyData);
        maxFrequencyDataInput.setData(maxFrequencyData);
        centralFrequencyDataInput.setData(centralFrequencyData);
        spanFrequencyDataInput.setData(spanFrequencyData);
        pointsValueView.setData(pointsData);

        /*
        minFrequencyDataInput.setChoiceUnits(FrequencyData.FIELD_UNITS);
        maxFrequencyDataInput.setChoiceUnits(FrequencyData.FIELD_UNITS);
        centralFrequencyDataInput.setChoiceUnits(FrequencyData.FIELD_UNITS);
        spanFrequencyDataInput.setChoiceUnits(FrequencyData.FIELD_UNITS);
        */

        blockUpdate = false;
    }

    private void minMaxFrequenciesUpdate()
    {
        if (blockUpdate)
        {
            return;
        }

        blockUpdate = true;

        float centralFrequency =
                (maxFrequencyData.getMagnitude() + minFrequencyData.getMagnitude()) / 2.0f;
        float spanFrequency =
                (maxFrequencyData.getMagnitude() - minFrequencyData.getMagnitude());

        centralFrequencyData.setMagnitude(centralFrequency);
        spanFrequencyData.setMagnitude(spanFrequency);

        centralFrequencyDataInput.setUpdateEnabled(false);
        spanFrequencyDataInput.setUpdateEnabled(false);

        centralFrequencyDataInput.setData(centralFrequencyData);
        spanFrequencyDataInput.setData(spanFrequencyData);

        centralFrequencyDataInput.setUpdateEnabled(true);
        spanFrequencyDataInput.setUpdateEnabled(true);

        blockUpdate = false;
    }

    private void centralSpanFrequenciesUpdate()
    {
        if (blockUpdate)
        {
            return;
        }

        blockUpdate = true;

        float maxFrequency =
                centralFrequencyData.getMagnitude() + spanFrequencyData.getMagnitude() / 2.0f;
        float minFrequency =
                centralFrequencyData.getMagnitude() - spanFrequencyData.getMagnitude() / 2.0f;

        maxFrequencyData.setMagnitude(maxFrequency);
        minFrequencyData.setMagnitude(minFrequency);

        minFrequencyDataInput.setUpdateEnabled(false);
        maxFrequencyDataInput.setUpdateEnabled(false);

        maxFrequencyDataInput.setData(maxFrequencyData);
        minFrequencyDataInput.setData(minFrequencyData);

        minFrequencyDataInput.setUpdateEnabled(true);
        maxFrequencyDataInput.setUpdateEnabled(true);

        blockUpdate = false;
    }

    public boolean validate()
    {
        return minFrequencyDataInput.validate(valueValidator) &&
                maxFrequencyDataInput.validate(valueValidator) &&
                centralFrequencyDataInput.validate(valueValidator) &&
                spanFrequencyDataInput.validate(valueValidator);
    }

    public FrequencyData getMinFrequency()
{
    return minFrequencyData;
}

    public FrequencyData getMaxFrequency()
    {
        return maxFrequencyData;
    }

    public FrequencyData getCentralFrequency()
    {
        return centralFrequencyData;
    }

    public FrequencyData getFrequencySpan()
    {
        return spanFrequencyData;
    }

    public ValueData getPoints()
    {
        return pointsData;
    }

    public void setMinFrequency(FrequencyData value)
    {
        if (value == null) return;

        minFrequencyData = value;

        centralSpanFrequenciesUpdate();
    }

    public void setMaxFrequency(FrequencyData value)
    {
        if (value == null) return;

        maxFrequencyData = value;

        centralSpanFrequenciesUpdate();
    }

    public void setCentralFrequency(FrequencyData value)
    {
        if (value == null) return;

        centralFrequencyData = value;

        centralSpanFrequenciesUpdate();
    }

    public void setSpanFrequency(FrequencyData value)
    {
        if (value == null) return;

        spanFrequencyData = value;

        centralSpanFrequenciesUpdate();
    }

    public void setPoints(ValueData value)
    {
        if (value == null) return;

        this.pointsData = value;
    }
}
