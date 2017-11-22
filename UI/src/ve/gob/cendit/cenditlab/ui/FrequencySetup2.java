package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FrequencySetup2 extends VBox
{
    private static final String FXML_URL = "fxml/frequency-setup.fxml";

    @FXML
    private TitledPane bandwidthAveragePane;

    @FXML
    private TitledPane frequencyModePane;

    @FXML
    private TitledPane frequencyRangePane;

    @FXML
    private TitledPane frequencyFixedPane;

    @FXML
    private TitledPane frequencyListPane;

    @FXML
    private ToggleGroup frequencyModeToggleGroup;

    @FXML
    private RadioButton fixedFrequencyRadioButton;

    @FXML
    private RadioButton listFrequencyRadioButton;

    @FXML
    private RadioButton sweepFrequencyRadioButton;


    public FrequencySetup2() throws IOException
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

        initialize();
    }

    private void initialize()
    {
        /*
        frequencyRangePane.visibleProperty().bind(sweepFrequencyRadioButton.selectedProperty());
        frequencyListPane.visibleProperty().bind(listFrequencyRadioButton.selectedProperty());
        fixedFrequencyPane.visibleProperty().bind(fixedFrequencyRadioButton.selectedProperty());
        */

        this.getChildren().remove(frequencyRangePane);
        this.getChildren().remove(frequencyListPane);
        this.getChildren().remove(frequencyFixedPane);

        sweepFrequencyRadioButton.selectedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue)
                    {
                        addFrequencyPane(frequencyRangePane);
                    }
                });

        listFrequencyRadioButton.selectedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue)
                    {
                        addFrequencyPane(frequencyListPane);
                    }
                }
        );

        fixedFrequencyRadioButton.selectedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue)
                    {
                        addFrequencyPane(frequencyFixedPane);
                    }
                }
        );
    }

    private void addFrequencyPane(Node frequencyPane)
    {
        this.getChildren().remove(frequencyRangePane);
        this.getChildren().remove(frequencyListPane);
        this.getChildren().remove(frequencyFixedPane);

        this.getChildren().add(1, frequencyPane);
    }
}

