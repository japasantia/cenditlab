package ve.gob.cendit.cenditlab.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericField extends Field
{
    protected static final Unit DEFAULT_UNIT =
            new Unit("", 1.0f);

    protected static final String DEFAULT_VALUE = "0.0";

    private static final String NUMERIC_FIELD_REGEX =
            "^\\s*(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)(\\s*(?<unit>\\w+))?\\s*$";

    private static final String NUMERIC_SCALAR_REGEX =
            "(?<scalar>[+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)";

    private static final String NUMERIC_UNIT_REGEX =
            "([+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)(?<unit>\\w+)";

    private static final Pattern numericFieldPattern =
            Pattern.compile(NUMERIC_FIELD_REGEX);

    private static final Pattern numericScalarPattern =
            Pattern.compile(NUMERIC_SCALAR_REGEX);

    private static final Pattern numericUnitRegex =
            Pattern.compile(NUMERIC_UNIT_REGEX);

    private FieldUnits fieldUnits = FieldUnits.EMPTY_UNITS;

    private float magnitude;

    public NumericField()
    {
        super(DEFAULT_VALUE, DEFAULT_UNIT);
    }

    public NumericField(String scalar, Unit unit)
    {
        float magnitude = NumericField.parseFloat(scalar);
        setMagnitude(magnitude * unit.getMultiplier());
    }

    public NumericField(String field)
    {
        float magnitude = NumericField.parseFloat(field);
        setMagnitude(magnitude);
    }

    public void setMagnitude(float magnitude)
    {
        this.magnitude = magnitude;

        normalize();
    }

    public float getMagnitude()
    {
        return magnitude;
    }

    @Override
    public void setValue(String value)
    {
        float fValue = Float.parseFloat(value);
        this.magnitude = fValue * getUnit().getMultiplier();

        super.setValue(String.valueOf(fValue));
    }

    @Override
    public void setUnit(Unit unit)
    {
        float value = Float.parseFloat(getValue());
        float multiplier = unit.getMultiplier();
        this.magnitude = value * multiplier;

        super.setUnit(unit);
    }

    protected void setValidUnits(FieldUnits units)
    {
        if (units == null)
            throw new IllegalArgumentException("units must not be null");

        fieldUnits = units;
    }

    public FieldUnits getValidUnits()
    {
        return fieldUnits;
    }

    @Override
    public String toString()
    {
        return String.format("%.2f %s",
                getMagnitude(),
                getUnit().getName());
    }

    private void normalize()
    {
        Unit unit = fieldUnits.getUnitForMagnitude(magnitude);
        float mag = NumericField.normalizeMagnitude(magnitude);

        super.setValue(String.valueOf(mag));
        super.setUnit(unit);
    }

    public static boolean isValid(String value)
    {
        return value.matches(NUMERIC_FIELD_REGEX);
    }

    public static NumericField parse(String value, FieldUnits fieldUnits)
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

    public static boolean hasScalar(String field)
    {
        return field != null && field.matches(NUMERIC_FIELD_REGEX);
    }

    public static boolean hasUnit(String field)
    {
        return field != null && field.matches(NUMERIC_UNIT_REGEX);
    }

    public static float parseFloat(String field)
    {
        float magnitude = 0.0f;

        if (hasScalar(field))
        {
            String scalar = FrequencyField.extractScalar(field);
            magnitude = Float.parseFloat(scalar);
        }

        return magnitude;
    }

    /*
    public static float parseFloat(String field, ve.gob.cendit.cenditlab.data.FieldUnits units)
    {
        float magnitude = 0.0f;
        float multiplier = 1.0f;

        if (hasScalar(field))
        {
            String scalar = ve.gob.cendit.cenditlab.data.FrequencyField.extractScalar(field);
            magnitude = Float.parseFloat(scalar);
        }

        if (hasUnit(field))
        {
            String unitName = ve.gob.cendit.cenditlab.data.NumericField.extractUnit(field);
            ve.gob.cendit.cenditlab.data.Unit unit = units.get(unitName);
            multiplier =  unit.getMultiplier();
        }

        return magnitude * multiplier;
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
