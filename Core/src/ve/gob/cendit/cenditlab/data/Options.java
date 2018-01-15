package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Options
{
    private String name;
    private List<String> valuesList;

    private String selected;
    private int selectedIndex;

    public Options(String name, String... values)
    {
        this.name = name;
        this.valuesList = Arrays.asList(values);

        selected = getDefault();
    }

    public String getName()
    {
        return name;
    }

    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    public void setSelected(String value)
    {
        int index =  valuesList.indexOf(value);

        if (index != -1)
        {
            selected = value;
            selectedIndex = index;
        }
    }

    public String getSelected()
    {
        return selected;
    }

    public String getDefault()
    {
        return valuesList.get(0);
    }

    public boolean hasOption(String value)
    {
        return valuesList.indexOf(value) != -1;
    }

    public boolean isSelected(String value)
    {
        return selected == value;
    }

    public boolean isSelected(int index)
    {
        return selectedIndex == index;
    }

    public List<String> getValues()
    {
        return Collections.unmodifiableList(valuesList);
    }

    @Override
    public String toString()
    {
        return getSelected();
        // return String.format("%s: %s", getSelected());
    }
}
