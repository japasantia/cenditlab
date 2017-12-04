package ve.gob.cendit.cenditlab.ui;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ve.gob.cendit.cenditlab.data.EnrSetup;


public class SetupView extends TabPane
{
    private static final String FXML_URL = "fxml/setup-view-proto-1.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    private static final int SETUP_TO_VIEW = 0;
    private static final int VIEW_TO_SETUP = 1;

    @FXML
    Tab commonEnrTab;

    @FXML
    Tab measurementEnrTab;

    @FXML
    FrequencySetupViewProto1 frequencySetupView;

    @FXML
    EnrSetupViewProto1 enrSetupView;

    public SetupView()
    {
        viewLoader.load(this, this);

        initialize();
        attachListeners();
        makeBindings();
    }

    private void initialize()
    {

    }

    private void attachListeners()
    {

    }

    private void makeBindings()
    {
        BooleanProperty enableTableMode =
                enrSetupView.enableEnrTableModeProperty();
        BooleanProperty enableCommonTable =
                enrSetupView.enableCommonEnrTableProperty();

        commonEnrTab.disableProperty()
                .bind(enableTableMode.not());
        measurementEnrTab.disableProperty()
                .bind(enableTableMode.not().or(enableCommonTable));
    }

    private void updateCommonTable(boolean commonTable)
    {
        commonEnrTab.setText(commonTable ? "ENR Comun" : "ENR Calibracion");
    }

    private void transferSetup(int direction)
    {
        if (direction == VIEW_TO_SETUP)
        {

        }
        else if (direction == SETUP_TO_VIEW)
        {
            // TODO: presentar datos en vista
        }
    }

    public EnrSetup getSetup()
    {
        transferSetup(VIEW_TO_SETUP);

        return null;
    }

    public void setSetup(EnrSetup setup)
    {


        transferSetup(SETUP_TO_VIEW);
    }
}
