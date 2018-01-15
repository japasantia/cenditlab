package ve.gob.cendit.cenditlab.control;

import java.util.ArrayList;
import java.util.List;

public class EventEmitter<T>
{
    private IEventCaller<T> eventCaller;
    private List<T> eventListenersList;
    private boolean enabled;

    public EventEmitter(String name, IEventCaller<T> callerFunction)
    {
        if (callerFunction == null)
        {
            throw new IllegalArgumentException("callerFunction must not be null");
        }

        enabled = true;
        eventCaller = callerFunction;

        eventListenersList = new ArrayList<>();
    }

    public void call(Object source, Object... args)
    {
        if (isEnabled())
        {
            eventListenersList.forEach(listener -> eventCaller.call(source, listener, args));
        }
    }

    public void addListener(T listener)
    {
        if (listener !=  null)
        {
            eventListenersList.add(listener);
        }
    }

    public void removeListener(T listener)
    {
        if (listener != null)
        {
            eventListenersList.remove(listener);
        }
    }

    public void clearListeners()
    {
        eventListenersList.clear();
    }

    public void setEnabled(boolean value)
    {
        enabled = value;
    }

    public boolean isEnabled()
    {
        return enabled;
    }
}