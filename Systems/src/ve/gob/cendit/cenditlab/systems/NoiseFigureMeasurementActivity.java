package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.Activity;
import ve.gob.cendit.cenditlab.control.ActivityFlow;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.IApplicationController;
import ve.gob.cendit.cenditlab.ui.ActivityFlowBarView;

public class NoiseFigureMeasurementActivity extends Activity
{
    private static final String NAME = "Noise Figure Measurement Activity";

    private System[] systems;

    private Activity[] activities;

    private ActivityFlowBarView activityFlowBarView;
    private ActivityFlow activityFlow;

    public NoiseFigureMeasurementActivity(IApplicationController applicationController)
    {
        super(NAME, applicationController);
    }

    @Override
    public boolean canEnterFromActivity(Activity activity)
    {
        return true;
    }

    @Override
    public boolean canExitToActivity(Activity activity)
    {
        return true;
    }

    @Override
    public Node getView()
    {
        return activityFlow.getCurrentActivity().getView();
    }

    @Override
    protected void initialize()
    {
        systems = new System[]
            {
                new NoiseFigureAnalyzer8975ASystem(),
                new AttenuatorSwitchDriver11713System()
            };

        IApplicationController applicationController = getApplicationController();

        activities = new Activity[]
            {
                new SystemsSetupActivity("Systems Setup", applicationController, systems),
                new TasksSetupActivity("Task Setup", applicationController, systems),
                new TasksExecutionActivity("Task Execution", applicationController, systems)
            };

        activityFlow = new ActivityFlow(NAME, activities);
        activityFlowBarView = new ActivityFlowBarView(activityFlow);

        setActivityFlow(activityFlow);
    }

    @Override
    protected void load()
    {
        Activity activity = activityFlow.loadCurrentActivity();

        if (activity != null)
        {
            getApplicationController().setTopToolbar(activityFlowBarView);
            getApplicationController().setMainView(activity.getView());
        }
    }

    @Override
    protected void run()
    {

    }

    @Override
    protected void unload()
    {
        activityFlow.unloadCurrentActivity();
    }
}
