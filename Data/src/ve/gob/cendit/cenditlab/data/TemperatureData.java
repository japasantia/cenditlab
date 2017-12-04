package ve.gob.cendit.cenditlab.data;


public class TemperatureData extends NumericData
{
    public static final String DEFAULT_VALUE = "0.0";

    public static final Unit KELVIN =
            new Unit("K", 1.0f);
    public static final Unit CELSIUS =
            new Unit("C", 1.0f);
    public static final Unit FARENHEIT =
            new Unit("F", 1.0f);

    public static final Unit DEFAULT_UNIT = KELVIN;

    public static final DataUnits FIELD_UNITS =
            new DataUnits(KELVIN, CELSIUS, FARENHEIT);

    public TemperatureData()
    {
        this(DEFAULT_VALUE, DEFAULT_UNIT);
    }

    public TemperatureData(String scalar, Unit unit)
    {
        super(scalar, unit);

        setValidUnits(FIELD_UNITS);
    }

    public TemperatureData(String field)
    {
        super(field);

        setValidUnits(FIELD_UNITS);
    }
}
