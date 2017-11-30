import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import ve.gob.cendit.cenditlab.data.*;
import ve.gob.cendit.cenditlab.ui.*;

import java.util.Timer;
import java.util.TimerTask;

public class ViewTests extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;

        // setupViewTest();
        // enrSetupViewProto1Test();
        // frequencySetupViewTest();
        // setupContainerViewTest();
        // basicFrequencySetupTest();
        // genericMainViewTest();
        // componentViewTests();
        // graphViewTest();
        graphViewTest2();
    }

    public void setupViewTest()
    {
        VBox root = new VBox();
        Button loadSetupButton = new Button("load Setup");
        SetupView setupView = new SetupView();

        root.getChildren().addAll(setupView, loadSetupButton);

        loadSetupButton.setOnAction(event ->
            System.out.println(setupView.getSetup()));

        showView(root, "CenditLab | SetupView Test",
                600, 400);
    }

    private void frequencySetupViewTest()
    {
        FrequencySetupViewProto1 frequencySetupView =
                new FrequencySetupViewProto1();

        showView(frequencySetupView, "CenditLab.Reduced | Test EnrSetupView Test",
                800, 400);
    }

    private void enrSetupViewProto1Test()
    {
        VBox root = new VBox();
        EnrSetupViewProto1 enrSetupView = new EnrSetupViewProto1();
        TextArea textArea = new TextArea();
        Button button = new Button("Show EnrSetup");

        enrSetupView.enableCommonEnrTableProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    textArea.appendText(String.format("Common ENR table %s\n",
                            (newValue ? "enabled" : "disabled")));
                });

        enrSetupView.enableEnrTableModeProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    textArea.appendText(String.format("ENR table mode %s\n",
                            (newValue ? "enabled" : "disabled")));
                });

        button.setOnAction(event ->
            {
                textArea.appendText(enrSetupView.getSetup().toString());
                textArea.appendText("\n");
            });

        root.getChildren().addAll(enrSetupView, textArea, button);

        showView(root, "CenditLab.Reduced | Test EnrSetupView Test",
                600, 400);
    }

    private void setupContainerViewTest()
    {
        SetupContainerView setupContainerView = new SetupContainerView();

        Field field = new Field();
        NumericField numericField = new NumericField();
        EnrField enrField = new EnrField();
        FrequencyField frequencyField = new FrequencyField();

        Options options = new Options("Opciones",
                "Opcion 1", "Opcion 2", "Opcion 3");

        FrequencyListPane frequencyListPane = new FrequencyListPane();

        setupContainerView.addField("Field", field);
        setupContainerView.addField("Numeric field", numericField);
        setupContainerView.addField("ENR field", enrField);
        setupContainerView.addField("Frequency", frequencyField);

        setupContainerView.addOptions("Options", options);
        setupContainerView.addFrequencyListPane(
                "Frequency list", frequencyListPane);

        setupContainerView.addBasicFrequencySetupView(
                "Basic frequency setup", new BasicFrequencySetupView());

        setupContainerView.addEnrSetupView("Enr Setup", new EnrSetupViewProto1());

        setupContainerView.addSetup("Frequency Range Pane", new FrequencyRangePane());

        setupContainerView.addSetup("Frequency List Pane",  new FrequencyListPane());

        setupContainerView.addSetup("Connection Setup", new ConnectionSetupView());

        showView(setupContainerView, "CenditLab.Reduced | Test SetupContainerView",
                600, 400);
    }

    private void basicFrequencySetupTest()
    {
        BasicFrequencySetupView basicFrequencySetupView = new BasicFrequencySetupView();

        showView(basicFrequencySetupView, "CenditLab.Reduced | Test SetupContainerView",
                600, 400);
    }

    private void genericMainViewTest()
    {
        SectionedView mainView = new SectionedView();

        BasicFrequencySetupView basicFrequencySetupView = new BasicFrequencySetupView();
        FrequencyListPane frequencyListPane = new FrequencyListPane();
        SetupView setupView = new SetupView();

        mainView.createCenterSection("Master", setupView);
        mainView.createCenterSection("Detail", basicFrequencySetupView);
        mainView.createCenterSection("Status", frequencyListPane);

        Node node = mainView.getCenterSection("Master");

        node = mainView.removeCenterSection("Master");
        node = mainView.removeCenterSection("Master");
        node = mainView.getCenterSection("Master");

        mainView.createCenterSection("Master", setupView);

        showView(mainView, "CenditLab.Reduced | Test SectionedView Test",
                800, 600);
    }

    private static void componentViewTests()
    {
        VBox containerVBox = new VBox();

        Image iconImage =
                new Image(ViewTests.class.getResource("/ve/gob/cendit/cenditlab/ui/images/multimeter.png" ).toExternalForm());
        HeaderView headerView =
                new HeaderView("Header View", iconImage);
        containerVBox.getChildren().add(headerView);

        ScrollPane scrollPane = new ScrollPane(containerVBox);

        for (int i = 0; i < 10; i++)
        {
            ComponentIconView componentIconView = new ComponentIconView();
            componentIconView.setName(String.format("Component %d", i));
            componentIconView.setDescription(String.format("This is the component %d", i));
            componentIconView.setSelected(i % 2 == 0);
            containerVBox.getChildren().addAll(componentIconView);
        }


        showView(scrollPane, "CenditLab.Reduced | Component Views Tests",
                800, 600);
    }

    private void graphViewTest()
    {
        GraphView graphView = new GraphView();
        GraphData graphData = new GraphData("Data 1");

        int size = 100;
        double[] pointsX = new double[size];
        double[] pointsY = new double[size];

        for (int i = 0; i < size; i++)
        {
            pointsX[i] = 10.0 * Math.random();
            pointsY[i] = 10.0 * Math.random();
        }

        graphData.addPoints(pointsX, pointsY);
        graphView.setTitle("Random points graph");
        graphView.addGraph(graphData);

        showView(graphView, "CenditLab.Reduced | Component Views Tests",
                600.0, 400.0);
    }

    private void graphViewTest2()
    {
        GraphView graphView = new GraphView();

        int size = 100;

        GraphData[] graphsData = new GraphData[]
            {
                new GraphData("Data 1", size),
                new GraphData("Data 2", size),
                new GraphData("Data 3", size),
                new GraphData("Data 4", size)
            };

        graphView.addGraphs(graphsData);

        TimerTask timerTask = new TimerTask()
        {
            @Override
           public void run()
           {
               for (int j = 0; j < graphsData.length; ++j)
               {
                   double phase = 2 * Math.PI * Math.random();
                   double deltaX = 2 * Math.PI / size;
                   double x = 0.0;
                   double y = 0.0;

                   for (int i = 0; i < size; i++)
                   {
                       x += deltaX;
                       y = Math.sin(x + phase);

                       graphsData[j].addPoint(x, y);
                   }
               }
           }
        };

        Timer timer = new Timer();

        timer.schedule(timerTask, 100, 500);

        showView(graphView, "CenditLab.Reduced | Component Views Tests",
                600.0, 400.0);

        stage.setOnCloseRequest(event -> timer.cancel());
    }

    private static void showView(Parent root, String title, double width, double height)
    {
        stage.setScene(new Scene(root, width, height));
        stage.setTitle(title);
        stage.show();
    }


}
