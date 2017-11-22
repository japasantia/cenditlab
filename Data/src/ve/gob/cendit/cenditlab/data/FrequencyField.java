package ve.gob.cendit.cenditlab.data;

import java.util.regex.Pattern;

public class FrequencyField extends NumericField
{
    public static final String DEFAULT_VALUE = "0.0";

    public static final Unit HZ =
            new Unit("Hz", 1.0f);
    public static final Unit KHZ =
            new Unit("kHz", 1000.0f);
    public static final Unit MHZ =
            new Unit("MHz", 1.0e6f);
    public static final Unit GHZ =
            new Unit("GHz", 1.0e9f);

    public static final Unit DEFAULT_UNIT = HZ;

    public static final FieldUnits FIELD_UNITS =
            new FieldUnits(HZ, KHZ, MHZ, GHZ);

    private static final String FREQUENCY_FIELD_REGEX =
        "^\\s*(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)(\\s*(?<unit>Hz|kHz|MHz|GHz))?\\s*$";

    private static final String FREQUENCY_SCALAR_REGEX =
            "(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)";

    private static final String FREQUENCY_UNIT_REGEX =
            "(?<unit>Hz|kHz|MHz|GHz)";

    private static final Pattern frequencyFieldPattern =
            Pattern.compile(FREQUENCY_FIELD_REGEX);

    private static final Pattern frequencyScalarPattern =
            Pattern.compile(FREQUENCY_SCALAR_REGEX);

    private static final Pattern frequencyUnitRegex =
            Pattern.compile(FREQUENCY_UNIT_REGEX);

    public FrequencyField()
    {
        super(DEFAULT_VALUE, DEFAULT_UNIT);

        setValidUnits(FIELD_UNITS);
    }

    public FrequencyField(String scalar, Unit unit)
    {
        super(scalar, unit);

        setValidUnits(FIELD_UNITS);
    }

    public FrequencyField(String field)
    {
        super(field);

        setValidUnits(FIELD_UNITS);
    }
}
