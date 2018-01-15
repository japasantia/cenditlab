package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Unit
{
    HZ("Hz"),
    DB("dB"),
    KELVIN("K"),
    CELSIUS("C"),
    FARENHEIT("F"),
    NONE("");

    private String name;

    private static final String UNIT_PART_REGEX =
            "([+-]?\\d+(.\\d*)?([eE][+-]?\\d+)?)\\s*(p|n|u|m|k|MEG|G|T)?\\s*(?<unit>Hz|dB|K|C|°C|F)?";

    private static final Pattern pattern = Pattern.compile(UNIT_PART_REGEX);

    private Unit(String name)
    {
        Objects.requireNonNull(name,"name must not be null");

        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public static Unit getUnitFromName(String name)
    {
        return Arrays.stream(Unit.values())
            .filter(u -> u.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    public static boolean hasUnit(String data)
    {
        return data != null && data.matches(UNIT_PART_REGEX);
    }

    public static Unit extractUnit(String data)
    {
        return getUnitFromName(extractUnitName(data));
    }

    public static String extractUnitName(String data)
    {
        Matcher matcher = pattern.matcher(data);

        if (matcher.find())
        {
            return matcher.group("unit");
        }

        return null;
    }
}
