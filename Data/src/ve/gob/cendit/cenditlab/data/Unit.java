package ve.gob.cendit.cenditlab.data;

public class Unit
{
    private float multiplier;
    private String name;

    public static final Unit EMPTY_UNIT =
            new Unit("", 1.0f);

    public Unit(String name, float multiplier)
    {
        if (name == null) throw new IllegalArgumentException("name must not be null");

        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName()
    {
        return name;
    }

    public float getMultiplier()
    {
        return multiplier;
    }

    public Unit getUnitForMagnitude(float magnitude)
    {
        return Unit.EMPTY_UNIT;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
