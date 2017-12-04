package ve.gob.cendit.cenditlab.data;

import java.util.LinkedList;
import java.util.List;

public class Field
{
    private String value;
    private Unit unit;

    List<IUpdateListener> listenersList;

    public Field()
    {}

    public Field(String value, Unit unit)
    {
        this.value = value;
        this.unit = unit;
    }

    public String getValue()
    {
        return value;
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setValue(String value)
    {
        this.value = value;

        callUpdateListeners();
    }

    public void setUnit(Unit unit)
    {
        this.unit = unit;

        callUpdateListeners();
    }

    public DataUnits getValidUnits()
    {
        return DataUnits.EMPTY_UNITS;
    }

    public void addUpdateListener(IUpdateListener listener)
    {
        if (listenersList == null)
        {
            listenersList = new LinkedList<>();
        }

        if (listener != null)
        {
            listenersList.add(listener);
        }
    }

    public void removeUpdateListener(IUpdateListener listener)
    {
        if (listenersList != null && listener != null)
        {
            listenersList.remove(listener);
        }
    }

    private void callUpdateListeners()
    {
        if (listenersList != null)
        {
            listenersList.stream()
                    .forEach( listener -> listener.onUpdate(this));
        }
    }

    @Override
    public String toString()
    {
        return String.format("%s %s",
                (value != null ? value : "") ,
                (unit != null ? unit : ""));
    }
}
