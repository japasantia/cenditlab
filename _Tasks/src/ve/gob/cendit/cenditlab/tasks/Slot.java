package ve.gob.cendit.cenditlab.tasks;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.data.DataDirection;

/**
 * Created by jarias on 23/08/17.
 */
public class Slot
{
    private final String name;
    private final Data defaultData;
    private final DataDirection direction;

    public Slot(String name)
    {
        this(name, null, null);
    }

    public Slot(String name, Data defaultData)
    {
        this(name, defaultData, null);
    }

    public Slot(String name, Data defaultData, DataDirection direction)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("name must not be null");
        }

        this.name = name;
        this.defaultData = defaultData;

        this.direction = (direction == null) ? DataDirection.BIDIRECTIONAL : direction;
    }

    public String getName()
    {
        return name;
    }

    public Data getDefaultData()
    {
        return defaultData;
    }

    public DataDirection getDirection()
    {
        return direction;
    }

    @Override
    public String toString()
    {
        return String.format("%s [%s, %s]",
                name,
                defaultData != null ? defaultData : "Empty",
                direction);
    }
}
