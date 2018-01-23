package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumericData extends ValueData
{
    public static final String DEFAULT_VALUE = "0.0";

    private static final String NUMERIC_DATA_REGEX =
            "([+-]?\\d+(\\.\\d*)?([eE][+-]?\\d+)?)\\s*(p|n|u|m|k|MEG|G|T)?\\s*(Hz|dB|K|C|°C|F)?";

    private static final String SCALAR_PART_REGEX =
            "(?<scalar>[+-]?\\d+(\\.\\d*)?([eE][+-]?\\d+)?)";

    private static final Pattern numericDataPattern =
            Pattern.compile(NUMERIC_DATA_REGEX);

    private static final Pattern numericScalarPattern =
            Pattern.compile(SCALAR_PART_REGEX);

    private float magnitude;
    private boolean normalizeEnabled = false;


    public NumericData()
    {

    }

    public NumericData(String value)
    {
        setValue(value);
    }

    public NumericData(String scalar, Unit unit)
    {
        this(scalar, null, unit);
    }

    public NumericData(String scalar, Multiplier multiplier, Unit unit)
    {
        float value = Float.parseFloat(scalar);

        update(String.valueOf(value), unit, multiplier);
    }

    public void setMagnitude(float magnitude)
    {
        this.magnitude = magnitude;

        Multiplier multiplier = Multiplier.getMagnitudeMultiplier(magnitude);

        if (multiplier != null)
        {
            magnitude = magnitude / multiplier.getMultiplier();
        }

        update(String.valueOf(magnitude), getUnit(), multiplier);
    }

    public float getMagnitude()
    {
        return magnitude;
    }

    @Override
    public void setValue(String value)
    {
        String scalarPart = NumericData.extractScalar(value);

        Objects.requireNonNull(scalarPart, "Bad format for numeric data");

        Multiplier multiplier = Multiplier.extractMultiplier(value);

        Unit unit = Unit.extractUnit(value);

        float scalar = Float.parseFloat(scalarPart);

        if (multiplier != null)
        {
            magnitude = scalar * multiplier.getMultiplier();
        }

        update(String.valueOf(scalar), unit, multiplier);
    }

    private void update(String value, Unit unit, Multiplier multiplier)
    {
        setUpdateEnabled(false);

        super.setValue(value);

        if (unit != null)
        {
            setUnit(unit);
        }

        if (multiplier != null)
        {
            setMultiplier(multiplier);
        }

        setUpdateEnabled(true);

        update();
    }

    public boolean isNormalizeEnabled()
    {
        return normalizeEnabled;
    }

    public void setNormalizeEnabled(boolean value)
    {
        normalizeEnabled = value;
    }

    public void normalize()
    {
        float magnitude = getMagnitude();
        Multiplier multiplier = getMultiplier();
        multiplier = multiplier != null ? multiplier : Multiplier.UNIT;

        magnitude = magnitude * multiplier.getMultiplier();
        multiplier = Multiplier.getMagnitudeMultiplier(magnitude);

        magnitude = magnitude / multiplier.getMultiplier();

        update(String.valueOf(magnitude), getUnit(), multiplier);
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
        return value.matches(NUMERIC_DATA_REGEX);
    }

    public static boolean hasScalar(String value)
    {
        return value != null && value.matches(SCALAR_PART_REGEX);
    }

    public static boolean hasUnit(String data)
    {
        return Unit.hasUnit(data);
    }

    public static String extractScalar(String field)
    {
        Matcher matcher = numericScalarPattern.matcher(field);

        if (matcher.find())
        {
            return matcher.group("scalar");
        }

        return null;
    }
}
