package ve.gob.cendit.cenditlab.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;

public class NoiseSourceSetup extends TitledPane
{
    private static final String FXML_URL = "fxml/noise-source-setup.fxml";

    @FXML
    private RadioButton normalNsRadioButton;

    @FXML
    private RadioButton smartNsRadioButton;

    @FXML
    private CheckBox userTcoldCheckBox;

    @FXML
    private TextField userTcoldTextField;

    @FXML
    private ProgressIndicator loadProgressIndicator;

    @FXML
    private Button loadFromSnsButton;


    public NoiseSourceSetup()
    {
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource(FXML_URL));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try
        {
            fxmlLoader.load();
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        initialize();
    }

    private void initialize()
    {
        normalNsRadioButton.selectedProperty()
                .addListener((observable, oldValue, newValue) ->
                        {
                            userTcoldCheckBox.setDisable(newValue);
                            userTcoldTextField.setDisable(newValue);
                            loadFromSnsButton.setDisable(newValue);
                        });
    }

    @FXML
    private void loadFromSnsButtonClicked(ActionEvent event)
    {
        loadProgressIndicator.setDisable(!loadProgressIndicator.isDisabled());
    }


}
