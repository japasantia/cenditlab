package ve.gob.cendit.cenditlab.data;

import ve.gob.cendit.cenditlab.control.EventEmitter;

public class Data
{
    private static final String DEFAULT_NAME = "Data";
    private static final String DEFAULT_VALUE = "";

    private String name;
    private String value;

    private EventEmitter<IUpdateListener> updateEventEmitter;

    public Data()
    {
        this(DEFAULT_NAME);
    }

    public Data(String name)
    {
        this(name, DEFAULT_VALUE);
    }

    public Data(String name, String value)
    {
        updateEventEmitter = new EventEmitter<IUpdateListener>("OnUpdate", this::onUpdateEventCaller);

        this.name = name;
        this.value = value;
    }

    protected void setName(String value)
    {
        name = value;
    }

    public String getName()
    {
        return name;
    }

    public void setValue(String value)
    {
        this.value = value;

        update();
    }

    public String getValue()
    {
        return value;
    }

    public void update()
    {
        updateEventEmitter.call(this, (Object) null);
    }

    public void setUpdateEnabled(boolean value)
    {
        updateEventEmitter.setEnabled(value);
    }

    public boolean isUpdateEnabled()
    {
        return updateEventEmitter.isEnabled();
    }

    public void addUpdateListener(IUpdateListener listener)
    {
        updateEventEmitter.addListener(listener);
    }

    public void removeUpdateListener(IUpdateListener listener)
    {
        updateEventEmitter.removeListener(listener);
    }

    private void onUpdateEventCaller(Object source, IUpdateListener listener, Object... args)
    {
        listener.onUpdate(source);
    }
}
