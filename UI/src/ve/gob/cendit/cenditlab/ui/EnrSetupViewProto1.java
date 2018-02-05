package ve.gob.cendit.cenditlab.ui;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.data.EnrSetup;
import ve.gob.cendit.cenditlab.data.TemperatureData;

public class EnrSetupViewProto1 extends HBox
{
    private static final String FXML_URL = "fxml/enr-setup-view-proto-1.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    OptionsView enrModeOptionsView;

    @FXML
    OptionsView noiseSourcePreferenceOptionsView;

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

    @FXML
    private VBox enrDataVBox;

    @FXML
    private EnrSpotSetupView enrSpotSetupView;

    private EnrTableSetupView enrCommonTableSetupView;

    private EnrTableSetupView enrMeasurementTableSetupView;

    private EnrSetup enrSetup;

    public EnrSetupViewProto1()
    {
        this(new EnrSetup());
    }

    public EnrSetupViewProto1(EnrSetup setup)
    {
        viewLoader.load(this, this);

        initialize();
        makeBindings();

        setSetup(setup);
    }

    private void initialize()
    {
        enrCommonTableSetupView = new EnrTableSetupView();
        enrMeasurementTableSetupView = new EnrTableSetupView();
    }

    private void makeBindings()
    {
        userTcoldValueView.disableProperty().bind(userTcoldToggleView.selectedProperty());

        enrModeOptionsView.valueProperty()
            .addListener((observable, oldValue, newValue) -> changeSetupView(newValue));
    }

    public EnrSetup getSetup()
    {
        return enrSetup;
    }

    public void setSetup(EnrSetup setup)
    {
        enrSetup = setup;

        autoLoadEnrToggleView.setOptions(enrSetup.getAutoLoadEnrOptions());

        commonEnrTableToggleView.setOptions(enrSetup.getCommonEnrTableOptions());

        snsTcoldToggleView.setOptions(enrSetup.getSnsTcoldOptions());

        userTcoldToggleView.setOptions(enrSetup.getUserTcoldOptions());

        userTcoldValueView.setData(enrSetup.getUserTcoldData());

        enrModeOptionsView.setOptions(enrSetup.getEnrModeOptions());

        enrSpotSetupView.setSpotModeOptions(enrSetup.getSpotModeOptions());

        userTcoldValueView.setData(enrSetup.getUserTcoldData());

        noiseSourcePreferenceOptionsView.setOptions(enrSetup.getNoiseSourcePreferenceOptions());
    }

    private void changeSetupView(String setupView)
    {
        if (setupView == null)
            return;

        switch (setupView)
        {
            case "Tabla":

                enrDataVBox.getChildren().clear();

                enrDataVBox.getChildren().add(enrCommonTableSetupView);

                if (commonEnrTableToggleView.isSelected())
                {
                    enrCommonTableSetupView.setText("Enr common table");
                }
                else
                {
                    enrMeasurementTableSetupView.setText("Enr measurement table");
                    enrDataVBox.getChildren().add(enrMeasurementTableSetupView);
                }

                break;

            case "Spot":

                enrDataVBox.getChildren().clear();
                enrDataVBox.getChildren().remove(enrMeasurementTableSetupView);

                enrDataVBox.getChildren().add(enrSpotSetupView);

                break;
        }
    }
}
