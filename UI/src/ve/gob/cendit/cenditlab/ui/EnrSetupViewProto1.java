package ve.gob.cendit.cenditlab.ui;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.data.EnrData;
import ve.gob.cendit.cenditlab.data.EnrData;
import ve.gob.cendit.cenditlab.data.EnrSetup;
import ve.gob.cendit.cenditlab.data.TemperatureData;

public class EnrSetupViewProto1 extends GridPane
{
    private static final String FXML_URL = "fxml/enr-setup-view-proto-1.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    private static final int SETUP_TO_VIEW = 0;
    private static final int VIEW_TO_SETUP = 1;

    @FXML
    ChoiceBox<String> enrModeChoiceBox;

    @FXML
    ChoiceBox<String> spotModeChoiceBox;

    @FXML
    ChoiceBox<String> noiseSourcePreferenceChoiceBox;

    @FXML
    ValueView spotEnrValueView;

    @FXML
    ValueView userTcoldValueView;

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
        viewLoader.load(this, this);

        initialize();
        attachListeners();
        makeBindings();
    }

    private void initialize()
    {
        enrSetup = new EnrSetup();

        enableEnrTableModeProperty = new SimpleBooleanProperty();
        enableCommonEnrTableProperty = new SimpleBooleanProperty();

        spotEnrValueView.setChoiceUnits(EnrData.FIELD_UNITS);

        userTcoldValueView.setChoiceUnits(TemperatureData.FIELD_UNITS);

        transferSetup(SETUP_TO_VIEW);
    }

    private void attachListeners()
    {}

    private void makeBindings()
    {
        BooleanBinding binding;

        binding = enrModeChoiceBox.getSelectionModel()
                .selectedIndexProperty().isEqualTo(0);
        spotEnrValueView.disableProperty().bind(binding);
        enableEnrTableModeProperty.bind(binding);

        binding = commonEnrTableToggleView.textProperty()
                .isEqualTo("ON");
        enableCommonEnrTableProperty.bind(binding);

        binding = userTcoldToggleView.textProperty()
                .isEqualTo("OFF");

        userTcoldValueView.disableProperty().bind(binding);
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

            EnrData spotEnrData =
                    (EnrData) spotEnrValueView.getData();
            enrSetup.getEnrData().setValue(spotEnrData.getValue());
            enrSetup.getEnrData().setUnit(spotEnrData.getUnit());

            enrSetup.getAutoLoadEnrOptions()
                    .setSelected(autoLoadEnrToggleView.getText());
            enrSetup.getCommonEnrTableOptions()
                    .setSelected(commonEnrTableToggleView.getText());

            enrSetup.getUserTcoldOptions()
                    .setSelected(userTcoldToggleView.getText());

            TemperatureData temperatureData = enrSetup.getUserTcoldField();
            enrSetup.getUserTcoldField().setValue(temperatureData.getValue());
            enrSetup.getUserTcoldField().setUnit(temperatureData.getUnit());

            enrSetup.getSnsTcoldOptions()
                    .setSelected(snsTcoldToggleView.getText());
            enrSetup.getNoiseSourcePreferenceOptions()
                    .setSelected(noiseSourcePreferenceChoiceBox.getValue());
        }
        else if (direction == SETUP_TO_VIEW)
        {
            // Presentar datos en vista

            enrModeChoiceBox.getItems()
                    .setAll(enrSetup.getEnrModeOptions().getValues());
            spotModeChoiceBox.getItems()
                    .setAll(enrSetup.getSpotModeOptions().getValues());

            spotEnrValueView.setData(enrSetup.getEnrData());

            autoLoadEnrToggleView
                    .setTextOn(enrSetup.getAutoLoadEnrOptions().getSelected());
            commonEnrTableToggleView
                    .setTextOn(enrSetup.getCommonEnrTableOptions().getSelected());
            userTcoldToggleView
                    .setTextOn(enrSetup.getUserTcoldOptions().getSelected());
            snsTcoldToggleView
                    .setTextOn(enrSetup.getSnsTcoldOptions().getSelected());

            userTcoldValueView.setData(enrSetup.getUserTcoldField());

            noiseSourcePreferenceChoiceBox.getItems()
                    .setAll(enrSetup.getNoiseSourcePreferenceOptions().getValues());
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
