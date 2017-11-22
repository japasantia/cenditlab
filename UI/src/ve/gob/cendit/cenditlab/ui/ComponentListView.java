package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import ve.gob.cendit.cenditlab.control.Component;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class ComponentListView<T extends Component> extends ListView<T>
{
    private static final String FXML_URL = "fxml/component-list-view.fxml";

    private static final String DEFAULT_TITLE = "";

    private ViewType viewType = ViewType.LIST_ICON;

    private EventHandler<MouseEvent> onItemClickedHandler;

    private T[] components;

    public ComponentListView()
    {
        ViewLoader.load(FXML_URL, this, this);

        this.setCellFactory(listView -> new ComponentListCell());
    }

    public void setViewType(ViewType value)
    {
        viewType = value;
    }

    public void enableMultipleSelection(boolean value)
    {
        this.getSelectionModel()
                .setSelectionMode(value ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
    }

    public void setComponents(T... components)
    {
        this.getItems().clear();

        addComponents(components);
    }

    public void addComponents(T... components)
    {
        if (components != null)
        {
            this.getItems().addAll(components);
        }
    }

    public T getSelectedItem()
    {
        return this.getSelectionModel().getSelectedItem();
    }

    public ObservableList<T> getSelectedItems()
    {
        return this.getSelectionModel().getSelectedItems();
    }

    public void setOnListItemClicked(EventHandler<MouseEvent> eventHandler)
    {
       onItemClickedHandler = eventHandler;
    }

    public void setOnListSelectionChanged(ChangeListener<T> listener)
    {
        this.getSelectionModel()
                .selectedItemProperty()
                .addListener(listener);
    }

    private class ComponentListCell extends ListCell<T>
    {
        public ComponentListCell()
        { }

        @Override
        protected void updateItem(T componentItem, boolean empty)
        {
            super.updateItem(componentItem, empty);

            if (empty || componentItem == null)
                return;

            // TODO: revisar por elemento null
            Node node = componentItem.getView(viewType);

            if (node != null)
            {
                setGraphic(node);
                node.setOnMouseClicked(event ->
                {
                    onItemClickedHandler.handle(event);
                });
            }
        }
    }
}
