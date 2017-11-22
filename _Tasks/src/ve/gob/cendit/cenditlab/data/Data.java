package ve.gob.cendit.cenditlab.data;


public class Data
{
    private Object value;
    private String name;

    public Data(String name)
    {
        setName(name);
    }

    public Data(String name, Object value)
    {
        setName(name);
        set(value);
    }

    public Data(String name, Data data)
    {
        this(name);
        set(data);
    }

    public Data(Data data)
    {
        this(data.getName());
        set(data);
    }

    public void set(Object value)
    {
        this.value = value;
    }

    public void set(Data data)
    {
        value = data.get();
    }

    public Object get()
    {
        return value;
    }

    protected void setName(String value)
    {
        name = value;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return (value != null ? value.toString() : "");
    }
}
