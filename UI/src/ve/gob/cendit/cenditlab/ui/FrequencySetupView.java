package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FrequencySetupView extends VBox
{
    private static final String FXML_URL = "fxml/frequency-setup-view.fxml";

    @FXML
    private TitledPane bandwidthAveragePane;

    @FXML
    private TitledPane frequencyModePane;

    @FXML
    private ToggleGroup frequencyModeToggleGroup;

    @FXML
    private RadioButton fixedRadioButton;

    @FXML
    private RadioButton listRadioButton;

    @FXML
    private RadioButton sweepRadioButton;

    private FrequencyRangePane frequencyRangePane;

    private FrequencyListPane frequencyListPane;

    private FrequencyFixedPane frequencyFixedPane;


    public FrequencySetupView() throws IOException
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_URL));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        }
        catch (IOException ex)
        {
            throw ex;
        }

        initializePanes();
        initialize();
    }

    private void initialize()
    {
        sweepRadioButton.selectedProperty().addListener(
                (observable, oldValue, newValue) -> setFrequencyPane(newValue, frequencyRangePane));

        listRadioButton.selectedProperty().addListener(
                (observable, oldValue, newValue) -> setFrequencyPane(newValue, frequencyListPane));

        fixedRadioButton.selectedProperty().addListener(
                (observable, oldValue, newValue) -> setFrequencyPane(newValue, frequencyFixedPane));
    }

    private void initializePanes()
    {
        frequencyRangePane = new FrequencyRangePane();

        frequencyFixedPane = new FrequencyFixedPane();

        frequencyListPane = new FrequencyListPane();
    }

    private void setFrequencyPane(boolean enabled, Node pane)
    {
        if (enabled)
        {
            this.getChildren().remove(1);

            this.getChildren().add(1, pane);
        }
    }
}

