package ve.gob.cendit.cenditlab.ui;


import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import ve.gob.cendit.cenditlab.control.MeasurementManager;
import ve.gob.cendit.cenditlab.control.MeasurementStep;

public class MeasurementBarView extends ToolBar
{
    private static final String FXML_URL = "fxml/measurement-bar-view.fxml";

    @FXML
    private ToolBar containerToolBar;

    private MeasurementManager measurementManager;
    private StepButtonView selectedStepButton;

    public MeasurementBarView()
    {
        ViewLoader.load(FXML_URL, this, this);

        initialize();
    }

    public MeasurementBarView(MeasurementManager manager)
    {
        ViewLoader.load(FXML_URL, this, this);

        setMeasurementManager(manager);

        initialize();
    }

    private void initialize()
    {
        if (measurementManager == null)
        {
            return;
        }

        ToggleGroup stepButtonsToggleGroup = new ToggleGroup();

        for (int i = 0; i < measurementManager.getStepsCount(); i++)
        {
            MeasurementStep step = measurementManager.getStep(i);
            StepButtonView stepButton = new StepButtonView(step);
            stepButton.setToggleGroup(stepButtonsToggleGroup);

            stepButton.setOnAction(event ->
                {
                    MeasurementStep currentStep = measurementManager.getCurrentStep();
                    System.out.printf("Check enter to %s from %s\n",
                            step.toString(),
                            (currentStep != null ? currentStep.toString() : "START"));

                    if (measurementManager.toStep(step))
                    {
                        // TODO: cambio de step exitoso
                        stepButton.setSelected(true);
                        selectedStepButton = stepButton;

                        System.out.printf("Can enter to %s\n", step.toString());
                    }
                    else
                    {
                        // TODO: no se logra cambiar de paso
                        stepButton.setSelected(false);
                        selectedStepButton.setSelected(true);
                        selectedStepButton.requestFocus();

                        System.out.printf("Cannot enter to %s\n", step.toString());
                    }
                });

            addStepButton(stepButton);
        }

        selectedStepButton = (StepButtonView) this.getItems().get(0);
    }

    public void setMeasurementManager(MeasurementManager manager)
    {
        if (manager == null)
        {
            throw new IllegalArgumentException("manager must not be null");
        }

        this.measurementManager = manager;
    }

    public void addStepButton(StepButtonView buttonView)
    {
        containerToolBar.getItems().add(buttonView);
    }

    public void addStepButtons(StepButtonView... buttonViews)
    {
        containerToolBar.getItems().addAll(buttonViews);
    }
}
