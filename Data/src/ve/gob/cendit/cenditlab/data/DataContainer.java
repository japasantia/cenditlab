package ve.gob.cendit.cenditlab.data;

import java.util.HashMap;
import java.util.Map;

public class DataContainer
{
    private Map<String, Data> dataMap = new HashMap<>();
    private String[] dataNamesArray;

    public DataContainer()
    {}

    public void add(String name, Data field)
    {
        if (name != null && field != null)
        {
            dataMap.put(name, field);
            invalidate();
        }
    }

    public Data remove(String name)
    {
        Data field = dataMap.remove(name);
        if (field != null)
        {
            invalidate();
        }

        return field;
    }

    public int getDataCount()
    {
        return dataMap.size();
    }

    public Data get(String name)
    {
        return dataMap.get(name);
    }

    public Data get(int index)
    {
        return get(getName(index));
    }

    public String getName(int index)
    {
        if (index >= 0 && index < getDataCount())
        {
            return getNames()[index];
        }

        return null;
    }

    public String[] getNames()
    {
        if (dataNamesArray == null)
        {
            dataNamesArray = dataMap.keySet().toArray(new String[dataMap.size()]);
        }

        return dataNamesArray;
    }

    private void invalidate()
    {
        dataNamesArray = null;
    }
}
