package ve.gob.cendit.cenditlab.tests;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ve.gob.cendit.cenditlab.data.GraphData;
import ve.gob.cendit.cenditlab.data.GraphPoint;

import java.util.Arrays;
import java.util.List;

public class GraphDataTest extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        String stringPoints =  "1.5, 12.3; 3.14, 4.5; 5.5, 16.78; 2.78, 17";

        Float[] floatPoints = new Float[] { 1.2f, 3.4f, 5.6f, 6.7f, 8.9f };

        Integer[] intPointsX = new Integer[] { 3, 4, 5, 6 };
        Integer[] intPointsY = new Integer[] { 1, 2, 3 };

        Object objArray[][] = new Float[][]
                {
                        { 3.2f, 4.5f, 3.14f, 12.34f }, { 6.3f, 5.2f, 10.14f, 200.15f, -2.78f},
                        { 17.79f, -2.78f, 0.0f, 6.28e2f, 15.0f }, { 5.2f, -4.5f, 69.13f, 3.1616f },
                        { 7.49f, 71.9f, 0.68f, 56.78f, 1.0f }, { 12.52f, 4.5f, 2.2f, 3.1616f },
                        { -6.28f, 47.49f, 1.9f, 6.08f, 6.78f, 1.5f }, { -2.52f, -45.5f, 12.12f, 3.1616f }                };

        GraphData graphData1 = GraphData.fromString("Graph 1", stringPoints);

        GraphData graphData2 = GraphData.fromArray("Graph 2", floatPoints);

        GraphData graphData3 = GraphData.fromArray("Graph 3",
                 intPointsX, intPointsY, -1);

        GraphData graphData4 = GraphData.fromArrayColumns("Graph 4", objArray, 3, 1, -1);

        GraphData graphData5 = GraphData.fromArrayRows("Graph 5", objArray, 2, 1,
                1, 2, 3);

        printGraphData(graphData1, graphData2, graphData3, graphData4, graphData5);

        showGraphs(primaryStage, graphData1, graphData2, graphData3, graphData4, graphData5);
    }

    public void printGraphData(GraphData... graphDataArgs)
    {
        Arrays.stream(graphDataArgs)
                .forEach(graphData -> {
                    System.out.printf("%s: %s\n", graphData.getName(), graphData.toString());
                });
    }

    public void showGraphs(Stage stage, GraphData... graphDataArgs)
    {
        ScrollPane scrollPane = new ScrollPane();
        VBox container = new VBox();


        for (GraphData graphData : graphDataArgs)
        {
            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            LineChart<Number, Number> chart =
                    new LineChart<Number, Number>(xAxis, yAxis);

            xAxis.setAutoRanging(true);
            yAxis.setAutoRanging(true);

            XYChart.Series<Number, Number> series = new XYChart.Series<>();

            series.setName(graphData.getName());

            for (GraphPoint graphPoint : (List<GraphPoint>)graphData.get())
            {
                series.getData().add(new XYChart.Data<>(graphPoint.getX(), graphPoint.getY()));
            }

            chart.getData().add(series);

            container.getChildren().add(chart);
        }


        scrollPane.setContent(container);
        Scene root = new Scene(scrollPane);

        stage.setScene(root);
        stage.setTitle("GraphData Test");
        stage.setWidth(1200.0);
        stage.setHeight(800.0);
        stage.show();
    }
}
