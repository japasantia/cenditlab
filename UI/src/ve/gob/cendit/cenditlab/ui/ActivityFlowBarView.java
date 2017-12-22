package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import ve.gob.cendit.cenditlab.control.ActivityFlow;
import ve.gob.cendit.cenditlab.control.Activity;

public class ActivityFlowBarView extends ToolBar
{
    private static final String FXML_URL = "fxml/activity-flow-bar-view.fxml";

    private ActivityFlow activityFlow;
    private ActivityButtonView selectedActivityButton;

    public ActivityFlowBarView()
    {
        ViewLoader.load(FXML_URL, this, this);

        loadActivityFlow();
    }

    public ActivityFlowBarView(ActivityFlow activityFlow)
    {
        ViewLoader.load(FXML_URL, this, this);

        setActivityFlow(activityFlow);
    }

    private void loadActivityFlow()
    {
        if (activityFlow == null)
        {
            return;
        }

        ToggleGroup stepButtonsToggleGroup = new ToggleGroup();

        for (int i = 0; i < activityFlow.getAtivitiesCount(); i++)
        {
            Activity activity = activityFlow.getActivity(i);
            ActivityButtonView stepButton = new ActivityButtonView(activity);
            stepButton.setToggleGroup(stepButtonsToggleGroup);

            stepButton.setOnAction(event ->
                {
                    Activity currentActivity = activityFlow.getCurrentActivity();
                    System.out.printf("Check enter to %s from %s\n",
                            activity.toString(),
                            (currentActivity != null ? currentActivity.toString() : "START"));

                    if (activityFlow.toActivity(activity))
                    {
                        // TODO: cambio de activity exitoso
                        stepButton.setSelected(true);
                        selectedActivityButton = stepButton;

                        System.out.printf("Can enter to %s\n", activity.toString());
                    }
                    else
                    {
                        // TODO: no se logra cambiar de paso
                        stepButton.setSelected(false);
                        selectedActivityButton.setSelected(true);
                        selectedActivityButton.requestFocus();

                        System.out.printf("Cannot enter to %s\n", activity.toString());
                    }
                });

            addActivityButton(stepButton);
        }

        selectedActivityButton = (ActivityButtonView) this.getItems().get(0);
    }

    public void setActivityFlow(ActivityFlow activityFlow)
    {
        if (activityFlow == null)
        {
            throw new IllegalArgumentException("activityFlow must not be null");
        }

        this.activityFlow = activityFlow;

        loadActivityFlow();
    }

    public void addActivityButton(ActivityButtonView buttonView)
    {
        this.getItems().add(buttonView);
    }

    public void addActivityButtons(ActivityButtonView... buttonViews)
    {
        this.getItems().addAll(buttonViews);
    }
}
