import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ve.gob.cendit.cenditlab.control.MeasurementManager;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
import ve.gob.cendit.cenditlab.ui.MainViewProto1;
import ve.gob.cendit.cenditlab.ui.MeasurementBarView;

import java.io.PrintStream;

public class MeasurementTests extends Application
{
    private Stage mainStage;

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        mainStage = primaryStage;

        basicMeasurementSessionTest();
    }

    private Stage getMainStage()
    {
        return mainStage;
    }

    private void basicMeasurementSessionTest()
    {
        TestMeasurementStep testStep1 =
                new TestMeasurementStep("Setup system [step 1]");

        TestMeasurementStep testStep2 =
                new TestMeasurementStep("Setup tasks [step 2]");

        TestMeasurementStep testStep3 =
                new TestMeasurementStep("Execution [step 3]");

        MeasurementManager measurementManager =
                new MeasurementManager("Measurement Manager Test",
                        testStep1, testStep2, testStep3);

        MeasurementBarView measurementBarView
                = new MeasurementBarView(measurementManager);

        MainViewProto1 mainView = new MainViewProto1();
        mainView.addToolBar(measurementBarView);

        showMainWindow(mainView,
                "CenditLab | Basic Application Test", 800, 600);
    }

    private void showMainWindow(Parent parent, String title,
                                double width, double height)
    {
        Scene root = new Scene(parent, width, height);
        Stage mainStage = getMainStage();
        mainStage.setScene(root);
        mainStage.setTitle(title);
        mainStage.show();
    }
}

class TestMeasurementStep extends MeasurementStep
{
    private PrintStream out;

    public TestMeasurementStep(String name)
    {
        super(name);

        out = System.out;
    }

    @Override
    public boolean canEnterFromStep(MeasurementStep step)
    {
        printTrace("in canEnterFromStep");

        return Math.random() > 0.5;
    }

    @Override
    public boolean canExitToStep(MeasurementStep step)
    {
        printTrace("in canExitToStep");

        return Math.random() > 0.5;
    }

    @Override
    public void initialize()
    {
        printTrace("on initialize");
    }

    @Override
    public void load()
    {
        printTrace("on load");
    }

    @Override
    public void run()
    {
        printTrace("on run");
    }

    @Override
    public void unload()
    {
        printTrace("on unload");
    }

    private void printTrace(String message)
    {
        out.printf("Step %s: %s\n", getName(), message);
    }
}
