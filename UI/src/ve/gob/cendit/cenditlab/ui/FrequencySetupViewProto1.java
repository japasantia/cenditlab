package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.data.FrequencyData;
import ve.gob.cendit.cenditlab.data.FrequencySetup;
import ve.gob.cendit.cenditlab.data.NumericData;


public class FrequencySetupViewProto1 extends GridPane
{
    private static final int SETUP_TO_VIEW = 0;
    private static final int VIEW_TO_SETUP = 1;

    private String FXML_URL = "fxml/frequency-setup-view-proto-1.fxml";
    
    private ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private ChoiceBox<String> frequencyModeChoiceBox;

    @FXML
    private ChoiceBox<String> bandwidthChoiceBox;

    @FXML
    private ValueView minFrequencyValueView;

    @FXML
    private ValueView maxFrequencyValueView;

    @FXML
    private ValueView centralFrequencyValueView;

    @FXML
    private ValueView spanFrequencyValueView;

    @FXML
    private ValueView valueFrequencyValueView;

    @FXML
    private ValueView pointsValueView;

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

        minFrequencyValueView.addUpdateListener(source -> minMaxFrequenciesUpdate());
        maxFrequencyValueView.addUpdateListener(source -> minMaxFrequenciesUpdate());

        centralFrequencyValueView.addUpdateListener(source -> centralSpanFrequenciesUpdate());
        spanFrequencyValueView.addUpdateListener(source -> centralSpanFrequenciesUpdate());

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

        FrequencyData maxFrequencyData = getFrequencySetup().getMaxFrequencyData();
        FrequencyData minFrequencyData = getFrequencySetup().getMinFrequencyData();
        FrequencyData centralFrequencyData = getFrequencySetup().getCentralFrequencyData();
        FrequencyData spanFrequencyData = getFrequencySetup().getSpanFrequencyData();

        float maxFrequency = maxFrequencyData.getMagnitude();
        float minFrequency = minFrequencyData.getMagnitude();

        float centralFrequency = (maxFrequency + minFrequency) / 2.0f;
        float spanFrequency = (maxFrequency - minFrequency);

        centralFrequencyData.setMagnitude(centralFrequency);
        spanFrequencyData.setMagnitude(spanFrequency);

        centralFrequencyValueView.setUpdateEnabled(false);
        spanFrequencyValueView.setUpdateEnabled(false);

        centralFrequencyValueView.setData(centralFrequencyData);
        spanFrequencyValueView.setData(spanFrequencyData);

        centralFrequencyValueView.setUpdateEnabled(true);
        spanFrequencyValueView.setUpdateEnabled(true);

        blockUpdate = false;
    }

    private void centralSpanFrequenciesUpdate()
    {
        if (blockUpdate)
        {
            return;
        }

        blockUpdate = true;

        FrequencyData maxFrequencyData = getFrequencySetup().getMaxFrequencyData();
        FrequencyData minFrequencyData = getFrequencySetup().getMinFrequencyData();
        FrequencyData centralFrequencyData = getFrequencySetup().getCentralFrequencyData();
        FrequencyData spanFrequencyData = getFrequencySetup().getSpanFrequencyData();

        float centralFrequency = centralFrequencyData.getMagnitude();
        float spanFrequency = spanFrequencyData.getMagnitude();

        float maxFrequency =
                centralFrequency + spanFrequency / 2.0f;
        float minFrequency =
                centralFrequency - spanFrequency / 2.0f;

        maxFrequencyData.setMagnitude(maxFrequency);
        minFrequencyData.setMagnitude(minFrequency);

        minFrequencyValueView.setUpdateEnabled(false);
        maxFrequencyValueView.setUpdateEnabled(false);

        maxFrequencyValueView.setData(maxFrequencyData);
        minFrequencyValueView.setData(minFrequencyData);

        minFrequencyValueView.setUpdateEnabled(true);
        maxFrequencyValueView.setUpdateEnabled(true);

        blockUpdate = false;
    }

    private void transferSetup(int direction)
    {
        if (direction == SETUP_TO_VIEW)
        {
            minFrequencyValueView.setData(frequencySetup.getMinFrequencyData());
            maxFrequencyValueView.setData(frequencySetup.getCentralFrequencyData());
            centralFrequencyValueView.setData(frequencySetup.getCentralFrequencyData());
            spanFrequencyValueView.setData(frequencySetup.getSpanFrequencyData());

            valueFrequencyValueView.setData(frequencySetup.getFixedFrequencyData());

            pointsValueView.setData(frequencySetup.getAveragePointsNumericData());

            minFrequencyValueView.setChoiceUnits(FrequencyData.FIELD_UNITS);
            maxFrequencyValueView.setChoiceUnits(FrequencyData.FIELD_UNITS);
            centralFrequencyValueView.setChoiceUnits(FrequencyData.FIELD_UNITS);
            spanFrequencyValueView.setChoiceUnits(FrequencyData.FIELD_UNITS);
            valueFrequencyValueView.setChoiceUnits(FrequencyData.FIELD_UNITS);

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
            FrequencyData frequencyData;

            frequencyData = (FrequencyData) minFrequencyValueView.getData();
            frequencySetup.getMinFrequencyData().setValue(frequencyData.getValue());
            frequencySetup.getMinFrequencyData().setUnit(frequencyData.getUnit());

            frequencyData = (FrequencyData) maxFrequencyValueView.getData();
            frequencySetup.getMaxFrequencyData().setValue(frequencyData.getValue());
            frequencySetup.getMaxFrequencyData().setUnit(frequencyData.getUnit());

            frequencyData = (FrequencyData) centralFrequencyValueView.getData();
            frequencySetup.getCentralFrequencyData().setValue(frequencyData.getValue());
            frequencySetup.getCentralFrequencyData().setUnit(frequencyData.getUnit());

            frequencyData = (FrequencyData) spanFrequencyValueView.getData();
            frequencySetup.getSpanFrequencyData().setValue(frequencyData.getValue());
            frequencySetup.getSpanFrequencyData().setUnit(frequencyData.getUnit());

            frequencyData = (FrequencyData) valueFrequencyValueView.getData();
            frequencySetup.getFixedFrequencyData().setValue(frequencyData.getValue());
            frequencySetup.getFixedFrequencyData().setUnit(frequencyData.getUnit());

            NumericData averagePointsField = (NumericData) pointsValueView.getData();
            frequencySetup.getAveragePointsNumericData().setValue(averagePointsField.getValue());
            frequencySetup.getAveragePointsNumericData().setUnit(averagePointsField.getUnit());

            frequencySetup.getBandwidthOptions().setSelected(
                    bandwidthChoiceBox.getSelectionModel().getSelectedItem());

            frequencySetup.getBandwidthOptions().setSelected(
                    bandwidthChoiceBox.getSelectionModel().getSelectedItem());
        }
    }
}
