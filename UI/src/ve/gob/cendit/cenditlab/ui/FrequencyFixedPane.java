package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import ve.gob.cendit.cenditlab.data.FrequencyField;

import java.io.IOException;

public class FrequencyFixedPane extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-fixed-pane.fxml";

    @FXML
    FieldInput fixedFrequencyFieldInput;

    FrequencyField fixedFrequencyField;

    public FrequencyFixedPane()
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
        fixedFrequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
    }

    public void setFixedFrequency(FrequencyField value)
    {
        fixedFrequencyField = value;

        fixedFrequencyFieldInput.setField(value);
    }

    public FrequencyField getFixedFrequency()
    {
        return fixedFrequencyField;
    }
}
