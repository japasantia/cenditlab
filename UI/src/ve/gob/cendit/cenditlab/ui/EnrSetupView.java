package ve.gob.cendit.cenditlab.ui;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;

public class EnrSetupView extends TitledPane
{
    private static final String FXML_URL = "fxml/enr-setup-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private RadioButton enrTableModeRadioButton;

    @FXML
    private CheckBox enrCommonTableCheckBox;

    @FXML
    private ValueView enrSpotValueView;

    @FXML
    private Accordion enrTablesAccordion;

    @FXML
    private EnrTableSetupView enrCommonTablePane;

    @FXML
    private EnrTableSetupView enrExtraTablePane;


    public EnrSetupView()
    {
        viewLoader.load(this, this);

        initialize();
    }

    private void initialize()
    {
        BooleanProperty enrTableModeProperty = enrTableModeRadioButton.selectedProperty();

        enrTablesAccordion.disableProperty()
                .bind(enrTableModeProperty.not());
        enrTablesAccordion.visibleProperty()
                .bind(enrTableModeProperty);

        enrSpotValueView.disableProperty()
                .bind(enrTableModeProperty);
        enrSpotValueView.visibleProperty()
                .bind(enrTableModeProperty.not());

        enrCommonTableCheckBox.disableProperty()
                .bind(enrTableModeProperty.not());

        /*
        enrTableModeRadioButton.selectedProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        enrTablesAccordion.setDisable(!newValue);
                        enrTablesAccordion.setVisible(newValue);

                        enrSpotValueView.setDisable(newValue);
                        enrSpotValueView.setVisible(!newValue);

                        enrCommonTableCheckBox.setDisable(!newValue);
                    });
        */
        enrCommonTableCheckBox.selectedProperty()
                .addListener((observable, oldValue, newValue) ->
                    {
                        if (newValue)
                            configureCommonEnrTable();
                        else
                            configureCalibrationAndMeasurementTable();
                    });

        /*
        enrCommonTablePane = new EnrTableSetupView();
        enrTablesAccordion.getPanes().add(enrCommonTablePane);

        configureCommonEnrTable();
        */
    }

    /*
    private void configureCommonEnrTable()
    {
        enrCommonTablePane.setText("Tabla ENR comun");
        enrTablesAccordion.getPanes().remove(enrExtraTablePane);
    }

    private void configureCalibrationAndMeasurementTable()
    {
        if (enrExtraTablePane == null)
        {
            enrExtraTablePane = new EnrTableSetupView();
        }

        enrCommonTablePane.setText("Tabla ENR calibracion");
        enrExtraTablePane.setText("Tabla ENR medicion");

        enrTablesAccordion.getPanes().add(enrExtraTablePane);
    }
    */

    private void configureCommonEnrTable()
    {
        enrCommonTablePane.setText("Tabla ENR");
        enableCommonEnrTable(true);
        enableExtraEnrTable(false);
    }

    private void configureCalibrationAndMeasurementTable()
    {
        enrCommonTablePane.setText("Tabla ENR calibracion");
        enableCommonEnrTable(true);
        enableExtraEnrTable(true);
    }

    private void enableCommonEnrTable(boolean enable)
    {
        enrCommonTablePane.setDisable(!enable);
        enrCommonTablePane.setVisible(enable);
    }

    private void enableExtraEnrTable(boolean enable)
    {
        enrExtraTablePane.setDisable(!enable);
        enrExtraTablePane.setVisible(enable);
    }
}
