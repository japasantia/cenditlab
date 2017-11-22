package ve.gob.cendit.cenditlab.data;


import ve.gob.cendit.cenditlab.data.FieldUnits;
import ve.gob.cendit.cenditlab.data.NumericField;
import ve.gob.cendit.cenditlab.data.Unit;

public class TemperatureField extends NumericField
{
    public static final String DEFAULT_VALUE = "0.0";

    public static final Unit KELVIN =
            new Unit("K", 1.0f);
    public static final Unit CELSIUS =
            new Unit("C", 1.0f);
    public static final Unit FARENHEIT =
            new Unit("F", 1.0f);

    public static final Unit DEFAULT_UNIT = KELVIN;

    public static final FieldUnits FIELD_UNITS =
            new FieldUnits(KELVIN, CELSIUS, FARENHEIT);

    public TemperatureField()
    {
        super(DEFAULT_VALUE, DEFAULT_UNIT);

        setValidUnits(FIELD_UNITS);
    }

    public TemperatureField(String scalar, Unit unit)
    {
        super(scalar, unit);

        setValidUnits(FIELD_UNITS);
    }

    public TemperatureField(String field)
    {
        super(field);

        setValidUnits(FIELD_UNITS);
    }
}
