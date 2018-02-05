package ve.gob.cendit.cenditlab.data;

public class Setup
{
    private EventEmitter<IUpdateListener> applyEventEmitter;

    public Setup()
    {
        applyEventEmitter = new EventEmitter<>("OnApply", this::onApplyEventCaller);
    }

    public void apply()
    {
        applyEventEmitter.call(this, (Object[])null);
    }

    public void addApplyListener(IUpdateListener listener)
    {
        applyEventEmitter.addListener(listener);
    }

    public void removeApplyListener(IUpdateListener listener)
    {
        applyEventEmitter.removeListener(listener);
    }

    private void onApplyEventCaller(Object source, IUpdateListener listener, Object... args)
    {
        listener.onUpdate(source);
    }
}
