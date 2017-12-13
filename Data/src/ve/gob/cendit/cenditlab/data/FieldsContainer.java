package ve.gob.cendit.cenditlab.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FieldsContainer
{
    private Map<String, Field> fieldsMap = new HashMap<>();
    private String[] fieldNamesArray;

    public FieldsContainer()
    {}

    public void add(String name, Field field)
    {
        if (name != null && field != null)
        {
            fieldsMap.put(name, field);
            invalidate();
        }
    }

    public Field remove(String name)
    {
        Field field = fieldsMap.remove(name);
        if (field != null)
        {
            invalidate();
        }

        return field;
    }

    public int getFieldsCount()
    {
        return fieldsMap.size();
    }

    public Field get(String name)
    {
        return fieldsMap.get(name);
    }

    public Field get(int index)
    {
        return get(getName(index));
    }

    public String getName(int index)
    {
        if (index >= 0 && index < getFieldsCount())
        {
            return getNames()[index];
        }

        return null;
    }

    public String[] getNames()
    {
        if (fieldNamesArray == null)
        {
            fieldNamesArray = fieldsMap.keySet().toArray(new String[fieldsMap.size()]);
        }

        return fieldNamesArray;
    }

    private void invalidate()
    {
        fieldNamesArray = null;
    }
}
