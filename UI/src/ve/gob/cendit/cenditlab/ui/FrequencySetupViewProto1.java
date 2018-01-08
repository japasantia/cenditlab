package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.data.FrequencySetup;

import java.util.Objects;


public class FrequencySetupViewProto1 extends HBox
{
    private static final int SETUP_TO_VIEW = 0;
    private static final int VIEW_TO_SETUP = 1;

    private String FXML_URL = "fxml/frequency-setup-view-proto-1.fxml";
    
    private ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private OptionsView frequencyModeOptionsView;

    @FXML
    private OptionsView bandwidthOptionsView;

    @FXML
    private ValueView averagePointsValueView;

    @FXML
    private VBox frequencyDataVBox;

    @FXML
    private FrequencyRangeSetupView frequencyRangeSetupView;

    private FrequencyFixedSetupView frequencyFixedSetupView;

    private FrequencyListSetupView frequencyListSetupView;

    private Node currentFrequencySetupNode;

    private boolean blockUpdate;

    private FrequencySetup frequencySetup;

    public FrequencySetupViewProto1()
    {
        ViewLoader.load(FXML_URL, this, this);

        initialize();
    }

    private void initialize()
    {
        setFrequencySetup(new FrequencySetup());

        currentFrequencySetupNode = frequencyRangeSetupView;

        frequencyModeOptionsView.valueProperty()
            .addListener((observable, oldValue, newValue) -> changeSetupView(newValue));
    }

    public void setFrequencySetup(FrequencySetup frequencySetup)
    {
        this.frequencySetup = frequencySetup;

        loadSetup();
    }

    public FrequencySetup getFrequencySetup()
    {
        return frequencySetup;
    }

    public void setUpdateBlocked(boolean value)
    {
        blockUpdate = true;
    }

    public boolean isUpdateBlocked()
    {
        return blockUpdate;
    }

    private void loadSetup()
    {
        setUpdateBlocked(true);

        frequencyModeOptionsView.setOptions(frequencySetup.getFrequencyModeOptions());

        bandwidthOptionsView.setOptions(frequencySetup.getBandwidthOptions());

        averagePointsValueView.setData(frequencySetup.getAveragePointsNumericData());

        setUpdateBlocked(false);
    }

    private void changeSetupView(String setupView)
    {
        if (Objects.isNull(setupView))
            return;

        switch (setupView)
        {
            case "Sweep":
                setCurrentFrequencySetupNode(frequencyRangeSetupView);
                break;

            case "List":

                if (frequencyListSetupView == null)
                {
                    frequencyListSetupView = new FrequencyListSetupView();
                }
                setCurrentFrequencySetupNode(frequencyListSetupView);
                break;

            case "Fixed":

                if (frequencyFixedSetupView == null)
                {
                    frequencyFixedSetupView = new FrequencyFixedSetupView();
                    frequencyFixedSetupView.setFixedFrequency(frequencySetup.getFixedFrequencyData());
                }
                setCurrentFrequencySetupNode(frequencyFixedSetupView);
                break;
        }
    }

    private void setCurrentFrequencySetupNode(Node node)
    {
        frequencyDataVBox.getChildren().remove(currentFrequencySetupNode);
        currentFrequencySetupNode = node;
        frequencyDataVBox.getChildren().add(currentFrequencySetupNode);
    }
}
