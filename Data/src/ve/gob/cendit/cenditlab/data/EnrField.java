package ve.gob.cendit.cenditlab.data;

import java.util.regex.Pattern;

public class EnrField extends NumericField
{
    public static final String DEFAULT_VALUE = "0.0";

    public static final Unit DB =
            new Unit("dB", 1.0f);
    public static final Unit PER_UNIT =
            new Unit("Unit", 1.0f);

    public static final Unit DEFAULT_UNIT = DB;

    public static final FieldUnits FIELD_UNITS =
            new FieldUnits(DB, PER_UNIT);

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

    public EnrField()
    {
        super(DEFAULT_VALUE, DEFAULT_UNIT);

        setValidUnits(FIELD_UNITS);
    }

    public EnrField(String scalar, Unit unit)
    {
        super(scalar, unit);

        setValidUnits(FIELD_UNITS);
    }

    public EnrField(String field)
    {
        super(field);

        setValidUnits(FIELD_UNITS);
    }
}
