package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;

/**
 * Created by jarias on 01/09/17.
 */
public class ListData extends Data
{
    public ListData(String name)
    {
        super(name);
    }

    public ListData(String name, Object value)
    {
        super(name, value);
    }

    public ListData(String name, Data data)
    {
        super(name, data);
    }

    public ListData(Data data)
    {
        super(data);
    }

    @Override
    public void set(Object value)
    {
       super.set(parseList(value));
    }

    @Override
    public void set(Data data)
    {
        this.set(data.get());
    }

    @Override
    public String toString()
    {
        Object data = get();

        if (data instanceof Object[])
        {
            StringBuilder sb = new StringBuilder();
            Arrays.stream(((Object[]) data))
                .forEach(item ->
                    {
                        sb.append(item);
                        sb.append(LIST_SEPARATOR);
                    });

            int length = sb.length();
            if (length > 1)
            {
                return sb.substring(0, length - 1);
            }
            return sb.toString();
        }
        else
        {
            return super.get().toString();
        }
    }

    private static final String LIST_FORMAT = "^([^,]+)(,[^,]+)*$";
    private static final String LIST_SEPARATOR = ",";

    public static boolean isValid(Object value)
    {
        if (value == null)
        {
            return false;
        }

        return value instanceof String[] ||
            value instanceof Object[] ||
            value.toString().matches(LIST_FORMAT);
    }

    private static Object[] parseList(Object value)
    {
        String rawList;

        if (value == null)
        {
            throw new IllegalArgumentException("Value must not be null");
        }

        if (value instanceof String[])
        {
            return (Object[])value;
        }
        else if (value instanceof Object[])
        {
            return (Object[])value;
        }
        else if (value instanceof String)
        {
            rawList = (String)value;
        }
        else
        {
            rawList = value.toString();
        }

        if ( ! value.toString().matches(LIST_FORMAT) )
        {
            throw new IllegalArgumentException("Value does not contain a valid list representation");
        }

        String[] items = rawList.split(LIST_SEPARATOR);

        for (int i = 0; i < items.length; i++)
        {
            items[i] = items[i].trim();
        }

        return items;
    }


}
