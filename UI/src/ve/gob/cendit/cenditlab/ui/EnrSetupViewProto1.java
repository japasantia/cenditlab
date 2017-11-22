package ve.gob.cendit.cenditlab.ui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.data.EnrField;
import ve.gob.cendit.cenditlab.data.EnrSetup;
import ve.gob.cendit.cenditlab.data.TemperatureField;

public class EnrSetupViewProto1 extends GridPane
{
    private static final String FXML_URL = "fxml/enr-setup-view-proto-1.fxml";

    private static final int SETUP_TO_VIEW = 0;
    private static final int VIEW_TO_SETUP = 1;

    @FXML
    ChoiceBox<String> enrModeChoiceBox;

    @FXML
    ChoiceBox<String> spotModeChoiceBox;

    @FXML
    ChoiceBox<String> noiseSourcePreferenceChoiceBox;

    @FXML
    FieldInput spotEnrFieldInput;

    @FXML
    FieldInput userTcoldFieldInput;

    @FXML
    ToggleView autoLoadEnrToggleView;

    @FXML
    ToggleView commonEnrTableToggleView;

    @FXML
    ToggleView snsTcoldToggleView;

    @FXML
    ToggleView userTcoldToggleView;

    private BooleanProperty enableEnrTableModeProperty;
    private BooleanProperty enableCommonEnrTableProperty;

    private EnrSetup enrSetup;

    public EnrSetupViewProto1()
    {
        ViewLoader.load(FXML_URL, this, this);

        initialize();
        attachListeners();
        makeBindings();
    }

    private void initialize()
    {
        enrSetup = new EnrSetup();

        enableEnrTableModeProperty = new SimpleBooleanProperty();
        enableCommonEnrTableProperty = new SimpleBooleanProperty();

        spotEnrFieldInput.setChoiceUnits(EnrField.FIELD_UNITS);

        userTcoldFieldInput.setChoiceUnits(TemperatureField.FIELD_UNITS);

        transferSetup(SETUP_TO_VIEW);
    }

    private void attachListeners()
    {}

    private void makeBindings()
    {
        BooleanBinding binding;

        binding = enrModeChoiceBox.getSelectionModel()
                .selectedIndexProperty().isEqualTo(0);
        spotEnrFieldInput.disableProperty().bind(binding);
        enableEnrTableModeProperty.bind(binding);

        binding = commonEnrTableToggleView.textProperty()
                .isEqualTo("ON");
        enableCommonEnrTableProperty.bind(binding);

        binding = userTcoldToggleView.textProperty()
                .isEqualTo("OFF");

        userTcoldFieldInput.disableProperty().bind(binding);
    }

    private void transferSetup(int direction)
    {
        if (direction == VIEW_TO_SETUP)
        {
            // Cargar datos de la vista en setup
            enrSetup.getEnrModeOptions()
                    .setSelected(enrModeChoiceBox.getValue());
            enrSetup.getSpotModeOptions()
                    .setSelected(spotModeChoiceBox.getValue());

            /*
            NumericField field =
                    (NumericField) spotEnrFieldInput.getField();
            enrSetup.getEnrField().setMagnitude(field.getMagnitude());
            */

            EnrField frequencyField =
                    (EnrField) spotEnrFieldInput.getField();
            enrSetup.getEnrField().setValue(frequencyField.getValue());
            enrSetup.getEnrField().setUnit(frequencyField.getUnit());

            enrSetup.getAutoLoadEnrOptions()
                    .setSelected(autoLoadEnrToggleView.getText());
            enrSetup.getCommonEnrTableOptions()
                    .setSelected(commonEnrTableToggleView.getText());

            enrSetup.getUserTcoldOptions()
                    .setSelected(userTcoldToggleView.getText());

            TemperatureField temperatureField = enrSetup.getUserTcoldField();
            enrSetup.getUserTcoldField().setValue(temperatureField.getValue());
            enrSetup.getUserTcoldField().setUnit(temperatureField.getUnit());

            enrSetup.getSnsTcoldOptions()
                    .setSelected(snsTcoldToggleView.getText());
            enrSetup.getNoiseSourcePreferenceOptions()
                    .setSelected(noiseSourcePreferenceChoiceBox.getValue());
        }
        else if (direction == SETUP_TO_VIEW)
        {
            // Presentar datos en vista

            enrModeChoiceBox.getItems()
                    .addAll(enrSetup.getEnrModeOptions().getValues());
            spotModeChoiceBox.getItems()
                    .addAll(enrSetup.getSpotModeOptions().getValues());

            spotEnrFieldInput.setField(enrSetup.getEnrField());

            autoLoadEnrToggleView
                    .setTextOn(enrSetup.getAutoLoadEnrOptions().getDefault());
            commonEnrTableToggleView
                    .setTextOn(enrSetup.getCommonEnrTableOptions().getDefault());
            userTcoldToggleView
                    .setTextOn(enrSetup.getUserTcoldOptions().getDefault());
            snsTcoldToggleView
                    .setTextOn(enrSetup.getSnsTcoldOptions().getDefault());

            userTcoldFieldInput.setField(enrSetup.getUserTcoldField());

            noiseSourcePreferenceChoiceBox.getItems()
                    .addAll(enrSetup.getNoiseSourcePreferenceOptions().getValues());
            noiseSourcePreferenceChoiceBox
                    .setValue(enrSetup.getNoiseSourcePreferenceOptions().getDefault());
        }
    }

    public EnrSetup getSetup()
    {
        transferSetup(VIEW_TO_SETUP);

        return enrSetup;
    }

    public void setSetup(EnrSetup setup)
    {
        enrSetup = setup;

        transferSetup(SETUP_TO_VIEW);
    }

    public BooleanProperty enableEnrTableModeProperty()
    {
        return enableEnrTableModeProperty;
    }

    public BooleanProperty enableCommonEnrTableProperty()
    {
        return enableCommonEnrTableProperty;
    }
}
