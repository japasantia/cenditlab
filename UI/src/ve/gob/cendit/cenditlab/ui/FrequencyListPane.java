package ve.gob.cendit.cenditlab.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import ve.gob.cendit.cenditlab.data.FrequencyField;
import ve.gob.cendit.cenditlab.data.IValueValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrequencyListPane extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-list-pane.fxml";

    @FXML
    private ListView frequencyListView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    private ObservableList<FieldInput> fieldsListInput;

    private static final IValueValidator valueValidator =
            value -> FrequencyField.isValid(value);

    public FrequencyListPane()
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

        fieldsListInput = frequencyListView.getItems();
        FieldInput fieldInput = new FieldInput();
        fieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
        fieldsListInput.add(fieldInput);
    }

    @FXML
    private void addButtonClicked(MouseEvent event)
    {
        FieldInput fieldInput = new FieldInput();
        fieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);

        fieldsListInput.add(fieldInput);
    }

    @FXML
    private void removeButtonClicked(MouseEvent event)
    {
        int selectedIndex = frequencyListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0 && selectedIndex < fieldsListInput.size())
        {
            fieldsListInput.remove(selectedIndex);
        }
    }

    public boolean validate()
    {
        return fieldsListInput.stream()
                .allMatch(fieldInput -> fieldInput.validate(valueValidator));
    }

    public List<String> getFrequencies()
    {
        int size = fieldsListInput.size();

        ArrayList<String> frequenciesList = new ArrayList<>(size);

        fieldsListInput.stream()
                .forEach(fieldInput ->
                    {
                        frequenciesList.add(fieldInput.toString());
                    });

        return frequenciesList;
    }
}


