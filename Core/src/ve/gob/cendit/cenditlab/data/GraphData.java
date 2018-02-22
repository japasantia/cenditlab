package ve.gob.cendit.cenditlab.data;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphData extends Data
{
    private static final String GRAPH_DATA_REGEX =
        "\\[(?<xData>[^\\[\\]]+)\\]\\s*\\[(?<yData>[^\\[\\]]+)\\]";

    private static final String GRAPH_DATA_FORMAT = "[%s][%s]";

    ObservableList<XYChart.Data<Number, Number>> observableList;

    ListData xListData;
    ListData yListData;

    public GraphData(String name)
    {
        super(name);
    }

    public GraphData(String name, ListData xListData, ListData yListData)
    {
        this(name);

        setData(xListData, yListData);
    }

    public GraphData(String name, String xData, String yData)
    {
        // TODO: revisar este constructor (null checking)
        this(name);

        setData(new ListData(xData), new ListData(yData));
    }

    public GraphData(String name, String listString)
    {
        this(name);

        parseFromString(listString);
    }

    @Override
    public void setValue(String value)
    {
        // TODO: considerar aplicar lazy evaluations
        parseFromString(value);

        observableList = null;

        super.setValue(getAsString());
    }

    /*
    @Override
    public String getValue()
    {

    }
    */

    public void setData(ListData xListData, ListData yListData)
    {
        Objects.requireNonNull(xListData);
        Objects.requireNonNull(yListData);

        this.xListData = xListData;
        this.yListData = yListData;

        observableList = null;

        super.setValue(getAsString());
    }

    public int size()
    {
        return Math.min(xListData.size(), yListData.size());
    }

    public ObservableList<XYChart.Data<Number, Number>> getPointsObservableList()
    {
        observableList = FXCollections.observableArrayList();

        int size = size();
        for (int i = 0; i < size; ++i)
        {
            observableList.add(new XYChart.Data(xListData.getAsFloat(i), yListData.getAsFloat(i)));
        }

        return observableList;
    }

    public String getAsString()
    {
        return String.format(GRAPH_DATA_FORMAT, xListData.getValue(), yListData.getValue());
    }

    private void parseFromString(String listString)
    {
        // TODO: considerar lanzar excepcion en caso de formato invalido
        Objects.requireNonNull(listString);

        Pattern pattern = Pattern.compile(GRAPH_DATA_REGEX);

        Matcher matcher = pattern.matcher(listString);

        if (matcher.find())
        {
            String data = matcher.group("xData");
            ListData xData = new ListData("X data", data);

            data = matcher.group("yData");
            ListData yData = new ListData("Y data", data);

            setData(xData, yData);
        }
    }

    private void flushData()
    {
        Platform.runLater(() ->
            {
                try
                {
                    synchronized (this)
                    {

                    }
                }
                catch (Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            });
    }

    @Override
    public String toString()
    {
        return getValue();
    }
}
