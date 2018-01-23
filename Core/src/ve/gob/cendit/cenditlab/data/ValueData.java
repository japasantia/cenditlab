package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ValueData extends Data
{
    private Unit unit;
    private Multiplier multiplier = Multiplier.UNIT;

    private Unit[] validUnits;

    public void setUnit(Unit value)
    {
        if (isValidUnit(value))
        {
            unit = value;

            update();
        }
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setMultiplier(Multiplier value)
    {
        multiplier = value;

        update();
    }

    public Multiplier getMultiplier()
    {
        return multiplier;
    }

    public void setValidUnits(Unit... units)
    {
        Objects.requireNonNull(units, "units must not be null");

        validUnits = units;
    }

    public Unit[] getValidUnits()
    {
        return validUnits;
    }

    public List<Unit> getValidUnitsList()
    {
        return Arrays.asList(validUnits);
    }

    public boolean isValidUnit(Unit unit)
    {
        if (validUnits != null)
        {
            return Arrays.stream(validUnits)
                    .anyMatch(u -> u == unit);
        }

        return true;
    }
}
