package ve.gob.cendit.cenditlab.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericData extends ValueData
{
    protected static final Unit DEFAULT_UNIT =
            new Unit("", 1.0f);

    protected static final String DEFAULT_VALUE = "0.0";

    private static final String NUMERIC_FIELD_REGEX =
            "^\\s*(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)(\\s*(?<unit>\\w+)?)\\s*$";

    private static final String SCALAR_PART_REGEX =
            "(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)";

    private static final String UNIT_PART_REGEX =
            "([+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)\\s*(?<unit>\\w+)?";

    private static final Pattern numericFieldPattern =
            Pattern.compile(NUMERIC_FIELD_REGEX);

    private static final Pattern numericScalarPattern =
            Pattern.compile(SCALAR_PART_REGEX);

    private static final Pattern numericUnitRegex =
            Pattern.compile(UNIT_PART_REGEX);

    private float magnitude;

    public NumericData()
    {
        super(DEFAULT_VALUE,  DEFAULT_UNIT);
    }

    public NumericData(String scalar, Unit unit)
    {
        float mag = Float.parseFloat(scalar);
        setMagnitude(magnitude * unit.getMultiplier());
    }

    public NumericData(String value)
    {
        setValue(value);
    }

    public void setMagnitude(float magnitude)
    {
        this.magnitude = magnitude;

        Unit unit = getValidUnits().getUnitForMagnitude(magnitude);
        float mag = NumericData.normalizeMagnitude(magnitude);

        super.setValue(String.valueOf(mag));
        super.setUnit(unit);
    }

    public float getMagnitude()
    {
        return magnitude;
    }

    @Override
    public void setValue(String value)
    {
        // TODO: check for null value
        String scalarPart = NumericData.extractScalar(value);

        if (scalarPart == null)
        {
            throw new IllegalArgumentException("Bad format numeric data");
        }

        String unitPart = NumericData.extractUnit(value);

        Unit unit = getValidUnits().get(unitPart);

        float scalar = Float.parseFloat(scalarPart);

        setMagnitude(scalar * unit.getMultiplier());
    }

    @Override
    public void setUnit(Unit unit)
    {
        // TODO: check for unit null

        float value = Float.parseFloat(getValue());
        float multiplier = unit.getMultiplier();
        this.magnitude = value * multiplier;

        super.setUnit(unit);
    }

    @Override
    public String toString()
    {
        return String.format("%.2f %s",
                getMagnitude(),
                getUnit().getName());
    }

    public static boolean isValid(String value)
    {
        return value.matches(NUMERIC_FIELD_REGEX);
    }

    public static boolean hasScalar(String value)
    {
        return value != null && value.matches(SCALAR_PART_REGEX);
    }

    public static boolean hasUnit(String value)
    {
        return value != null && value.matches(UNIT_PART_REGEX);
    }

    // TODO: revisar eliminacion
    /*
    public static float parseFloat(String value)
    {
        float magnitude = 0.0f;

        if (hasScalar(value))
        {
            String scalar = NumericField.extractScalar(value);
            magnitude = Float.parseFloat(scalar);
        }

        if (hasUnit(value))
        {
            String unitName = NumericField.extractUnit(value);
            Unit unit = dataUnits.get(unitName);
            multiplier =  unit.getMultiplier();
        }

        return magnitude * multiplier;


        return magnitude;
    }
    */

    /*
    public static NumericField parse(String value, DataUnits fieldUnits)
    {
        Matcher matcher = numericFieldPattern.matcher(value);

        if (matcher.find())
        {
            String scalar = matcher.group("scalar");
            String unitName = matcher.group("unit");
            Unit unit = fieldUnits.get(unitName);

            return new NumericField(scalar, unit);
        }

        throw new IllegalArgumentException("Invalid frequency value format");
    }
    */

    public static String extractScalar(String field)
    {
        return extractPart(field, "scalar", numericScalarPattern);
    }

    public static String extractUnit(String field)
    {
        return extractPart(field, "unit", numericUnitRegex);
    }

    private static String extractPart(String field, String partName, Pattern pattern)
    {
        Matcher matcher = pattern.matcher(field);

        if (matcher.find())
        {
            return matcher.group(partName);
        }

        return null;
    }

    private static float normalizeMagnitude(float magnitude)
    {
        float abs = Math.abs(magnitude);

        if (abs >= 1.0e9f)
        {
            return magnitude / 1.0e9f;
        }
        else if (abs >= 1.0e6)
        {
            return magnitude / 1.0e6f;
        }
        else if (abs >= 1.0e3)
        {
            return magnitude / 1.0e3f;
        }

        return magnitude;
    }
}
