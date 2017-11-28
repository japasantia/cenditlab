package ve.gob.cendit.cenditlab.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;


public class ExecutionToolbar extends GridPane
{
    private static final String FXML_URL = "fxml/execution-toolbar.fxml";

    @FXML
    Button startButton;

    @FXML
    Button stopButton;

    @FXML
    ProgressIndicator progressIndicator;

    public ExecutionToolbar()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    public void setOnStart(EventHandler<ActionEvent> eventHandler)
    {
        startButton.setOnAction(eventHandler);
    }

    public void setOnStop(EventHandler<ActionEvent> eventHandler)
    {
        stopButton.setOnAction(eventHandler);
    }

    public void setEnableStart(boolean value)
    {
        startButton.setDisable(! value);
    }

    public void setEnableStop(boolean value)
    {
        stopButton.setDisable(! value);
    }

    public void setVisibleProgress(boolean value)
    {
        progressIndicator.setVisible(value);
    }

    public void setProgress(double value)
    {
        progressIndicator.setProgress(value);
    }

    public double getProgress()
    {
        return progressIndicator.getProgress();
    }
}
