package ve.gob.cendit.cenditlab.tasks;


public class Value
{
    private Object content;
    private final String name;

    public Value(String name)
    {
        this.name = name;
    }

    public Value(Value copyValue)
    {
        name = copyValue.getName();
        content = copyValue.get();
    }

    public Object set(Object newValue)
    {
        Object oldContent = content;
        content = newValue;

        return oldContent;
    }

    public Object set(Value newValue)
    {
        Object oldContent = content;
        content = newValue.get();

        return oldContent;
    }

    public Object get()
    {
        return content;
    }

    public String getName()
    {
        return name;
    }

    public void addQualifier(ValueQualifier qualifier)
    {

    }
}
