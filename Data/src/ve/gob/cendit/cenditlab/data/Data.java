package ve.gob.cendit.cenditlab.data;

public class Data
{
    private String name;

    public Data(String name)
    {
        setName(name);
    }

    protected void setName(String value)
    {
        name = value;
    }

    public String getName()
    {
        return name;
    }
}
