package ve.gob.cendit.cenditlab.data;


import java.util.ArrayList;
import java.util.List;

public class EventEmitter<T>
{
    private IEventCaller<T> eventCaller;
    private List<T> eventListenersList;

    public EventEmitter(String name, IEventCaller<T> eventCaller)
    {
        if (eventCaller == null)
        {
            throw new IllegalArgumentException("eventCaller must not be null");
        }

        this.eventCaller = eventCaller;

        eventListenersList = new ArrayList<>();
    }

    public void call(Object source, Object... args)
    {
        eventListenersList.forEach(listener -> eventCaller.call(source, listener, args));
    }

    public void addListener(T listener)
    {
        eventListenersList.add(listener);
    }

    public void removeListener(T listener)
    {
        eventListenersList.remove(listener);
    }

    public void clearListeners()
    {
        eventListenersList.clear();
    }
}