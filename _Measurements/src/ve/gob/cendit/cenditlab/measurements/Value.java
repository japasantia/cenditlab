package ve.gob.cendit.cenditlab.measurements;


public class Value
{
    private Object content;
    private String name;
    private Boolean binded;
    private ValueDirection direction;


    public Value(String name, Object content, ValueDirection direction)
    {
        setName(name);
        set(content);

        this.direction = direction;
    }

    public Value(String name, Value content, ValueDirection direction)
    {
        setName(name);
        set(content);

        this.direction = direction;
    }

    public Value(String name, Object content)
    {
        this(name, content, ValueDirection.BIDIRECTIONAL);
    }

    public Value(String name, Value content)
    {
        this(name, content, ValueDirection.BIDIRECTIONAL);
    }

    public Value(String name)
    {
        this(name, (Object)null);
    }

    public void set(Object content)
    {
        binded = false;
        this.content = content;
    }

    public void set(Value content)
    {
        if (content == null)
        {
            throw new NullPointerException("Value's binded content must not be null");
        }

        if (checkCircularBind(content))
        {
            throw new IllegalArgumentException("Detected circular bind");
        }

        binded = true;
        this.content = content;
    }

    public Object get()
    {
        if (binded)
        {
            return ((Value)content).get();
        }
        else
        {
            return content;
        }
    }

    public String getName()
    {
        return name;
    }

    public Boolean isBinded()
    {
        return binded;
    }

    private void setName(String name)
    {
        if (name == null)
        {
            throw new NullPointerException("Value's name must not be null");
        }

        this.name = name;
    }

    private boolean checkCircularBind(Value content)
    {
        Object current = content.get();

        while (current instanceof Value)
        {
            if (current == this)
            {
                return true;
            }
            current = ((Value) current).get();
        }
        return false;
    }
}
