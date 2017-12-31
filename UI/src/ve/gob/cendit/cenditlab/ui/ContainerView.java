package ve.gob.cendit.cenditlab.ui;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContainerView<T> extends ScrollPane
{
    // private static final ViewLoader viewLoader = new ViewLoader("fxml/container-view.fxml");

    private Pane containerPane;

    private ContainerEventListener<Item> onItemClickedListener;

    private ViewFactory<Item> viewFactory;

    public ContainerView()
    {
        // viewLoader.load(this, this);

        setContainerPane(new FlowPane());
    }

    public void setViewFactory(ViewFactory<Item> factory)
    {
        viewFactory = factory;
    }

    public ViewFactory<Item> getViewFactory()
    {
        return viewFactory;
    }

    public void setItems(T... objects)
    {
        containerPane.getChildren().clear();

        addItems(objects);
    }

    public void addItems(T... objects)
    {
        if (objects != null)
        {
            Arrays.stream(objects)
                    .forEach(this::addItem);
        }
    }

    public void addItem(T object)
    {
        Item item = new Item(object);

        containerPane.getChildren().add(item.getView());
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
        Optional<Node> result = containerPane.getChildren().stream()
                .filter(node -> ( (Item)node.getUserData() ).getValue() == object)
                .findFirst();

        if (result.isPresent())
        {
            ((Item)result.get().getUserData()).remove();
        }
    }

    public void clearItems()
    {
        containerPane.getChildren().clear();
    }

    public List<T> getItems()
    {
        return containerPane.getChildren()
            .stream()
            .map( node -> ( (Item)node.getUserData() ).getValue() )
            .collect(Collectors.toList());
    }

    public List<T> getSelectedItems()
    {
        List<T> selectedItemsList = new ArrayList<>();

        containerPane.getChildren()
            .stream()
            .forEach(node -> {
                Item item = (Item) node.getUserData();
                if (item.isSelected())
                {
                    selectedItemsList.add(item.getValue());
                }
            });

        return selectedItemsList;
    }

    public void setOnItemClickedListener(ContainerEventListener<Item> listener)
    {
        onItemClickedListener = listener;
    }

    private void onItemClicked(Item item)
    {
        if (onItemClickedListener != null)
        {
            onItemClickedListener.handle(item);
        }
    }

    protected Node getViewForItem(Item item)
    {
        Node viewNode;

        if (viewFactory != null)
        {
            viewNode = viewFactory.getView(item);
        }
        else
        {
            viewNode = new Label(item.getValue().toString());
        }

        return viewNode;
    }

    public Pane getContainerPane()
    {
        return containerPane;
    }

    public void setContainerPane(Pane pane)
    {
        if (pane != null)
        {
            if (containerPane != null)
            {
                pane.getChildren().setAll(containerPane.getChildren());
            }

            containerPane = pane;

            containerPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            setContent(containerPane);
        }
    }

    public class Item
    {
        private T value;
        private Node viewNode;
        private boolean selected;

        private EventHandler<MouseEvent> onItemClickedHandler =
                event -> onItemClicked(this);

        protected Item(T value)
        {
            setValue(value);

            viewNode = getViewForItem(this);

            viewNode.setUserData(this);

            viewNode.addEventFilter(MouseEvent.MOUSE_CLICKED,
                    onItemClickedHandler);
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

        protected void remove()
        {
            viewNode.removeEventFilter(MouseEvent.MOUSE_CLICKED,
                    onItemClickedHandler);

            getContainerPane().getChildren().remove(viewNode);

            viewNode.setUserData(null);
        }
    }

    public interface ContainerEventListener<E>
    {
        void handle(E arg);
    }

    public interface ViewFactory<R>
    {
        Node getView(R item);
    }
}