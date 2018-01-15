package ve.gob.cendit.cenditlab.data;


public class TemperatureData extends NumericData
{
    public static final String DEFAULT_VALUE = "0.0";

    public static final Unit DEFAULT_UNIT = Unit.KELVIN;

    public static final Unit[] VALID_UNITS =
            { Unit.CELSIUS, Unit.KELVIN, Unit.FARENHEIT, Unit.NONE};

    public TemperatureData()
    {
        this(DEFAULT_VALUE, DEFAULT_UNIT);
    }

    public TemperatureData(String scalar, Unit unit)
    {
        super(scalar, unit);

        setValidUnits(VALID_UNITS);
    }

    public TemperatureData(String value)
    {
        super(value);

        setValidUnits(VALID_UNITS);
    }
}
