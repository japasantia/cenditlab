package ve.gob.cendit.cenditlab.data;


public class DataBinded extends Data
{
    private Data dataBinded;

    public DataBinded(String name, Data data)
    {
        super(name);
        dataBinded = data;
    }

    @Override
    public void set(Object value)
    {
        dataBinded.set(value);
    }

    @Override
    public void set(Data data)
    {
        dataBinded.set(data);
    }

    @Override
    public Object get()
    {
        return dataBinded.get();
    }

    @Override
    public String toString()
    {
        return dataBinded.toString();
    }
}
