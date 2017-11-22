package ve.gob.cendit.cenditlab.data;

public class NumberData extends Data
{
    public NumberData(String name)
    {
        super(name);
    }

    public NumberData(String name, Object value)
    {
        super(name, value);
    }

    public NumberData(String name, Data data)
    {
        super(name, data);
    }

    public NumberData(Data data)
    {
        super(data);
    }

    @Override
    public void set(Object value)
    {
        super.set(parseNumber(value));
    }

    @Override
    public void set(Data data)
    {
        this.set(data.get());
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    /* Representacion numerica NR1 */
    private static final String INTEGER_PATTERN =
            "^\\s*[+-]?\\d+$";

    /* Representacion numerica NR2 */
    private static final String FLOAT_PATTERN =
        "^\\s*[+-]?\\d+(\\.\\d*)?([eE][+-]?(\\d+))?\\s*$";

    /* representaciones NR3 */
    /* Representacion Binaria */
    private static final String BINARY_PATTERN =
        "^\\s*#[bB][\\dabcdefABCDEF]+\\s*$";

    /* Representacion Octal */
    private static final String OCTAL_PATTERN =
            "^\\s*#[oO][\\dabcdefABCDEF]+\\s*$";

    /* Representacion Hexadecimal */
    private static final String HEX_PATTERN =
            "^\\s*#[hH][\\dabcdefABCDEF]+\\s*$";


    private static final String[] NUMERIC_PATTERNS = new String[]
    {
        INTEGER_PATTERN,
        FLOAT_PATTERN,
        BINARY_PATTERN,
        OCTAL_PATTERN,
        HEX_PATTERN
    };


    public static boolean isValid(Object value)
    {
        return (value!= null &&
                (value instanceof  Number ||
                    (value instanceof String && isValid(value.toString()))));
        /*
        if (value == null || ! (value instanceof Number || value instanceof String))
        {
            return false;
        }

        return isValid(value.toString());
        */
    }

    public static boolean isValid(String value)
    {
        for (String pattern : NUMERIC_PATTERNS)
        {
            if ( value.matches(pattern) )
            {
                return true;
            }
        }

        return false;
    }

    public static boolean isInteger(String value)
    {
        return value != null && value.matches(INTEGER_PATTERN);
    }

    public static boolean isFloat(String value)
    {
        return value != null && value.matches(FLOAT_PATTERN);
    }

    public static boolean isHex(String value)
    {
        return value != null && value.matches(HEX_PATTERN);
    }

    public static boolean isBinary(String value)
    {
        return value != null && value.matches(BINARY_PATTERN);
    }

    public static boolean isOctal(String value)
    {
        return value != null && value.matches(OCTAL_PATTERN);
    }

    private static Number parseNumber(Object value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("Value must not be null");
        }

        if (value instanceof Number)
        {
            return (Number)value;
        }
        else if (value instanceof Object)
        {
            try
            {
                String numberString = value.toString();

                if (isFloat(numberString))
                {
                    return Float.parseFloat(numberString);
                }
                else if (isInteger(numberString))
                {
                    return Integer.parseInt(numberString);
                }
                else
                {
                    int radix = 10;

                    if (isBinary(numberString))
                    {
                        radix = 2;
                    }
                    else if (isOctal(numberString))
                    {
                        radix = 8;
                    }
                    else if (isHex(numberString))
                    {
                        radix = 16;
                    }
                    else
                    {
                        throw new IllegalArgumentException("Value does not contain a valid number representation");
                    }

                    return Integer.parseInt(numberString.substring(2), radix);
                }
            }
            catch (NumberFormatException ex)
            {
                throw new IllegalArgumentException("Value does not contain a valid number representation", ex);
            }
        }

        throw new IllegalArgumentException("Value does not contain a valid number representation");
    }
}
