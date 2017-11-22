package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.data.FrequencyField;
import ve.gob.cendit.cenditlab.data.FrequencySetup;
import ve.gob.cendit.cenditlab.data.NumericField;

public class FrequencySetupViewProto1 extends GridPane
{
    private static final int SETUP_TO_VIEW = 0;
    private static final int VIEW_TO_SETUP = 1;

    private String FXML_URL = "fxml/frequency-setup-view-proto-1.fxml";

    @FXML
    private ChoiceBox<String> frequencyModeChoiceBox;

    @FXML
    private ChoiceBox<String> bandwidthChoiceBox;

    @FXML
    private FieldInput minFrequencyFieldInput;

    @FXML
    private FieldInput maxFrequencyFieldInput;

    @FXML
    private FieldInput centralFrequencyFieldInput;

    @FXML
    private FieldInput spanFrequencyFieldInput;

    @FXML
    private FieldInput valueFrequencyFieldInput;

    @FXML
    private FieldInput pointsFieldInput;

    private Boolean blockUpdate;

    private FrequencySetup frequencySetup;

    public FrequencySetupViewProto1()
    {
        ViewLoader.load(FXML_URL, this, this);

        initialize();
    }

    private void initialize()
    {
        blockUpdate = true;

        frequencySetup = new FrequencySetup();

        minFrequencyFieldInput.addUpdateListener(() -> minMaxFrequenciesUpdate());
        maxFrequencyFieldInput.addUpdateListener(() -> minMaxFrequenciesUpdate());

        centralFrequencyFieldInput.addUpdateListener(() -> centralSpanFrequenciesUpdate());
        spanFrequencyFieldInput.addUpdateListener(() -> centralSpanFrequenciesUpdate());

        transferSetup(SETUP_TO_VIEW);

        blockUpdate = false;
    }

    public FrequencySetup getFrequencySetup()
    {
        return frequencySetup;
    }

    private void minMaxFrequenciesUpdate()
    {
        if (blockUpdate)
        {
            return;
        }

        blockUpdate = true;

        FrequencyField maxFrequencyField = getFrequencySetup().getMaxFrequencyField();
        FrequencyField minFrequencyField = getFrequencySetup().getMinFrequencyField();
        FrequencyField centralFrequencyField = getFrequencySetup().getCentralFrequencyField();
        FrequencyField spanFrequencyField = getFrequencySetup().getSpanFrequencyField();

        float maxFrequency = maxFrequencyField.getMagnitude();
        float minFrequency = minFrequencyField.getMagnitude();

        float centralFrequency = (maxFrequency + minFrequency) / 2.0f;
        float spanFrequency = (maxFrequency - minFrequency);

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

        FrequencyField maxFrequencyField = getFrequencySetup().getMaxFrequencyField();
        FrequencyField minFrequencyField = getFrequencySetup().getMinFrequencyField();
        FrequencyField centralFrequencyField = getFrequencySetup().getCentralFrequencyField();
        FrequencyField spanFrequencyField = getFrequencySetup().getSpanFrequencyField();

        float centralFrequency = centralFrequencyField.getMagnitude();
        float spanFrequency = spanFrequencyField.getMagnitude();

        float maxFrequency =
                centralFrequency + spanFrequency / 2.0f;
        float minFrequency =
                centralFrequency - spanFrequency / 2.0f;

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

    private void transferSetup(int direction)
    {
        if (direction == SETUP_TO_VIEW)
        {
            minFrequencyFieldInput.setField(frequencySetup.getMinFrequencyField());
            maxFrequencyFieldInput.setField(frequencySetup.getCentralFrequencyField());
            centralFrequencyFieldInput.setField(frequencySetup.getCentralFrequencyField());
            spanFrequencyFieldInput.setField(frequencySetup.getSpanFrequencyField());

            valueFrequencyFieldInput.setField(frequencySetup.getFixedFrequencyField());

            pointsFieldInput.setField(frequencySetup.getAveragePointsNumericField());

            minFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
            maxFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
            centralFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
            spanFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
            valueFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);

            frequencyModeChoiceBox.getItems()
                    .addAll(frequencySetup.getFrequencyModeOptions().getValues());
            frequencyModeChoiceBox.setValue(
                    frequencySetup.getFrequencyModeOptions().getSelected());

            bandwidthChoiceBox.getItems()
                    .addAll(frequencySetup.getBandwidthOptions().getValues());
            bandwidthChoiceBox.setValue(
                    frequencySetup.getBandwidthOptions().getSelected());
        }
        else if (direction == VIEW_TO_SETUP)
        {
            FrequencyField frequencyField;

            frequencyField = (FrequencyField)minFrequencyFieldInput.getField();
            frequencySetup.getMinFrequencyField().setValue(frequencyField.getValue());
            frequencySetup.getMinFrequencyField().setUnit(frequencyField.getUnit());

            frequencyField = (FrequencyField)maxFrequencyFieldInput.getField();
            frequencySetup.getMaxFrequencyField().setValue(frequencyField.getValue());
            frequencySetup.getMaxFrequencyField().setUnit(frequencyField.getUnit());

            frequencyField = (FrequencyField)centralFrequencyFieldInput.getField();
            frequencySetup.getCentralFrequencyField().setValue(frequencyField.getValue());
            frequencySetup.getCentralFrequencyField().setUnit(frequencyField.getUnit());

            frequencyField = (FrequencyField)spanFrequencyFieldInput.getField();
            frequencySetup.getSpanFrequencyField().setValue(frequencyField.getValue());
            frequencySetup.getSpanFrequencyField().setUnit(frequencyField.getUnit());

            frequencyField = (FrequencyField) valueFrequencyFieldInput.getField();
            frequencySetup.getFixedFrequencyField().setValue(frequencyField.getValue());
            frequencySetup.getFixedFrequencyField().setUnit(frequencyField.getUnit());

            NumericField averagePointsField = (NumericField) pointsFieldInput.getField();
            frequencySetup.getAveragePointsNumericField().setValue(averagePointsField.getValue());
            frequencySetup.getAveragePointsNumericField().setUnit(averagePointsField.getUnit());

            frequencySetup.getBandwidthOptions().setSelected(
                    bandwidthChoiceBox.getSelectionModel().getSelectedItem());

            frequencySetup.getBandwidthOptions().setSelected(
                    bandwidthChoiceBox.getSelectionModel().getSelectedItem());
        }
    }
}
