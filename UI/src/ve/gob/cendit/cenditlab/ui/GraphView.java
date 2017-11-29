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

import java.util.Arrays;

public class GraphView extends VBox
{
    private static final String FXML_URL =
            "fxml/graph-view.fxml";
    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

    @FXML
    private LineChart lineChart;

    private ObservableList<Series<Number, Number>> seriesList;

    public GraphView()
    {
        viewLoader.load(this, this);

        initialize();
    }

    private void initialize()
    {
        Axis<Number> xAxis = lineChart.getXAxis();
        xAxis.setAnimated(false);
        xAxis.setAutoRanging(true);

        Axis<Number> yAxis = lineChart.getYAxis();
        yAxis.setAnimated(false);
        yAxis.setAutoRanging(true);

        seriesList = FXCollections.observableArrayList();
        lineChart.setData(seriesList);
    }

    public void setTitle(String value)
    {
        lineChart.setTitle(value);
    }

    public void getTitle()
    {
        lineChart.getTitle();
    }

    public void addGraph(GraphData graphData)
    {
        Series<Number, Number> series = new Series<>(graphData.getPoints());
        series.setName(graphData.getName());

        seriesList.add(series);
    }

    public void addGraph(String name, double[] pointsX, double[] pointsY)
    {
        GraphData graphData = new GraphData(name);
        graphData.addPoints(pointsX, pointsY);

        addGraph(graphData);
    }

    public void addGraphs(GraphData... graphs)
    {
        if (graphs != null)
        {
            Arrays.stream(graphs)
                    .forEach(g -> addGraph(g));
        }
    }
}
