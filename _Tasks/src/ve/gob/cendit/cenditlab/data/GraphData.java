package ve.gob.cendit.cenditlab.data;


import java.util.ArrayList;
import java.util.List;

public class GraphData extends Data
{
    // private List<GraphPoint> pointsList;

    public GraphData(String name)
    {
        super(name);
    }

    public GraphData(String name, Object value)
    {
        super(name, value);
    }

    public GraphData(String name, Data data)
    {
        super(name, data);
    }

    public GraphData(Data data)
    {
        super(data);
    }

    private GraphData(String name, List<GraphPoint> pointsList)
    {
        super(name);
        super.set(pointsList);
    }

    @Override
    public void set(Object value)
    {
        super.set(parseFromObject(value));
    }

    @Override
    public void set(Data data)
    {
        this.set(data.get());
    }

    public List<GraphPoint> getDataPoints()
    {
        return (List<GraphPoint>) get();
    }

    public int getSize()
    {
        return getDataPoints().size();
    }

    public void addPoint(GraphPoint point)
    {
        getDataPoints().add(point);
    }

    public void addPoint(Number x, Number y)
    {
        addPoint(new GraphPoint(x, y));
    }

    public static GraphData fromString(String name, String pointsList)
    {
        return new GraphData(name, parseFromString(pointsList));
    }

    public static GraphData fromArray(String name, Object[] array)
    {
        return new GraphData(name, parseFromArray(array, 0, -1));
    }

    public static GraphData fromArray(String name, Object[] array, int offset, int length)
    {
        return new GraphData(name, parseFromArray(array, offset, length));
    }

    public static GraphData fromArray(String name, Object[] arrayX, Object[] arrayY, int length)
    {
        return new GraphData(name, parseFromArray(arrayX, arrayY, 0, 0, length));
    }

    public static GraphData fromArray(String name, Object[] arrayX, Object[] arrayY,
                                      int offsetX, int offsetY, int length)
    {
        return new GraphData(name, parseFromArray(arrayX, arrayY, offsetX, offsetY, length));
    }

    public static GraphData fromArrayColumns(String name, Object[][] array, int columnX, int columnY,
                                             int length)
    {
        return new GraphData(name, parseFromArrayColumns(array, columnX, columnY,
                0, 0, length));
    }

    public static GraphData fromArrayColumns(String name, Object[][] array, int columnX, int columnY,
                                             int offsetX, int offsetY, int length)
    {
        return new GraphData(name, parseFromArrayColumns(array, columnX, columnY,
                offsetX, offsetY, length));
    }

    public static GraphData fromArrayRows(String name, Object[][] array, int rowX, int rowY,
                                          int length)
    {
        return new GraphData(name, parseFromArray(array[rowX], array[rowY],
                0, 0, length));
    }

    public static GraphData fromArrayRows(String name, Object[][] array, int rowX, int rowY,
                                             int offsetX, int offsetY, int length)
    {
        return new GraphData(name, parseFromArray(array[rowX], array[rowY],
                offsetX, offsetY, length));
    }

    private static final String POINT_SEPARATOR = ";";
    private static final String COORD_SEPARATOR = ",";
    private static final String LIST_SPLIT_PATTERN = "[" + POINT_SEPARATOR + "," + COORD_SEPARATOR + "]";

    private static final String FLOAT_NUMBER_PATTERN =
        "\\s*[+-]?\\d+(\\.\\d*)?([eE][+-]?(\\d+))?\\s*";

    private static final String POINT_PATTERN =
        FLOAT_NUMBER_PATTERN + COORD_SEPARATOR + FLOAT_NUMBER_PATTERN;

    private static final String LIST_POINT_PATTERN =
        "(" + POINT_PATTERN + ")" + "(" + POINT_SEPARATOR + POINT_PATTERN + ")+";

    private static List<GraphPoint> parseFromObject(Object value)
    {
        if (value instanceof String)
        {
           return parseFromString((String)value);
        }
        else if (value instanceof Object[][])
        {
            return parseFromArrayColumns((Object[][]) value, 0, 1,
                    0, 0, -1);
        }
        else if (value instanceof Object[])
        {
            return parseFromArray((Object[]) value, 0, -1);
        }
        else
        {
            throw new IllegalArgumentException("Value does not contain a valid graph data representation");
        }
    }

