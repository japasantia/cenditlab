import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import ve.gob.cendit.cenditlab.data.*;
import ve.gob.cendit.cenditlab.ui.*;

import java.util.ArrayList;
import java.util.List;
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
        graphViewTest();
        // toolboxViewTest();
        // containerViewTest();
        // itemsListViewTest();
        // itemFrameViewTest();
        // dialogViewTest();
        // listDataViewTest();
        // tabularViewTest();
        // viewFactoryTest();
        // dataContainerViewTest();
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
            IconView iconView = new IconView();
            iconView.setName(String.format("Component %d", i));
            iconView.setDescription(String.format("This is the component %d", i));
            containerVBox.getChildren().addAll(iconView);
        }


        showView(scrollPane, "CenditLab.Reduced | Component Views Tests",
                800, 600);
    }

    private static void graphViewTest()
    {
        GraphData[] graphDataArray1 =
            {
                new GraphData("Graph 1",
                        new ListData("X", "0,1,2,3,4,5,6,7,8,9"),
                        new ListData("Y", "10,20,30,40,50,60,70,80,90,100")),
                new GraphData("Graph 2",
                        new ListData("X", "0,1,2,3,4,5,6,7,8,9"),
                        new ListData("Y", "4,2,6,8,1,6,7,2,9,4")),
                new GraphData("Graph 3",
                        new ListData("X", "0,1,2,3,4,5,6,7,8,9"),
                        new ListData("Y", "-5,5,-5,5,-5,5,-5,5,-5,5"))
            };

        GraphData[] graphDataArray2 =
            {
                new GraphData("Graph 1",
                        "0,1,2,3,4,5,6,7,8,9",
                        "10,20,30,40,50,60,70,80,90,100"),
                new GraphData("Graph 2",
                        "0,1,2,3,4,5,6,7,8,9",
                        "4,2,6,8,1,6,7,2,9,4"),
                new GraphData("Graph 3",
                        "0,1,2,3,4,5,6,7,8,9",
                        "-5,5,-5,5,-5,5,-5,5,-5,5")
            };

        GraphData[] graphDataArray3 =
            {
                new GraphData("Graph 1",
                        "[0,1,2,3,4,5,6,7,8,9][10,20,30,40,50,60,70,80,90,100]"),
                new GraphData("Graph 2",
                        "[0,1,2,3,4,5,6,7,8,9][4,2,6,8,1,6,7,2,9,4]"),
                new GraphData("Graph 3",
                        "[0,1,2,3,4,5,6,7,8,9][-5,5,-5,5,-5,5,-5,5,-5,5]")
            };

        GraphView graphView1 = new GraphView("Graph 1");
        GraphView graphView2 = new GraphView("Graph 2");
        GraphView graphView3 = new GraphView("Graph 3");

        graphView1.addGraphData(graphDataArray1);
        graphView2.addGraphData(graphDataArray2);
        graphView3.addGraphData(graphDataArray3);

        VBox vBox = new VBox(graphView1, graphView2, graphView3);

        showView(new ScrollPane(vBox), "CenditLab.Reduced | GraphView Test",
                800, 600);
    }

    private static void toolboxViewTest()
    {
        ToolboxView<String> toolboxView = new ToolboxView<>();

        String[] values = new String[10];

        for (int i = 0; i < 10; i++)
        {
            values[i] = "Item " + i;
        }

        toolboxView.setItemViewFactory(text -> new IconView(text, null, null));
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

    private static void itemsListViewTest()
    {
        ItemsListView<String> itemsListView = new ItemsListView();

        String[] values = new String[10];

        for (int i = 0; i < 10; i++)
        {
            values[i] = "Item " + i;
        }

        itemsListView.getItemsList()
            .setViewFactory(item -> new ItemView(new IconView(item.getValue(), null, null)));

        itemsListView.getItemsList().setAll(values);

        itemsListView.setOnItemClicked(item -> {
            System.out.printf("%s clicked\n", item.toString());

            /*
            if (item.isSelected())
                item.getView().showIcon(ItemView.SELECTED_ICON);
            else
                item.getView().hideIcon();
            */
        });

        showView(itemsListView, "CenditLab.Reduced | ItemsListView Test", 600.0, 400.0);
    }

    private static void containerViewTest()
    {
        ContainerView<String> containerView = new ContainerView<>();

        String[] values = new String[10];

        for (int i = 0; i < 10; i++)
        {
            values[i] = "Item " + i;
        }

        containerView.getItemsList().setViewFactory(item -> new ItemView(new IconView(item.getValue(), "Container Item", null)));

        // containerView.setOnItemClickedListener(item -> item.getView().showIcon(ItemView.SELECTED_ICON));

        containerView.getItemsList().setAll(values);

        showView(containerView, "CenditLab.Reduced | ContainerView Test", 600.0, 400.0);
    }

    private static void dialogViewTest()
    {
        VBox rootVBox = new VBox();
        rootVBox.setAlignment(Pos.CENTER);

        Button dialogButton1 = new Button("Ok dialog");
        Button dialogButton2 = new Button("Ok-Cancel dialog");
        Button dialogButton3 = new Button("Retry-Abort-Ignore dialog");
        Button dialogButton4 = new Button("Without buttons dialog");

        rootVBox.getChildren().addAll(dialogButton1, dialogButton2,
                dialogButton3, dialogButton4);

        DialogView dialogView = DialogView.createModal(stage);
        dialogView.setTitle("DialogView");
        dialogView.setContent(new FrequencySetupViewProto1());

        dialogButton1.setOnAction(event -> {
            dialogView.setTitle("Ok dialog");
            dialogView.setButtons(DialogView.OK);
            dialogView.show();
        });

        dialogButton2.setOnAction(event -> {
            dialogView.setTitle("Ok-Cancel dialog");
            dialogView.setButtons(DialogView.OK_CANCEL);
            dialogView.show();
        });

        dialogButton3.setOnAction(event -> {
            dialogView.setTitle("Retry-Abort-Ignore dialog");
            dialogView.setButtons(DialogView.RETRY_ABORT_IGNORE);
            dialogView.show();
        });

        dialogButton4.setOnAction(event -> {
            dialogView.setTitle("Without buttons dialog");
            dialogView.setButtons(DialogView.NONE);
            dialogView.show();
        });

        stage.getIcons().add(Resources.ADD_ICON);

        showView(rootVBox, "CenditLab.Reduced | DialogView Test", 600.0, 400.0);
    }

    private static void listDataViewTest()
    {
        ListDataView listDataView = new ListDataView();

        ListData listData = new ListData("Data", "0,1,2,3,4,5,6,7,8,9");

        listDataView.setListData(listData);

        VBox rootVBox = new VBox();
        rootVBox.setFillWidth(false);

        TextField textField = new TextField();
        Button button = new Button("Select item");
        button.setOnAction(event -> {
            int index = Integer.parseInt(textField.getText());
            listDataView.scrollToItem(index);
        });
        HBox hBox = new HBox(textField, button);

        rootVBox.getChildren()
            .addAll(listDataView, hBox);

        showView(rootVBox, "CenditLab.Reduced | ListDataView Test", 600.0, 400.0);
    }

    private static final void tabularViewTest()
    {
        TabularView tabularView = new TabularView();
        VBox rootVBox = new VBox(tabularView);
        rootVBox.setFillWidth(false);

        tabularView.getItems()
            .addAll(new ListData("C1", "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16"),
                    new ListData("C2", "10,20,30,40,50,60,70,80,90"),
                    new ListData("C3", "11,22,33,44,55,66,77,88,99"),
                    new ListData("C4", "15,25,35,45,55,65,75,85,95"));

        showView(rootVBox, "CenditLab.Reduced | TabularView Test", 600.0, 400.0);
    }

    private static final void viewFactoryTest()
    {
        Setup[] setupArray = { new FrequencySetup(), new EnrSetup() };

        ScrollPane setupView = (ScrollPane) ViewFactory.buildSetupView(setupArray);

        showView(setupView,"CenditLab.Reduced | ViewFactory Test", 600.0, 400.0);
    }

    private static final void dataContainerViewTest()
    {
        Data[] dataArray =
            {
                new ValueData(),
                new NumericData("10.0", Unit.DB),
                new FrequencyData("20.0", Unit.HZ),
                new EnrData("30.0", Unit.DB),
                new ListData("List Data", "1, 2 ,3 ,4, 5, 6, 7, 8, 9 , 0")
            };

        DataContainerView dataContainerView = new DataContainerView(dataArray, dataArray);

        showView(dataContainerView, "CenditLab.Reduced | DataContainerView Test", 600.0, 400.0);
    }

    private static void showView(Parent root, String title, double width, double height)
    {
        stage.setScene(new Scene(root, width, height));
        stage.setTitle(title);
        stage.show();
    }
}
