package ve.gob.cendit.cenditlab.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class ComponentListView<T> extends Pane
{
    private static final String FXML_URL = "fxml/component-list-view.fxml";

    @FXML
    private ListView<Item<T>> listView;

    private EventHandler<MouseEvent> onComponentViewClickedHandler;

    private ViewFactory viewFactory;

    public ComponentListView()
    {
        ViewLoader.load(FXML_URL, this, this);


        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener(this::onItemSelectionChanged);
    }

    public void enableMultipleSelection(boolean value)
    {
        listView.getSelectionModel()
                .setSelectionMode(value ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
    }

    public void setViewFactory(ViewFactory factory)
    {
        if (factory == null)
        {
            throw new IllegalArgumentException("factory must not be null");
        }

        viewFactory = factory;

        listView.setCellFactory(listView -> new ComponentListCell());
    }

    public ViewFactory getViewFactory()
    {
        return viewFactory;
    }

    public void setItems(T... items)
    {
        listView.getItems().clear();

        addComponents(items);
    }

    public void addComponents(T... items)
    {
        if (items != null)
        {
            Arrays.stream(items)
                    .forEach(item -> listView.getItems().add(new Item(item)));
        }
    }

    public Item<T> getSelectedItem()
    {
        return listView.getSelectionModel().getSelectedItem();
    }
    /*
    public ObservableList<Item<T>> getSelectedItems()
    {
        return listView.getSelectionModel()
                .getSelectedItems();
    }
    */
    public Item<T>[] getSelectedItems()
    {
        return (Item<T>[]) listView.getItems().stream().filter(item -> item.isSelected()).toArray();
    }

    public void setOnComponentViewClicked(EventHandler<MouseEvent> eventHandler)
    {
       onComponentViewClickedHandler = eventHandler;
    }

    public void setOnItemSelectionChanged(ChangeListener<Item<T>> listener)
    {
        if (listener != null)
        {
            listView.getSelectionModel()
                    .selectedItemProperty()
                    .addListener(listener);
        }
    }

    private void onItemSelectionChanged(ObservableValue<? extends Item<T>> observable,
                                        Item<T> oldValue, Item<T> newValue)
    {
        if (newValue != null)
            newValue.toggleSelected();
    }

    private void onComponentListItemClicked(MouseEvent event, Item<T> item)
    {
        if (onComponentViewClickedHandler != null)
        {
            onComponentViewClickedHandler.handle(event);
        }
    }

    private Node getViewForItem(Item<T> item)
    {
        if (viewFactory != null)
        {
            return viewFactory.getView(item);
        }

        return null;
    }

    private class ComponentListCell extends ListCell<Item<T>>
    {
        public ComponentListCell()
        { }

        @Override
        protected void updateItem(Item<T> item, boolean empty)
        {
            super.updateItem(item, empty);

            if (empty || item == null)
                return;

            Node node = getViewForItem(item);
            item.setView(node);

            if (node != null)
            {
                setGraphic(node);

                // node.setOnMouseClicked(event -> onComponentListItemClicked(event));
                // node.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> onComponentListItemClicked(event));
            }
        }
    }

    public static class Item<T>
    {
        private T item;
        private Node itemView;
        private boolean selected;

        public Item(T item)
        {
            this.item = item;
            selected = false;
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void toggleSelected()
        {
            selected = !selected;
        }

        public void setSelected(boolean value)
        {
            selected = value;
        }

        public T getItem()
        {
            return item;
        }

        public void setView(Node node)
        {
            itemView = node;
        }

        public Node getView()
        {
            return itemView;
        }
    }

    public interface ViewFactory<T>
    {
        Node getView(Item<T> item);
    }
}

