/*
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.data.*;
import ve.gob.cendit.cenditlab.ui.FieldContainerView;
import ve.gob.cendit.cenditlab.ui.IconView;
import ve.gob.cendit.cenditlab.ui.MainViewProto1;
import ve.gob.cendit.cenditlab.ui.MasterDetailView;

import java.util.Random;

public class TaskTests extends Application
{
    private Field[] fieldsArray;
    private DemoTask[] taskArray;
    private Stage rootStage;
    private Parent mainParent;

    public static void main(String [] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        rootStage = primaryStage;

        initialize();
        prepareViews();
        showMainView(mainParent, "CenditLab | Task Test", 800, 600);
    }

    private void initialize()
    {
        fieldsArray = new Field[5];
        fieldsArray[0] = new Field();
        fieldsArray[1] = new NumericField();
        fieldsArray[2] = new FrequencyField();
        fieldsArray[3] = new EnrField();
        fieldsArray[4] = new TemperatureField();

        taskArray = new DemoTask[3];

        for (int i = 0; i < taskArray.length; ++i)
        {
            taskArray[i] = new DemoTask("DemoTask " + i, "Task Description",
                    null, createRandomFieldsContainer(3));
        }
    }

    private void prepareViews()
    {
        MainViewProto1 mainView = new MainViewProto1();
        MasterDetailView masterDetailView = new MasterDetailView();

        for(DemoTask task : taskArray)
        {
            IconView iconView = new IconView(task.getName(), task.getIcon());
            iconView.setOnMouseClicked(event ->
                {
                    masterDetailView.clearDetail();
                    masterDetailView.addDetail(task.getSetupFieldContainerView());
                });

            masterDetailView.addMaster(iconView);
        }

        BorderPane.setAlignment(masterDetailView, Pos.TOP_LEFT);
        mainView.setCenter(masterDetailView);
        mainParent = mainView;
    }

    private void showMainView(Parent root, String title, double width, double height)
    {
        rootStage.setScene(new Scene(root, width, height));
        rootStage.setTitle(title);
        rootStage.show();
    }

    private FieldsContainer createRandomFieldsContainer(int size)
    {
        FieldsContainer fieldsContainer = new FieldsContainer();
        Random random = new Random();

        for (int i = 0; i < size; ++i)
        {
            Field field = fieldsArray[random.nextInt(fieldsArray.length)];
            fieldsContainer.add(
                    String.format("%s %d",field.getClass().getSimpleName(), i),
                    field);
        }

        return fieldsContainer;
    }
}


class DemoTask extends Task
{
    private FieldsContainer setupFieldsContainer;

    private FieldContainerView fieldsView;

    public DemoTask(String name, String description, String iconUrl,
                    FieldsContainer setupFieldsContainer)
    {
        super(name, description, iconUrl);

        setSetupFields(setupFieldsContainer);
    }

    public FieldContainerView getSetupFieldContainerView()
    {
        if (fieldsView == null)
        {
            fieldsView = new FieldContainerView();

            for (int i = 0; i < setupFieldsContainer.getFieldsCount(); ++i)
            {
                String name = setupFieldsContainer.getName(i);
                Field field = setupFieldsContainer.get(name);
                fieldsView.addField(name, field);
            }
        }

        return fieldsView;
    }

    public void setSetupFields(FieldsContainer fieldsContainer)
    {
        setupFieldsContainer = fieldsContainer;
    }

    @Override
    public FieldsContainer getSetupFields()
    {
        return setupFieldsContainer;
    }

    @Override
    public void execute()
    {

    }
}
*/
