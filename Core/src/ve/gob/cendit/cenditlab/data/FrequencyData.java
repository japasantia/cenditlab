package ve.gob.cendit.cenditlab.data;

import java.util.regex.Pattern;

public class FrequencyData extends NumericData
{
    public static final String DEFAULT_VALUE = "0.0";

    public static final Unit DEFAULT_UNIT = Unit.HZ;

    public static final Unit[] VALID_UNITS = { Unit.HZ };

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

    public FrequencyData()
    {
        this(DEFAULT_VALUE, DEFAULT_UNIT);
    }

    public FrequencyData(String scalar, Unit unit)
    {
        setValidUnits(VALID_UNITS);

        setValue(scalar);
        setUnit(unit);
    }

    public FrequencyData(String field)
    {
        setValidUnits(VALID_UNITS);

        setValue(field);
    }
}
