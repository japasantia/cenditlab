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
import ve.gob.cendit.cenditlab.data.EnrData;
import ve.gob.cendit.cenditlab.data.FrequencyData;

import java.io.IOException;

public class EnrTablePane extends TitledPane
{
    private static final String FXML_URL = "fxml/enr-table-pane.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private ListView containerListView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    private ObservableList<HBox> enrEntriesList;

    public EnrTablePane()
    {
        viewLoader.load(this, this);

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

        FrequencyData frequencyField = new FrequencyData();
        EnrData enrField = new EnrData();

        ValueView frequencyValueView = new ValueView();
        ValueView enrValueView = new ValueView();

        frequencyValueView.setData(frequencyField);
        enrValueView.setData(enrField);

        frequencyValueView.setChoiceUnits(FrequencyData.FIELD_UNITS);
        enrValueView.setChoiceUnits(EnrData.FIELD_UNITS);

        entryHBox.getChildren().addAll(frequencyValueView, enrValueView);

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
