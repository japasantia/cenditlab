package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import ve.gob.cendit.cenditlab.control.Component;
import ve.gob.cendit.cenditlab.control.DataDirection;
import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.data.Options;
import ve.gob.cendit.cenditlab.data.ValueData;

import java.util.Objects;

public class DataContainerView extends TabPane
{
    private static final String FXML_URL = "fxml/data-container-view.fxml";

    @FXML
    private ListView<Data> inputDataListView;

    @FXML
    private ListView<Data> outputDataListView;

    public DataContainerView()
    {
        ViewLoader.load(FXML_URL, this, this);

        initialize();
    }

    public DataContainerView(Data[] inputData, Data[] outputData)
    {
        this();

        addInputData(inputData);
        addOutputData(outputData);
    }

    private void initialize()
    {
        inputDataListView.setCellFactory(listView -> new DataListCell(DataDirection.INPUT));
        outputDataListView.setCellFactory(listView -> new DataListCell(DataDirection.OUTPUT));
    }

    public void addInputData(Data... argsData)
    {
        inputDataListView.getItems().addAll(argsData);
    }

    public void addOutputData(Data... argsData)
    {
        outputDataListView.getItems().addAll(argsData);
    }

    public void loadComponentData(Component component)
    {
        Objects.requireNonNull(component);

        clear();

        addInputData(component.getData(DataDirection.INPUT));
        addOutputData(component.getData(DataDirection.OUTPUT));
    }

    public void clearInputData()
    {
        inputDataListView.getItems().clear();
    }

    public void clearOutputData()
    {
        outputDataListView.getItems().clear();
    }

    public void clear()
    {
        clearInputData();
        clearOutputData();
    }

    private class DataListCell extends ListCell<Data>
    {
        private DataDirection direction;

        public DataListCell(DataDirection direction)
        {
            this.direction = direction;
        }

        @Override
        protected void updateItem(Data data, boolean empty)
        {
            super.updateItem(data, empty);

            if (empty || data ==  null)
                return;

            Node dataView;

            setGraphic(ViewFactory.buildDataView(data, direction));
        }
    }

}
