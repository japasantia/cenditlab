package ve.gob.cendit.cenditlab.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListData extends Data
{
    private ArrayList<String> dataArrayList;

    public ListData()
    {
        dataArrayList = new ArrayList<>();
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
