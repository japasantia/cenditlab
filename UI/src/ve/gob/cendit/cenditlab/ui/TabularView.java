package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import ve.gob.cendit.cenditlab.data.ListData;

public class TabularView extends ScrollPane
{
    private static final ViewLoader viewLoader = new ViewLoader("fxml/tabular-view.fxml");

    @FXML
    private HBox containerHBox;
    
    private ItemsList<ListData> columnsItems;

    public TabularView()
    {
        viewLoader.load(this, this);

        initialize();
    }
    
    private void initialize()
    {
        columnsItems = new ItemsList<>();

        columnsItems.setOnAddedItemListener(this::onAddedColumn);
        columnsItems.setOnRemovedItemListener(this::onRemovedColumn);
        columnsItems.setViewFactory(this::getColumnView);
    }

    public ItemsList<ListData> getItems()
    {
        return columnsItems;
    }

    private void onAddedColumn(Item<ListData> item)
    {
        containerHBox.getChildren().add(item.getView());
    }

    private void onRemovedColumn(Item<ListData> item)
    {
        containerHBox.getChildren().remove(item.getView());
    }

    private Node getColumnView(Item<ListData> item)
    {
        ListDataView listDataView = (ListDataView) item.getView();

        if (listDataView == null)
        {
            listDataView = new ListDataView(item.getValue());
        }

        return listDataView;
    }
}
