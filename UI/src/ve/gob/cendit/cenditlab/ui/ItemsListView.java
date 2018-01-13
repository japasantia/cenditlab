package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

public class ItemsListView<T> extends VBox
{
    private static final String FXML_URL = "fxml/toolbox-list-view.fxml";

    @FXML
    private ListView<Item<T>> listView;

    private ChangeListener<Item<T>> onItemSelectionChangedListener;

    private EventListener<Item<T>> onItemClickedListener;

    private ItemsList<T> itemsList;

    private Item<T> lastClickedItem;

    public ItemsListView()
    {
        ViewLoader.load(FXML_URL, this, this);

        initialize();
    }

    private void initialize()
    {
        itemsList = new ItemsList<>();

        itemsList.setOnAddedItemListener(this::onAddedItem);
        itemsList.setOnRemovedItemListener(this::onRemovedItem);

        enableMultipleSelection(true);

        listView.setCellFactory(listView -> new ItemsListCell());

        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener(this::onItemSelectionChanged);

    }

    public void enableMultipleSelection(boolean value)
    {
        listView.getSelectionModel()
                .setSelectionMode(value ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
    }

    public void load()
    {
        if (listView.getItems().size() == 0)
        {
            itemsList.getItems()
                .forEach(item -> listView.getItems().add(item));
        }
    }

    public void unload()
    {
        listView.getItems().clear();
    }

    public ItemsList<T> getItemsList()
    {
        return itemsList;
    }

    public Item<T> getLastClickedItem()
    {
        return lastClickedItem;
    }

    public void setOnItemClicked(EventListener<Item<T>> listener)
    {
       onItemClickedListener = listener;
    }

    public void setOnItemSelectionChanged(ChangeListener<Item<T>> listener)
    {
        onItemSelectionChangedListener = listener;
    }

    private void onItemSelectionChanged(ObservableValue<? extends Item<T>> observable,
                                        Item<T> oldValue, Item<T> newValue)
    {
        if (onItemSelectionChangedListener != null)
        {
            onItemSelectionChangedListener.changed(observable, oldValue, newValue);
        }
    }

    private void onAddedItem(Item<T> item)
    {
        listView.getItems().add(item);
        item.setOnItemClickedHandler(this::onItemClicked);
    }

    private void onRemovedItem(Item<T> item)
    {
        listView.getItems().remove(item);
        item.remove();
    }

    private void onItemClicked(Item<T> item)
    {
        item.toggleSelected();

        lastClickedItem = item;

        if (onItemClickedListener != null)
        {
            onItemClickedListener.handle(item);
        }
    }

    private class ItemsListCell extends ListCell<Item<T>>
    {
        public ItemsListCell()
        { }

        @Override
        protected void updateItem(Item item, boolean empty)
        {
            super.updateItem(item, empty);

            if (empty || item == null)
                return;

            // setOnMouseClicked(event -> onItemClicked(item));

            setGraphic(item.getView());
        }

        public void toggleSelected()
        {
            updateSelected( ! isSelected() );
        }
    }

    public interface EventListener<E>
    {
        void handle(E arg);
    }
}

