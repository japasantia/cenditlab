package ve.gob.cendit.cenditlab.data;

import java.util.regex.Pattern;

public class EnrData extends NumericData
{
    public static final String DEFAULT_VALUE = "0.0";

    public static final Unit DEFAULT_UNIT = Unit.DB;

    public static final Unit VALID_UNITS[] = { Unit.DB, Unit.KELVIN, Unit.CELSIUS, Unit.FARENHEIT, Unit.NONE};

    private static final String ENR_FIELD_REGEX =
            "^\\s*(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)(\\s*(?<unit>dB|DB|db|K|C|F))?\\s*$";

    private static final String ENR_SCALAR_REGEX =
            "(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)";

    private static final String ENR_UNIT_REGEX =
            "(?<unit>dB|DB|db|K|C|F)";

    private static final Pattern frequencyFieldPattern =
            Pattern.compile(ENR_FIELD_REGEX);

    private static final Pattern frequencyScalarPattern =
            Pattern.compile(ENR_SCALAR_REGEX);

    private static final Pattern frequencyUnitRegex =
            Pattern.compile(ENR_UNIT_REGEX);

    public EnrData()
    {
        this(DEFAULT_VALUE, DEFAULT_UNIT);
    }

    public EnrData(String scalar, Unit unit)
    {
        super(scalar, unit);

        setValidUnits(VALID_UNITS);
    }

    public EnrData(String value)
    {
        super(value);

        setValidUnits(VALID_UNITS);
    }
}
