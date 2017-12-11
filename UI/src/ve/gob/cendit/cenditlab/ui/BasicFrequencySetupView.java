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

    private ValueView fixedFrequencyValueView;

    private FrequencyListPane frequencyListPane;

    private Node currentFrequencySetupParent;

    private FrequencySetup frequencySetup;

    public BasicFrequencySetupView()
    {
        this(new FrequencySetup());
    }

    public BasicFrequencySetupView(FrequencySetup setup)
    {
        ViewLoader.load(FXML_URL, this, this);

        setFrequencySetup(setup);

        initialize();
    }

    private void initialize()
    {
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

    public void setFrequencySetup(FrequencySetup setup)
    {
        if (setup != null)
        {
            frequencySetup = setup;
        }
    }

    public FrequencySetup getFrequencySetup()
    {
        return frequencySetup;
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

    private void changeFrequencySetupPane(Node newPane)
    {
        this.getChildren().remove(currentFrequencySetupParent);

        currentFrequencySetupParent = newPane;

        this.add(newPane,0, 1, 2, 1);
    }

    private void loadFixedFrequencyFieldInput()
    {
        if (fixedFrequencyValueView == null)
        {
            fixedFrequencyValueView = new ValueView();
        }

        fixedFrequencyValueView.setData(frequencySetup.getFixedFrequencyData());
        changeFrequencySetupPane(fixedFrequencyValueView);
    }

    private void loadFrequencyListPane()
    {
        if (frequencyListPane == null)
        {
            frequencyListPane = new FrequencyListPane();
        }


        changeFrequencySetupPane(frequencyListPane);
    }

    private void loadFrequencyRangePane()
    {
        frequencyRangePane.setFrequencySetup(frequencySetup);
        changeFrequencySetupPane(frequencyRangePane);
    }
}
