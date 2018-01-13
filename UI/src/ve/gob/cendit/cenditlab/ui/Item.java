package ve.gob.cendit.cenditlab.ui;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class Item<T>
{
    private T value;
    private Node itemView;
    private boolean selected;

    private EventHandler onItemClickedHandler;

    protected Item(T value)
    {
        this(value, null);
    }

    protected Item(T value, ItemView itemView)
    {
        setValue(value);
        setView(itemView);
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

    public void setView(Node view)
    {
        this.itemView = view;
    }

    public Node getView()
    {
        return itemView;
    }

    public void remove()
    {
        getView().removeEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClicked);
    }

    public void setOnItemClickedHandler(EventHandler<T> handler)
    {
        if (handler != null)
        {
            getView().addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseClicked);

            onItemClickedHandler = handler;
        }
    }

    private void onMouseClicked(MouseEvent event)
    {
        onItemClickedHandler.handle(this);
    }

    public interface EventHandler<T>
    {
        void handle(Item<T> item);
    }
}
