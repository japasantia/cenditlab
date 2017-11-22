package ve.gob.cendit.cenditlab.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import ve.gob.cendit.cenditlab.data.EnrField;
import ve.gob.cendit.cenditlab.data.FrequencyField;

import java.io.IOException;

public class EnrTablePane extends TitledPane
{
    private static final String FXML_URL = "fxml/enr-table-pane.fxml";

    @FXML
    private ListView containerListView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    private ObservableList<HBox> enrEntriesList;

    public EnrTablePane()
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
        enrEntriesList = FXCollections.<HBox>observableArrayList();
        containerListView.setItems(enrEntriesList);
    }

    @FXML
    private void addButtonClicked(ActionEvent event)
    {
        addEntry();
    }

    @FXML
    private void removeButtonClicked(ActionEvent event)
    {
        int selectedIndex = containerListView.getSelectionModel().getSelectedIndex();

        removeEntry(selectedIndex);
    }

    public void addEntry()
    {
        HBox entryHBox = new HBox();

        FrequencyField frequencyField = new FrequencyField();
        EnrField enrField = new EnrField();

        FieldInput frequencyFieldInput = new FieldInput();
        FieldInput enrFieldInput = new FieldInput();

        frequencyFieldInput.setField(frequencyField);
        enrFieldInput.setField(enrField);

        frequencyFieldInput.setChoiceUnits(FrequencyField.FIELD_UNITS);
        enrFieldInput.setChoiceUnits(EnrField.FIELD_UNITS);

        entryHBox.getChildren().addAll(frequencyFieldInput, enrFieldInput);

        enrEntriesList.add(entryHBox);
    }

    public void removeEntry(int index)
    {
        if (index >= 0 && index < enrEntriesList.size())
        {
            enrEntriesList.remove(index);
        }
    }
}
