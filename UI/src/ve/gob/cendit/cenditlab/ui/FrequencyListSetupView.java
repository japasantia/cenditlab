package ve.gob.cendit.cenditlab.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import ve.gob.cendit.cenditlab.data.FrequencyData;
import ve.gob.cendit.cenditlab.data.IValueValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrequencyListSetupView extends TitledPane
{
    private static final String FXML_URL = "fxml/frequency-list-setup-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private ListView frequencyListView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    private ObservableList<ValueView> valueViewsList;

    private static final IValueValidator valueValidator =
            value -> FrequencyData.isValid(value);

    public FrequencyListSetupView()
    {
        viewLoader.load(this, this);

        valueViewsList = frequencyListView.getItems();
        ValueView valueView = new ValueView(new FrequencyData());
        valueViewsList.add(valueView);
    }

    @FXML
    private void addButtonClicked(MouseEvent event)
    {
        ValueView valueView = new ValueView(new FrequencyData());

        valueViewsList.add(valueView);
    }

    @FXML
    private void removeButtonClicked(MouseEvent event)
    {
        int selectedIndex = frequencyListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0 && selectedIndex < valueViewsList.size())
        {
            valueViewsList.remove(selectedIndex);
        }
    }

    public boolean validate()
    {
        return valueViewsList.stream()
                .allMatch(fieldInput -> fieldInput.validate(valueValidator));
    }

    public List<String> getFrequencies()
    {
        int size = valueViewsList.size();

        ArrayList<String> frequenciesList = new ArrayList<>(size);

        valueViewsList.stream()
                .forEach( viewData -> frequenciesList.add(viewData.toString()) );

        return frequenciesList;
    }
}


