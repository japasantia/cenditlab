package ve.gob.cendit.cenditlab.data;

public class ValueData extends Data
{
    private String value;
    private Unit unit;
    private DataUnits dataUnits = DataUnits.EMPTY_UNITS;

    public ValueData()
    {
        this("", Unit.EMPTY_UNIT);
    }

    public ValueData(String value, Unit unit)
    {
        this.value = value;
        this.unit = unit;
    }

    public void setValue(String value)
    {
        this.value = value;

        update();
    }

    public void setUnit(Unit unit)
    {
        this.unit = unit;

        update();
    }

    public String getValue()
    {
        return value;
    }

    public Unit getUnit()
    {
        return unit;
    }

    public void setValidUnits(DataUnits units)
    {
        dataUnits = units;
    }

    public DataUnits getValidUnits()
    {
        return dataUnits;
    }
}
