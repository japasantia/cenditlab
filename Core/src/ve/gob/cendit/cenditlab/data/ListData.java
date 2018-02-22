package ve.gob.cendit.cenditlab.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListData extends Data
{
    private static final String DEFAULT_NAME = "ListData";
    private ArrayList<String> dataArrayList;

    public ListData()
    {
        dataArrayList = new ArrayList<>();

        setName(DEFAULT_NAME);
    }

    public ListData(String list)
    {
        this();

        setName(DEFAULT_NAME);
        setValue(list);
    }

    public ListData(String name, String list)
    {
        this();

        setName(name);
        setValue(list);
    }

    @Override
    public void setValue(String value)
    {
        try
        {
            String[] values = value.split("[,;]");

            dataArrayList.ensureCapacity(value.length());

            Arrays.stream(values)
                    .forEach(v -> dataArrayList.add(v));

            super.setValue(value);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String get(int index)
    {
        return dataArrayList.get(index);
    }

    public Float getAsFloat(int index)
    {
        return Float.parseFloat(dataArrayList.get(index));
    }

    public List<String> getAsStringList()
    {
        return Collections.unmodifiableList(dataArrayList);
    }

    public int size()
    {
        return dataArrayList.size();
    }

    public void clear()
    {
        dataArrayList.clear();
    }
}
