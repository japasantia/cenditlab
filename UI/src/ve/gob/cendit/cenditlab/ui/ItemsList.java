package ve.gob.cendit.cenditlab.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.util.*;
import java.util.stream.Collectors;

public class ItemsList<T> 
{
    private List<Item<T>> list;

    private EventHandler<Item<T>> onAddedItemHandler;
    private EventHandler<Item<T>> onRemovedItemHandler;

    private ViewFactory<T> viewFactory;

    public ItemsList()
    {
        list = new ArrayList<>();
    }

    public ItemsList(ObservableList<Item<T>> list)
    {
        setList(list);
    }

    protected void setList(List<Item<T>> list)
    {
        Objects.requireNonNull(list, "list must not be null");

        this.list = list;
    }
    
    public void setAll(T... objects)
    {
        list.clear();

        addAll(objects);
    }
    
    public void addAll(T... objects)
    {
        if (objects != null && objects.length > 0)
        {
            Arrays.stream(objects)
                    .forEach(this::add);
        }
    }
    
    public void add(T object)
    {
        if (object != null)
        {
            Item<T> item = new Item<>(object);

            item.setView(getViewForItem(item));

            list.add(item);

            onAddedItem(item);
        }
    }
    
    public T get(int index)
    {
        return getItem(index).getValue();
    }
    
    public Item<T> getItem(int index)
    {
        return list.get(index);
    }

    public Item<T> getItem(T value)
    {
        return list.stream()
            .filter(item -> item != null && item.getValue() == value)
            .findFirst()
            .orElse(null);
    }
    
    public List<T> getAll()
    {
        List<T> valuesList = new ArrayList<>();

        getItems().forEach(item -> valuesList.add(item.getValue()));

        return valuesList;
    }
    
    public List<Item<T>> getItems()
    {
        return list;
    }
    
    public List<T> getAllSelected()
    {
        List<T> selectedList = new ArrayList<>();

        getAllSelectedItems()
                .forEach(item -> selectedList.add(item.getValue()));

        return selectedList;
    }
    
    public List<Item<T>> getAllSelectedItems()
    {
        return list.stream().filter(Item::isSelected).collect(Collectors.toList());
    }
    
    public void remove(int index)
    {
        Item<T> item = list.get(index);

        if (item != null)
        {
            list.remove(item);
            onRemovedItem(item);
        }
    }
    
    public void remove(T object)
    {
        Optional<Item<T>> result = list
                .stream()
                .filter(item -> item.getValue() == object)
                .findFirst();

        if (result.isPresent())
        {
            Item<T> item = result.get();

            list.remove(item);
            onRemovedItem(item);
        }
    }
    
    public void removeAll(T... objects)
    {
        if (objects != null && objects.length > 0)
        {
            Arrays.stream(objects)
                    .forEach(this::remove);
        }
    }
    
    public void clear()
    {
        list.clear();
    }

    public void setOnAddedItemListener(EventHandler<Item<T>> handler)
    {
        onAddedItemHandler = handler;
    }

    public void setOnRemovedItemListener(EventHandler<Item<T>> handler)
    {
        onRemovedItemHandler = handler;
    }

    public void setViewFactory(ViewFactory<T> factory)
    {
        viewFactory = factory;
    }

    protected Node getViewForItem(Item<T> item)
    {
        Node itemView;

        if (viewFactory != null)
        {
            itemView = viewFactory.getView(item);
        }
        else
        {
            itemView = new Label(item.getValue().toString());
        }

        return itemView;
    }

    private void onAddedItem(Item<T> item)
    {
        if (onAddedItemHandler != null)
        {
            onAddedItemHandler.handle(item);
        }
    }

    private void onRemovedItem(Item<T> item)
    {
        if (onRemovedItemHandler != null)
        {
            onRemovedItemHandler.handle(item);
        }
    }

    public interface EventHandler<R>
    {
        void handle(R item);
    }

    public interface ViewFactory<T>
    {
        Node getView(Item<T> item);
    }
}
