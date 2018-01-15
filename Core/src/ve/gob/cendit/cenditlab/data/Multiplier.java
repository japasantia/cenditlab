package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Multiplier
{
    PICO("p", 1.0e-12F),
    NANO("n", 1.0e-9F),
    MICRO("u", 1.0e-6F),
    MILLI("m", 1.0e-3F),
    UNIT("", 1),
    KILO("k", 1.0e3F),
    MEGA("MEG", 1.0e6F),
    GIGA("G", 1.0e9F),
    TERA("T", 1.0e12F);

    private String name;
    private float multiplier;

    private static final String MULTIPLIER_PART_REGEX =
            "([+-]?\\d+(\\.\\d*)?([eE][+-]?\\d+)?)\\s*(?<multiplier>p|n|u|m|k|MEG|G|T)?\\s*(Hz|dB|K|C|°C|F)?";

    private static final Pattern pattern = Pattern.compile(MULTIPLIER_PART_REGEX);

    Multiplier(String name, float multiplier)
    {
        this.name = name;
        this.multiplier = multiplier;
    }

    public String getName()
    {
        return name;
    }

    public float getMultiplier()
    {
        return multiplier;
    }

    public String toString()
    {
        return  name;
    }

    public static Multiplier getMagnitudeMultiplier(float magnitude)
    {
        Multiplier[] multipliers = Multiplier.values();

        Multiplier multiplier = Multiplier.UNIT;

        magnitude = Math.abs(magnitude);

        for (Multiplier m : multipliers)
        {
            if (magnitude >= m.multiplier)
                multiplier = m;
        }

        return multiplier;
    }

    public static Multiplier getMultiplierFromName(String name)
    {
        return Arrays.stream(Multiplier.values())
            .filter(m -> m.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    public static String extractMultiplierName(String data)
    {
        Matcher matcher = pattern.matcher(data);

        if (matcher.find())
        {
            return matcher.group("multiplier");
        }

        return null;
    }

    public static Multiplier extractMultiplier(String data)
    {
        return getMultiplierFromName(extractMultiplierName(data));
    }
}
