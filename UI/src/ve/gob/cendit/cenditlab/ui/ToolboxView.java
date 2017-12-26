package ve.gob.cendit.cenditlab.ui;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ToolboxView<T> extends ScrollPane
{
    private static final String FXML_URL ="fxml/toolbox-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private FlowPane containerFlowPane;

    private ObservableList<Item> itemsList;

    private ToolboxViewEvent<Item> onItemClickedHandler;
    private ViewFactory<T> itemViewFactory;


    public ToolboxView()
    {
        viewLoader.load(this, this);
    }

    public void setAll(T... objects)
    {
        containerFlowPane.getChildren().clear();

        addAll(objects);
    }

    public void add(T object)
    {
        if (object == null)
        {
            return;
        }

        Node viewNode = getItemView(object);

        if (viewNode != null && ! containerFlowPane.getChildren().contains(viewNode))
        {
            Item item = new Item(object, viewNode);
            containerFlowPane.getChildren().add(item);
        }
    }

    public void addAll(T... objects)
    {
        Arrays.stream(objects)
                .forEach(object-> add(object));
    }

    public void remove(T object)
    {
        Item item = get(object);

        containerFlowPane.getChildren().remove(object);
    }

    public void removeAll(T... objects)
    {
        Arrays.stream(objects)
                .forEach(object -> remove(object));
    }

    public void clear()
    {
        containerFlowPane.getChildren().clear();
    }

    public Item get (T object)
    {
         Optional<Node> result = containerFlowPane.getChildren()
            .stream()
            .filter(node -> ((Item) node).getItem() == object)
            .findFirst();

         return result.isPresent() ? (Item) result.get() : null;
    }

    public List<Item> getSelectedItems()
    {
        return containerFlowPane.getChildren()
                .stream()
                .map(node -> (Item) node)
                .filter(item -> item.isSelected())
                .collect(Collectors.toList());
    }

    public void setOnItemClicked(ToolboxViewEvent<Item> handler)
    {
        onItemClickedHandler = handler;
    }

    public void setItemViewFactory(ViewFactory<T> factory)
    {
        itemViewFactory = factory;
    }

    private Node getItemView(T item)
    {
        if (itemViewFactory != null)
        {
            return itemViewFactory.getView(item);
        }

        return null;
    }

    private void onItemClicked(Item item)
    {
       if (onItemClickedHandler != null)
       {
           onItemClickedHandler.handle(item);
       }
    }

    public class Item extends Pane
    {
        private T item;
        private boolean selected;

        protected Item(T item)
        {
            this(item, null);
        }

        protected Item(T item, Node view)
        {
            this.item = item;
            setView(view);

            this.setOnMousePressed(event -> onClicked());
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
            this.getChildren().add(node);
        }

        public Node getView()
        {
            return this.getChildren().get(0);
        }

        private void onClicked()
        {
            toggleSelected();

            ToolboxView.this.onItemClicked(this);
        }
    }

    public interface ViewFactory<T>
    {
        Node getView(T item);
    }

    public interface ToolboxViewEvent<E>
    {
        void handle(E arg);
    }
}
