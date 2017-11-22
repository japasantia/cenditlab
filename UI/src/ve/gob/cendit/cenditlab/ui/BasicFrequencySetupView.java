package ve.gob.cendit.cenditlab.ui;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.data.FrequencySetup;

public class BasicFrequencySetupView extends GridPane
{
    private static final String FXML_URL = "fxml/basic-frequency-setup-view.fxml";

    @FXML
    private ChoiceBox<String> frequencyModeChoiceBox;

    @FXML
    private FrequencyRangePane frequencyRangePane;

    private FieldInput fixedFrequencyFieldInput;

    private FrequencyListPane frequencyListPane;

    private FrequencySetup frequencySetup;

    private Node currentFrequencySetupParent;

    public BasicFrequencySetupView()
    {
        ViewLoader.load(FXML_URL, this, this);

        initialize();
    }

    private void initialize()
    {
        frequencySetup = new FrequencySetup();

        currentFrequencySetupParent = frequencyRangePane;

        frequencyModeChoiceBox.getItems()
                .addAll(frequencySetup.getFrequencyModeOptions().getValues());
        frequencyModeChoiceBox
                .setValue(frequencySetup.getFrequencyModeOptions().getDefault());

        frequencyModeChoiceBox.valueProperty()
                .addListener((observable, oldMode, newMode) ->
                {
                    updateFrequencyMode(newMode);
                });
    }

    private void updateFrequencyMode(String frequencyMode)
    {
        switch (frequencyMode)
        {
            case "Sweep":
                loadFrequencyRangePane();
                break;

            case "List":
                loadFrequencyListPane();
                break;

            case "Fixed":
                loadFixedFrequencyFieldInput();
                break;
        }
    }

    private void removeCurrentSetupPane()
    {
        this.getChildren().remove(currentFrequencySetupParent);
    }

    private void loadFixedFrequencyFieldInput()
    {
        if (fixedFrequencyFieldInput == null)
        {
            fixedFrequencyFieldInput = new FieldInput();
            fixedFrequencyFieldInput.setField(frequencySetup.getFixedFrequencyField());
        }

        removeCurrentSetupPane();
        setCurrentFrequencySetupPane(fixedFrequencyFieldInput);

        this.add(fixedFrequencyFieldInput,
                0, 1, 2, 1);

    }

    private void loadFrequencyListPane()
    {
        if (frequencyListPane == null)
        {
            frequencyListPane = new FrequencyListPane();
        }

        removeCurrentSetupPane();
        setCurrentFrequencySetupPane(frequencyListPane);

        this.add(frequencyListPane,
                0, 1, 2, 1);
    }

    private void loadFrequencyRangePane()
    {
        removeCurrentSetupPane();
        setCurrentFrequencySetupPane(frequencyRangePane);

        this.add(frequencyRangePane,
                0, 1, 2, 1);
    }

    private void setCurrentFrequencySetupPane(Node node)
    {
        currentFrequencySetupParent = node;
    }
}
