package ve.gob.cendit.cenditlab.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolboxListView<T> extends VBox
{
    private static final String FXML_URL = "fxml/toolbox-list-view.fxml";

    @FXML
    private ListView<Item> listView;

    private ChangeListener<Item> onItemSelectionChangedListener;

    private ToolboxListEventListener<Item> onItemClickedListener;

    private ViewFactory<Item> viewFactory;

    public ToolboxListView()
    {
        ViewLoader.load(FXML_URL, this, this);

        enableMultipleSelection(true);

        listView.setCellFactory(listView -> new ToolboxListCell());

        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener(this::onItemSelectionChanged);
    }

    public void enableMultipleSelection(boolean value)
    {
        listView.getSelectionModel()
                .setSelectionMode(value ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
    }

    public void setViewFactory(ViewFactory<Item> factory)
    {
        if (factory == null)
        {
            throw new IllegalArgumentException("factory must not be null");
        }

        viewFactory = factory;
    }

    public ViewFactory<Item> getViewFactory()
    {
        return viewFactory;
    }

    public void setItems(T... objects)
    {
        listView.getItems().clear();

        addItems(objects);
    }

    public void addItems(T... objects)
    {
        if (objects != null && objects.length > 0)
        {
            Arrays.stream(objects)
                .forEach(this::addItem);
        }
    }

    public void addItem(T object)
    {
        if (object != null)
        {
            listView.getItems().add(new Item(object));
        }
    }

    public void removeItems(T... objects)
    {
        if (objects != null && objects.length > 0)
        {
            Arrays.stream(objects)
                .forEach(this::removeItem);
        }
    }

    public void removeItem(T object)
    {
        List<Item> toRemoveList = listView.getItems()
            .filtered(item -> item.getValue() == object);

        listView.getItems().remove(toRemoveList);
    }

    public void clearItems()
    {
        listView.getItems().clear();
    }

    public List<T> getValues()
    {
        List<T> valuesList = new ArrayList<>();

        listView.getItems()
            .forEach(item -> valuesList.add(item.getValue()));

        return valuesList;
    }

    public T getSelected()
    {
        return getSelectedItem().getValue();
    }

    public Item getSelectedItem()
    {
        return listView.getSelectionModel().getSelectedItem();
    }

    public T getSelectedValue()
    {
        return getSelectedItem().getValue();
    }

    public List<T> getSelectedValues()
    {
        List<T> selectedList = new ArrayList<>();

        getSelectedItems()
            .forEach(item -> selectedList.add(item.getValue()));

        return selectedList;
    }

    public List<Item> getSelectedItems()
    {
        return listView.getItems().filtered(Item::isSelected);
    }

    public void setOnItemClicked(ToolboxListEventListener<Item> listener)
    {
       onItemClickedListener = listener;
    }

    public void setOnItemSelectionChanged(ChangeListener<Item> listener)
    {
        onItemSelectionChangedListener = listener;
    }

    private void onItemSelectionChanged(ObservableValue<? extends Item> observable,
                                        Item oldValue, Item newValue)
    {
        if (onItemSelectionChangedListener != null)
        {
            onItemSelectionChangedListener.changed(observable, oldValue, newValue);
        }
    }

    private void onItemClicked(Item item)
    {
        item.toggleSelected();

        if (onItemClickedListener != null)
        {
            onItemClickedListener.handle(item);
        }
    }

    private Node getViewForItem(Item item)
    {
        Node view;

        if (viewFactory != null)
        {
            view = viewFactory.getView(item);
        }
        else if (item != null)
        {
            view = item.getView();
        }
        else
        {
            view = new Label(item.getValue().toString());
        }

        return view;
    }

    private class ToolboxListCell extends ListCell<Item>
    {
        public ToolboxListCell()
        { }

        @Override
        protected void updateItem(Item item, boolean empty)
        {
            super.updateItem(item, empty);

            if (empty || item == null)
                return;

            Node node = getViewForItem(item);
            item.setView(node);

            setOnMouseClicked(event -> onItemClicked(item));

            if (node != null)
            {
                setGraphic(node);
            }
        }

        public void toggleSelected()
        {
            updateSelected( ! isSelected() );
        }
    }

    public class Item
    {
        private T value;
        private Node viewNode;
        private boolean selected;

        protected Item(T value)
        {
            this(value, null);
        }

        protected Item(T value, Node view)
        {
            this.value = value;
            setView(view);
        }

        public boolean isSelected()
        {
            return selected;
        }

        public void setSelected(boolean value)
        {
            selected = value;
        }

        public void toggleSelected()
        {
            selected = !selected;
        }

        public void setValue(T value)
        {
            this.value = value;
        }

        public T getValue()
        {
            return value;
        }

        public void setView(Node node)
        {
            viewNode = node;
        }

        public Node getView()
        {
            return viewNode;
        }
    }

    public interface ToolboxListEventListener<E>
    {
        void handle(E arg);
    }

    public interface ViewFactory<R>
    {
        Node getView(R item);
    }
}

