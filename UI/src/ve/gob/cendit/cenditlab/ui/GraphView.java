package ve.gob.cendit.cenditlab.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ve.gob.cendit.cenditlab.data.GraphData;
import ve.gob.cendit.cenditlab.data.ListData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GraphView extends VBox
{
    private static final String FXML_URL = "fxml/graph-view.fxml";
    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private LineChart lineChart;

    private List<GraphData> graphDataList;

    public GraphView(String title)
    {
        viewLoader.load(this, this);

        initialize();

        setTitle(title);
    }

    public GraphView(String title, GraphData graphData)
    {
        this(title);

        setGraphData(graphData);
    }

    private void initialize()
    {
        Axis<Number> xAxis = lineChart.getXAxis();
        xAxis.setAnimated(false);
        xAxis.setAutoRanging(true);

        Axis<Number> yAxis = lineChart.getYAxis();
        yAxis.setAnimated(false);
        yAxis.setAutoRanging(true);

        graphDataList = new ArrayList<>();

        lineChart.setData(FXCollections.<Series>observableArrayList());
    }

    public void setGraphData(GraphData... graphDataArgs)
    {
        clear();

        addGraphData(graphDataArgs);
    }

    public void addGraphData(GraphData... graphDataArgs)
    {
        Objects.requireNonNull(graphDataArgs);

        Arrays.stream(graphDataArgs)
            .forEach(this::addGraphData);
    }

    public void addGraphData(GraphData graphData)
    {
        Objects.requireNonNull(graphData);

        Series series = new Series(graphData.getName(), graphData.getPointsObservableList());

        lineChart.getData().add(series);
    }

    public void clear()
    {
        graphDataList.clear();
        lineChart.getData().clear();
    }

    public void setTitle(String value)
    {
        lineChart.setTitle(value);
    }

    public void getTitle()
    {
        lineChart.getTitle();
    }


}
