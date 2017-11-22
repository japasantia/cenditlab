package ve.gob.cendit.cenditlab.tasks;


import ve.gob.cendit.cenditlab.views.View;

public abstract class Component
{
    private String name;
    private String description;
    private String iconUrl;

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public String getIcon() { return iconUrl; }

    public abstract ComponentSlots getSlots(/* Context context */);

    // protected abstract void setSlots(ComponentSlots slots);

    // protected abstract void setContext(/* Context context */);

    protected abstract void setContext(int context);

    // protected abstract Context getContext();

    // public abstract View getView(Context context);

    public abstract View getView(int context);
}
