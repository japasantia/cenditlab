package ve.gob.cendit.cenditlab.data;

import java.util.Arrays;

/**
 * Created by root on 04/09/17.
 */
public class ArrayData extends Data
{
    private int arrayRows;
    private int arrayColumns;

    public ArrayData(String name)
    {
        super(name);
    }

    public ArrayData(String name, Object value)
    {
        super(name, value);
    }

    public ArrayData(String name, Data data)
    {
        super(name, data);
    }

    public ArrayData(Data data)
    {
        super(data);
    }

    @Override
    public void set(Object value)
    {
        super.set(parseArray(value));

        loadArrayDimensions();
    }

    @Override
    public void set(Data data)
    {
        this.set(data.get());
    }

    public void setItem(int row, int column, Object item)
    {
        if (itemExists(row, column))
        {
            Object[][] objArray = getInternalArray();
            Object[] objRow = (Object[])objArray[row];

            if (objRow == null)
            {
                objRow = createRow(row);
            }
            else if (column >= objRow.length)
            {
                objRow = expandRow(row);
            }

            objRow[column] = item;
        }
    }

    public Object getItem(int row, int column)
    {
        Object[][] objArray = getInternalArray();

        if (objArray != null && checkIndices(row, column))
        {
            Object[] objRow = (Object[]) objArray[row];

            if (objRow != null && column < objRow.length)
            {
                return objRow[column];
            }
        }

        return null;

        /*
        if (row >= arrayRows || column >= arrayColumns)
        {
            return null;
        }

        Object[][] objArray = getInternalArray();

        if (objArray == null)
        {
            return null;
        }

        Object[] objRow = (Object[]) objArray[row];

        if (column >= objArray.length)
        {
            return null;
        }

        return objRow[column];
        */
    }

    public Object[] getRow(int row)
    {
        Object[][] objArray = getInternalArray();

        if (objArray != null && row < arrayRows)
        {
            return objArray[row];
        }

        return null;
    }

    public int getRows()
    {
        return arrayRows;
    }

    public int getColumns()
    {
        return arrayColumns;
    }

    public boolean itemExists(int row, int column)
    {
        Object[][] objArray = getInternalArray();

        return objArray != null && checkIndices(row, column) &&
                objArray[row] != null && column < objArray[row].length;
    }

    public boolean checkIndices(int row, int column)
    {
        return row >= 0 && column >= 0 &&
                row < arrayRows && column < arrayColumns;
    }

    private Object[] createRow(int row)
    {
        Object[][] objArray = getInternalArray();

        objArray[row] = new Object[arrayColumns];

        return objArray[row];
    }

    private Object[] expandRow(int row)
    {
        Object[][] objArray = getInternalArray();

        objArray[row] = Arrays.copyOf(objArray[row], arrayColumns);

        return objArray[row];
    }

    public static boolean isValid(Object value)
    {
        return (value != null && value instanceof Object[][]);
    }

    private static Object[][] parseArray(Object value)
    {
        if ( ! isValid(value) )
        {
            throw new IllegalArgumentException("Value does not contain a valid array representation");
        }

        Object[][] inputArray = (Object[][]) value;
        Object[][] outputArray = new Object[inputArray.length][];

        for (int i = 0; i < inputArray.length; ++i)
        {
            Object[] row = inputArray[i];
            
            if (row != null)
            {
                outputArray[i] = new Object[row.length];

                for (int j = 0; j <row.length; ++j)
                {
                    outputArray[i][j] = row[j];
                }
            }
        }

        return outputArray;
    }

    private Object[][] getInternalArray()
    {
        return (Object[][]) get();
    }

    private void loadArrayDimensions()
    {
        Object[][] objArray = getInternalArray();

        if (objArray != null && objArray.length > 0)
        {
            arrayRows = objArray.length;

            Arrays.stream(objArray)
                .forEach(row ->
                    {
                        if (row != null)
                        {
                            arrayColumns = (row.length > arrayColumns ? row.length : arrayColumns);
                        }
                    });
        }
        else
        {
            arrayRows = 0;
            arrayColumns = 0;
        }
    }

    @Override
    public String toString()
    {
        Object[][] objArray = getInternalArray();

        if (objArray == null)
        {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arrayRows; ++i)
        {
            Object[] objRow = objArray[i];

            if (objRow == null)
            {
                sb.append("{}");
                continue;
            }

            sb.append("{");

            for (int j = 0; j < objRow.length - 1; j++)
            {
                Object value = objRow[j];
                sb.append(String.format(" %s,",
                        value != null ? value : ""));
            }

            Object value = objRow[objRow.length - 1];
            sb.append(String.format(" %s }",
                    (value != null) ? value : ""));
        }

        return sb.toString();
    }
}
