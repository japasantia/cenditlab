package ve.gob.cendit.cenditlab.data;

/**
 * Created by root on 03/09/17.
 */
public class BooleanData extends Data
{
    public BooleanData(String name)
    {
        super(name);
    }

    public BooleanData(String name, Object value)
    {
        super(name, value);
    }

    public BooleanData(String name, Data data)
    {
        super(name, data);
    }

    public BooleanData(Data data)
    {
        super(data);
    }

    @Override
    public void set(Object value)
    {
        super.set(parseBoolean(value));
    }

    @Override
    public void set(Data data)
    {
        this.set(data.get());
    }
    /*
    private static final String BOOLEAN_FORMAT =
        "^\\s*(1|0|TRUE|FALSE|ON|OFF)\\s*$";
    */
    private static final String BOOLEAN_FORMAT =
        "^\\s*(1|0|[Tt][Rr][Uu][Ee]|[Ff][Aa][Ll][Ss][Ee]|[Oo][Nn]|[Oo][Ff][Ff])\\s*$";

    private static final String BOOLEAN_TRUE_FORMAT =
        "^\\s*(1|[Tt][Rr][Uu][Ee]|[Oo][Nn])\\s*$";

    private static final String BOOLEAN_FALSE_FORMAT =
        "^\\s*(0|[Ff][Aa][Ll][Ss][Ee]|[Oo][Ff][Ff])\\s*$";

    public static boolean isValid(Object value)
    {
        if (value == null)
        {
            return false;
        }

        return value instanceof Boolean ||
                value instanceof Number ||
                value.toString().matches(BOOLEAN_FORMAT);
    }

    private static Boolean parseBoolean(Object value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("Value must not be null");
        }

        if (value instanceof Boolean)
        {
            return (Boolean)value;
        }
        else if (value instanceof Number)
        {
            return ((Number) value).intValue() != 0;
        }
        else
        {
            String stringValue = value.toString();

            if (stringValue.matches(BOOLEAN_TRUE_FORMAT))
            {
                return Boolean.TRUE;
            }

            if (stringValue.matches(BOOLEAN_FALSE_FORMAT))
            {
                return Boolean.FALSE;
            }
        }

        throw new IllegalArgumentException("Value does not contain a valid number representation");
    }
}
