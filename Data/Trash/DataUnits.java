package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;

public class DataUnits
{
    public static final DataUnits EMPTY_UNITS =
            new DataUnits(Unit.EMPTY_UNIT);

    private final Unit[] units;

    public DataUnits(Unit... units)
    {
        if (units == null || units.length == 0)
            throw new IllegalArgumentException("units must not be empty");

        this.units = units;
    }

    public Unit[] getUnits()
    {
        return units;
    }

    public String[] getUnitNames()
    {
        return (String[]) Arrays.stream(units)
                .map(unit -> unit.getName())
                .toArray();
    }

    public Unit getUnitForMagnitude(float magnitude)
    {
        Unit unit = Unit.EMPTY_UNIT;

        for (Unit u : units)
        {
            if (magnitude >= u.getMultiplier())
                unit = u;
        }

        return unit;
    }

    public Unit get(String name)
    {
        Unit unit = Unit.EMPTY_UNIT;

        if (name == null)
            return unit;

        for (Unit u : units)
        {
            if (name.equalsIgnoreCase(u.getName()))
                unit = u;
        }

        return unit;
    }

    public Unit get(int index)
    {
        return units[index];
    }
}
