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

    private ItemsList<T> itemsList;

    public ContainerView()
    {
        // viewLoader.load(this, this);

        setContainerPane(new FlowPane());

        initialize();
    }

    private void initialize()
    {
        itemsList = new ItemsList<>();

        itemsList.setOnAddedItemListener(this::onAddedItem);
        itemsList.setOnRemovedItemListener(this::onRemovedItem);
    }

    public void load()
    {
        if (itemsList.getItems().size() == 0)
        {
            itemsList.getItems()
                .forEach(item -> containerPane.getChildren().add(item.getView()));
        }
    }

    public void unload()
    {
        itemsList.getItems().clear();
    }

    public ItemsList<T> getItemsList()
    {
        return itemsList;
    }

    public void setOnItemClickedListener(ContainerEventListener<Item> listener)
    {
        onItemClickedListener = listener;
    }

    private void onItemClicked(Item<T> item)
    {
        if (onItemClickedListener != null)
        {
            onItemClickedListener.handle(item);
        }
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

    private void onAddedItem(Item<T> item)
    {
        ItemView itemView = item.getView();

        if (itemView != null)
        {
            containerPane.getChildren().add(itemView);

            item.setOnItemClickedHandler(this::onItemClicked);
        }
    }

    private void onRemovedItem(Item<T> item)
    {
        containerPane.getChildren().remove(item.getView());

        item.remove();
    }

    public interface ContainerEventListener<E>
    {
        void handle(E arg);
    }
}