    private static List<GraphPoint> parseFromString(String value)
    {
        if (value != null && value.matches(LIST_POINT_PATTERN))
        {
            String[] pointsString = value.split(LIST_SPLIT_PATTERN);

            int length = 2 * (pointsString.length / 2);

            List<GraphPoint> pointsList = new ArrayList<>(length / 2);

            for (int i = 0; i <  length; i += 2)
            {
                Float x = Float.parseFloat(pointsString[i]);
                Float y = Float.parseFloat(pointsString[i + 1]);
                pointsList.add(new GraphPoint(x, y));
            }

            return pointsList;
        }
        else
        {
            /* TODO: Cadena no presenta formato de lista */
            throw new IllegalArgumentException("Input string is not a valid point list");
        }
        /*
        if (value != null && value.matches(LIST_POINT_PATTERN))
        {
            String[] pointsString = value.split(POINT_SEPARATOR);

            List<GraphPoint> pointsList = new ArrayList<>(pointsString.length);

            Arrays.stream(pointsString)
                    .forEach(point -> {
                        String[] coords = point.split(COORD_SEPARATOR);
                        Float x = Float.parseFloat(coords[0]);
                        Float y = Float.parseFloat(coords[1]);
                        pointsList.add(new GraphPoint(x, y));
                    });

            return pointsList;
        }
        else
        {
            //TODO: Cadena no presenta formato de lista
            throw new IllegalArgumentException("Value does not contain a valid graph data representation");
        }
        */
    }

    private static List<GraphPoint> parseFromArray(Object[] array, int offset, int length)
    {
        if (array == null || offset < 0)
        {
            throw new IllegalArgumentException("array must not be null and offset must be valid array start index");
        }

        length = (length >= 0 ? length : array.length);
        length = 2 * (length / 2);

        ArrayList pointsList = new ArrayList<>(length / 2);

        for (int i = 0; i < length; i += 2)
        {
            try
            {
                pointsList.add(new GraphPoint(array[i], array[i + 1]));
            }
            catch (Exception ex)
            {
                /* TODO: cambiar por tipo de excepcion mas apropiado */
                throw new RuntimeException("Error parsing from input array", ex);
            }
        }

        return pointsList;
    }

    private static List<GraphPoint> parseFromArray(Object[] arrayX, Object[] arrayY,
                                                   int offsetX, int offsetY, int length)
    {
        if (arrayX == null || arrayY == null)
        {
            throw new IllegalArgumentException("Input arrays must not be null");
        }

        if (offsetX < 0  || offsetY < 0)
        {
            throw new IllegalArgumentException("The offset in the array must not be negative");
        }

        length = (length >= 0 ?
                Math.min(Math.min(arrayX.length, arrayY.length), length) :
                Math.min(arrayX.length, arrayY.length));

        List<GraphPoint> pointsList = new ArrayList<>(length);

        for (int i = 0; i < length; ++i)
        {
            try
            {
                pointsList.add(new GraphPoint(arrayX[i + offsetX], arrayY[i + offsetY]));
            }
            catch (Exception ex)
            {
                /* TODO: cambiar por tipo de excepcion mas apropiado */
                throw new RuntimeException("Error parsing from input arrays", ex);
            }
        }

        return pointsList;
    }

    private static List<GraphPoint> parseFromArrayColumns(Object[][] array,
                                                   int columnX, int columnY,
                                                   int offsetX, int offsetY,
                                                   int length)
    {
        if (array == null)
        {
            throw new IllegalArgumentException("Input array must not be null");
        }

        if (columnX < 0 || columnY < 0 || offsetX < 0 || offsetY < 0)
        {
            throw new IllegalArgumentException("Column indexes and offsets must not be negative");
        }

        length = (length >= 0 ? length : array.length);
        List<GraphPoint> pointsList = new ArrayList<GraphPoint>(length);

        for (int i = 0; i < length; ++i)
        {
            Object[] rowX = array[i + offsetX];
            Object[] rowY = array[i + offsetY];

            if (rowX != null && rowY != null/* && xIndex < row.length && yIndex < row.length */)
            {
                try
                {
                    pointsList.add(new GraphPoint(rowX[columnX], rowY[columnY]));
                }
                catch (Exception ex)
                {
                        /* TODO: cambiar por tipo de excepcion mas apropiado */
                    throw new RuntimeException("Error parsing from input array", ex);
                }
            }
            else
            {
                    /* TODO: cambiar por tipo de excepcion mas apropiado */
                throw new RuntimeException("Array row must not be null");
            }
        }

        return pointsList;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        List<GraphPoint> pointsList = (List<GraphPoint>) get();

        pointsList.stream()
            .forEach(point -> {
                /* TODO: revisar cast, falla para Integer -> point.toString() */
                sb.append(String.format("%s; ", point));
            });

        if (sb.length() > 1)
        {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

}
