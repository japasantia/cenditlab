package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import ve.gob.cendit.cenditlab.data.Field;
import ve.gob.cendit.cenditlab.data.FrequencyField;
import ve.gob.cendit.cenditlab.data.IValueValidator;

import java.io.IOException;

public class FrequencyRangePane extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-range-pane.fxml";

    @FXML
    private FieldInput minFrequencyFieldInput;

    @FXML
    private FieldInput maxFrequencyFieldInput;

    @FXML
    private FieldInput centralFrequencyFieldInput;

    @FXML
    private FieldInput spanFrequencyFieldInput;

    @FXML
    private FieldInput pointsFieldInput;

    private static final IValueValidator valueValidator =
            value -> FrequencyField.isValid(value);

    private FrequencyField minFrequencyField;
    private FrequencyField maxFrequencyField;
    private FrequencyField centralFrequencyField;
    private FrequencyField spanFrequencyField;
    private Field pointsField;

    private Boolean blockUpdate;

    public FrequencyRangePane()
    {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().
                        getResource(FXML_URL));

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

        minFrequencyField = new FrequencyField();
        maxFrequencyField = new FrequencyField();
        centralFrequencyField = new FrequencyField();
        spanFrequencyField = new FrequencyField();
        pointsField = new Field();

        minFrequencyFieldInput.addUpdateListener(() -> minMaxFrequenciesUpdate());
        maxFrequencyFieldInput.addUpdateListener(() -> minMaxFrequenciesUpdate());

        centralFrequencyFieldInput.addUpdateListener(() -> centralSpanFrequenciesUpdate());
        spanFrequencyFieldInput.addUpdateListener(() -> centralSpanFrequenciesUpdate());

        minFrequencyFieldInput.setField(minFrequencyField);
        maxFrequencyFieldInput.setField(maxFrequencyField);
        centralFrequencyFieldInput.setField(centralFrequencyField);
        spanFrequencyFieldInput.setField(spanFrequencyField);
        pointsFieldInput.setField(pointsField);

        /*
        minFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
        maxFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
        centralFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
        spanFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
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
                (maxFrequencyField.getMagnitude() + minFrequencyField.getMagnitude()) / 2.0f;
        float spanFrequency =
                (maxFrequencyField.getMagnitude() - minFrequencyField.getMagnitude());

        centralFrequencyField.setMagnitude(centralFrequency);
        spanFrequencyField.setMagnitude(spanFrequency);

        centralFrequencyFieldInput.setUpdateEnabled(false);
        spanFrequencyFieldInput.setUpdateEnabled(false);

        centralFrequencyFieldInput.setField(centralFrequencyField);
        spanFrequencyFieldInput.setField(spanFrequencyField);

        centralFrequencyFieldInput.setUpdateEnabled(true);
        spanFrequencyFieldInput.setUpdateEnabled(true);

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
                centralFrequencyField.getMagnitude() + spanFrequencyField.getMagnitude() / 2.0f;
        float minFrequency =
                centralFrequencyField.getMagnitude() - spanFrequencyField.getMagnitude() / 2.0f;

        maxFrequencyField.setMagnitude(maxFrequency);
        minFrequencyField.setMagnitude(minFrequency);

        minFrequencyFieldInput.setUpdateEnabled(false);
        maxFrequencyFieldInput.setUpdateEnabled(false);

        maxFrequencyFieldInput.setField(maxFrequencyField);
        minFrequencyFieldInput.setField(minFrequencyField);

        minFrequencyFieldInput.setUpdateEnabled(true);
        maxFrequencyFieldInput.setUpdateEnabled(true);

        blockUpdate = false;
    }

    public boolean validate()
    {
        return minFrequencyFieldInput.validate(valueValidator) &&
                maxFrequencyFieldInput.validate(valueValidator) &&
                centralFrequencyFieldInput.validate(valueValidator) &&
                spanFrequencyFieldInput.validate(valueValidator);
    }

    public FrequencyField getMinFrequency()
{
    return minFrequencyField;
}

    public FrequencyField getMaxFrequency()
    {
        return maxFrequencyField;
    }

    public FrequencyField getCentralFrequency()
    {
        return centralFrequencyField;
    }

    public FrequencyField getFrequencySpan()
    {
        return spanFrequencyField;
    }

    public Field getPoints()
    {
        return pointsField;
    }

    public void setMinFrequency(FrequencyField value)
    {
        if (value == null) return;

        minFrequencyField = value;

        centralSpanFrequenciesUpdate();
    }

    public void setMaxFrequency(FrequencyField value)
    {
        if (value == null) return;

        maxFrequencyField = value;

        centralSpanFrequenciesUpdate();
    }

    public void setCentralFrequency(FrequencyField value)
    {
        if (value == null) return;

        centralFrequencyField = value;

        centralSpanFrequenciesUpdate();
    }

    public void setSpanFrequency(FrequencyField value)
    {
        if (value == null) return;

        spanFrequencyField = value;

        centralSpanFrequenciesUpdate();
    }

    public void setPoints(Field value)
    {
        if (value == null) return;

        this.pointsField = value;
    }
}
