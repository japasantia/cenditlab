package ve.gob.cendit.cenditlab.tasks;

public abstract class Task
{
    private String name;

    private ComponentSlots slots;

    public Task(String name, ComponentSlots slots)
    {
        setName(name);

        setSlots(slots);
    }

    public Task(String name, Slot... slots)
    {
        this(name, new ComponentSlots(slots));
    }

    protected Task()
    { }

    final protected void setName(String name)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("Task name must not be null");
        }

        this.name = name;
    }

    final protected void setSlots(ComponentSlots slots)
    {
        if (slots == null)
        {
            throw new IllegalArgumentException("Task slots must not be null");
        }

        this.slots = slots;
    }

    final protected void setDataSlots(Slot... slots)
    {
        this.slots = new ComponentSlots(slots);
    }

    public String getName()
    {
        return name;
    }

    public ComponentSlots getSlots()
    {
        return slots;
    }

    public abstract void execute();
}
