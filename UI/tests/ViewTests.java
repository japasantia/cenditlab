import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
        frequencySetupViewTest();
        // setupContainerViewTest();
        // basicFrequencySetupTest();
        // genericMainViewTest();
        // componentViewTests();
        // graphViewTest();
        // graphViewTest2();
        // toolboxViewTest();
        // containerViewTest();
        // toolboxListViewTest();
        // overlayFrameViewTest();
        // itemFrameViewTest();
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

        EnrSetupViewProto1 enrSetupView = new EnrSetupViewProto1();

        showView(enrSetupView, "CenditLab.Reduced | Test EnrSetupView Test",
                600, 400);
    }

    private void setupContainerViewTest()
    {
        SetupContainerView setupContainerView = new SetupContainerView();

        ValueData data = new ValueData();
        NumericData numericData = new NumericData();
        EnrData enrData = new EnrData();
        FrequencyData frequencyData = new FrequencyData();

        Options options = new Options("Opciones",
                "Opcion 1", "Opcion 2", "Opcion 3");

        FrequencyListSetupView frequencyListSetupView = new FrequencyListSetupView();

        setupContainerView.addValueData("Field", data);
        setupContainerView.addValueData("Numeric field", numericData);
        setupContainerView.addValueData("ENR field", enrData);
        setupContainerView.addValueData("Frequency", frequencyData);

        setupContainerView.addOptions("Options", options);
        setupContainerView.addFrequencyListPane(
                "Frequency list", frequencyListSetupView);

        setupContainerView.addBasicFrequencySetupView(
                "Basic frequency setup", new BasicFrequencySetupView());

        setupContainerView.addEnrSetupView("Enr Setup", new EnrSetupViewProto1());

        setupContainerView.addSetup("Frequency Range Pane", new FrequencyRangeSetupView());

        setupContainerView.addSetup("Frequency List Pane",  new FrequencyListSetupView());

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
        FrequencyListSetupView frequencyListSetupView = new FrequencyListSetupView();
        SetupView setupView = new SetupView();

        mainView.createCenterSection("Master", setupView);
        mainView.createCenterSection("Detail", basicFrequencySetupView);
        mainView.createCenterSection("Status", frequencyListSetupView);

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

        showView(graphView, "CenditLab.Reduced | GraphView Test",
                600.0, 400.0);

        stage.setOnCloseRequest(event -> timer.cancel());
    }

    private static void toolboxViewTest()
    {
        ToolboxView<String> toolboxView = new ToolboxView<>();

        String[] values = new String[10];

        for (int i = 0; i < 10; i++)
        {
            values[i] = "Item " + i;
        }

        toolboxView.setItemViewFactory(text -> new IconView(text, null));
        toolboxView.setOnItemClicked(item ->
            {
                System.out.printf("%s clicked\n", item.getItem());
                System.out.println("Selected items\n");
                toolboxView.getSelectedItems().forEach(i -> System.out.printf("%s ", i.getItem()));
            });

        toolboxView.setAll(values);

        //toolboxView.setMaxWidth(200.0);
        showView(new VBox(toolboxView), "CenditLab.Reduced | ToolboxView Test", 600.0, 400.0);
    }

    private static void toolboxListViewTest()
    {
        ToolboxListView<String> toolboxListView = new ToolboxListView();

        String[] values = new String[10];

        for (int i = 0; i < 10; i++)
        {
            values[i] = "Item " + i;
        }

        toolboxListView.getItemsList()
            .setItemViewFactory(item -> new ItemView(new ComponentIconView(item.getValue(), "Item")));

        toolboxListView.getItemsList().setAll(values);

        toolboxListView.setOnItemClicked(item -> {
            System.out.printf("%s clicked\n", item.toString());

            if (item.isSelected())
                item.getView().showIcon(ItemView.SELECTED_ICON);
            else
                item.getView().hideIcon();
        });

        showView(toolboxListView, "CenditLab.Reduced | ToolboxListView Test", 600.0, 400.0);
    }

    private static void containerViewTest()
    {
        ContainerView<String> containerView = new ContainerView<>();

        String[] values = new String[10];

        for (int i = 0; i < 10; i++)
        {
            values[i] = "Item " + i;
        }

        containerView.getItemsList().setItemViewFactory(item -> new ItemView(new ComponentIconView(item.getValue(), "Container Item")));

        containerView.setOnItemClickedListener(item -> item.getView().showIcon(ItemView.SELECTED_ICON));

        containerView.getItemsList().setAll(values);

        showView(containerView, "CenditLab.Reduced | ContainerView Test", 600.0, 400.0);
    }

    private static void overlayFrameViewTest()
    {
        OverlayFrameView overlayFrameView = new OverlayFrameView();
        overlayFrameView.setPrefSize(200.0, 200.0);

        VBox rootVBox = new VBox(overlayFrameView);
        rootVBox.setFillWidth(false);

        int[] positions =
            {
                OverlayFrameView.CENTER_CENTER,
                OverlayFrameView.CENTER_LEFT,
                OverlayFrameView.CENTER_RIGHT,
                OverlayFrameView.TOP_CENTER,
                OverlayFrameView.TOP_LEFT,
                OverlayFrameView.TOP_RIGHT,
                OverlayFrameView.BOTTOM_CENTER,
                OverlayFrameView.BOTTOM_LEFT,
                OverlayFrameView.BOTTOM_RIGHT
            };

        for (int i = 0; i < positions.length ; i++)
        {
            ImageButton imageButton = new ImageButton();
            imageButton.setPrefSize(32.0, 32.0);
            imageButton.setText(null);
            imageButton.setOnMouseClicked(event -> overlayFrameView.removeContent((Node)event.getSource()));

            overlayFrameView.addContent(imageButton, positions[i]);
        }

        showView(rootVBox, "CenditLab.Reduced | OverlayFrameView Test", 600.0, 400.0);
    }

    private static void itemFrameViewTest()
    {
        ComponentIconView iconView =
                new ComponentIconView("Component", "Description");
        ItemView itemView = new ItemView(iconView);

        itemView.setOnButtonClicked(
                new EventHandler<MouseEvent>()
                {
                    private boolean buttonFlag = true;

                    @Override
                    public void handle(MouseEvent event)
                    {
                        if (buttonFlag)
                            itemView.showButton(ItemView.ADD_BUTTON);
                        else
                            itemView.showButton(ItemView.REMOVE_BUTTON);

                        buttonFlag = ! buttonFlag;
                    }
                });

        itemView.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    boolean iconFlag = true;

                    @Override
                    public void handle(MouseEvent event)
                    {
                        if (iconFlag)
                            itemView.showIcon(ItemView.SELECTED_ICON);
                        else
                            itemView.hideIcon();

                        iconFlag = ! iconFlag;
                    }
                });

        showView(itemView, "CenditLab.Reduced | ItemView Test", 600.0, 400.0);
    }


    private static void showView(Parent root, String title, double width, double height)
    {
        stage.setScene(new Scene(root, width, height));
        stage.setTitle(title);
        stage.show();
    }
}
