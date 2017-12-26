package ve.gob.cendit.cenditlab.tests.app;

import javafx.application.Application;
import javafx.stage.Stage;
import ve.gob.cendit.cenditlab.app.CenditLabApplication;
import ve.gob.cendit.cenditlab.systems.NoiseFigureMeasurementActivity;
import ve.gob.cendit.cenditlab.ui.ActivityFlowBarView;

public class CenditLabApplicationTests extends Application
{
    private CenditLabApplication app;

    private NoiseFigureMeasurementActivity noiseFigureMeasurementActivity;

    private ActivityFlowBarView activityFlowBarView;

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        CenditLabApplication.initialize(primaryStage);

        app = CenditLabApplication.getApp();

        initializeApp();
        initializeGui();

        app.show();
    }

    private void initializeApp()
    {
        noiseFigureMeasurementActivity = new NoiseFigureMeasurementActivity(app);

        noiseFigureMeasurementActivity.executeInitialize();
        noiseFigureMeasurementActivity.executeLoad();
    }

    private void initializeGui()
    {
        activityFlowBarView = new ActivityFlowBarView(noiseFigureMeasurementActivity.getActivityFlow());
    }
}
