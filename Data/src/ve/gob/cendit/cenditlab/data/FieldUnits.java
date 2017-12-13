package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;
import java.util.Optional;

public class FieldUnits
{
    public static final FieldUnits EMPTY_UNITS =
            new FieldUnits(Unit.EMPTY_UNIT);

    private final Unit[] units;

    public FieldUnits(Unit... units)
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

        // TODO: elegir el codigo stream o bucle
        /*
        Optional<Unit> result = Arrays.stream(units)
            .filter(u -> magnitude > u.getMultiplier())
            .findFirst();

        return (result.isPresent() ? result.get() : Unit.EMPTY_UNIT);
        */

        for (Unit u : units)
        {
            if (magnitude > u.getMultiplier())
                unit = u;
        }

        return unit;
    }

    public Unit get(String name)
    {
        Unit unit = Unit.EMPTY_UNIT;

        // TODO: elegir el codigo stream o bucle
        /*
        Optional<Unit> result = Arrays.stream(units)
                .filter(u -> name == u.getName())
                .findFirst();

        return (result.isPresent() ? result.get() : Unit.EMPTY_UNIT);
        */

        for (Unit u : units)
        {
            if (name == u.getName())
                unit = u;
        }

        return unit;
    }

    public Unit get(int index)
    {
        return units[index];
    }
}
