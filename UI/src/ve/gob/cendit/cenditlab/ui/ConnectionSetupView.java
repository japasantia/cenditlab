package ve.gob.cendit.cenditlab.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;

public class ConnectionSetupView extends GridPane
{
    private static final String FXML_URL = "fxml/connection-setup-view.fxml";

    @FXML
    ComboBox addressComboBox;

    @FXML
    Button connectButton;

    @FXML
    ProgressIndicator progressIndicator;

    public ConnectionSetupView()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    @FXML
    private void connectButtonClicked(ActionEvent event)
    {
        progressIndicator.setProgress(-1.0);
        progressIndicator.setVisible(true);

        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex)
        { }

        progressIndicator.setProgress(1.0);
    }
}
