package ve.gob.cendit.cenditlab.control;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MeasurementManager
{
    private String name;
    private int currentIndex = -1;
    private MeasurementStep currentStep;
    private MeasurementStep previousStep;

    private EventEmitter<IStepChangeListener> stepChangeEventEmitter;

    private List<MeasurementStep> stepsList;

    public MeasurementManager(String name, MeasurementStep... stepsArgs)
    {
        this.name = name;

        stepChangeEventEmitter = new EventEmitter<>("onStepChange", this::onStepChangeCaller);

        stepsList = new LinkedList<MeasurementStep>();
        addSteps(stepsArgs);
    }

    public void addStep(MeasurementStep step)
    {
        if (step == null)
        {
            throw new IllegalArgumentException("step must not be null");

        }

        stepsList.add(step);
    }

    public void addSteps(MeasurementStep... stepsArgs)
    {
        if (stepsArgs == null)
        {
            throw new IllegalArgumentException("stepsArgs must not be null");
        }

        Arrays.stream(stepsArgs)
            .forEach(step -> {
                if (step == null)
                {
                    throw new IllegalArgumentException("no step in stepArgs must not be null");
                }
                step.setMeasurementManager(this);
                stepsList.add(step);
            });
    }

    public void removeStep(MeasurementStep step)
    {
        if (step != null && step.getMeasurementManager() == this)
        {
            stepsList.remove(step);
        }
    }

    public void clearSteps()
    {
        stepsList.clear();
    }

    public int getStepsCount()
    {
        return stepsList.size();
    }

    public int getCurrentStepIndex()
    {
        return currentIndex;
    }

    public MeasurementStep getCurrentStep()
    {
        return currentStep;
    }

    public MeasurementStep getStep(int index)
    {
        return stepsList.get(index);
    }

    public List<MeasurementStep> getSteps()
    {
        return Collections.unmodifiableList(stepsList);
    }

    public boolean hasNextStep()
    {
        return getCurrentStepIndex() < getStepsCount() - 1;
    }

    public boolean hasPrevStep()
    {
        return getCurrentStepIndex() > 0;
    }


    public boolean canGoNextStep()
    {
        return canGoToStep(getCurrentStepIndex() + 1);
    }

    public boolean canGoPrevStep()
    {
        return canGoToStep(getCurrentStepIndex() - 1);
    }

    public boolean canGoToStep(MeasurementStep step)
    {
        if (step == null || step == currentStep)
        {
            return false;
        }

        if (currentStep != null)
        {
            if (currentStep.canExitToStep(step) && step.canEnterFromStep(currentStep))
            {
                return true;
            }
        }
        else if (step.canEnterFromStep(currentStep))
        {
            return true;
        }

        return false;
    }

    public boolean canGoToStep(int index)
    {
        if (index >= 0 && index < stepsList.size())
        {
            return canGoToStep(stepsList.get(index));
        }

        return false;
    }

    public boolean nextStep()
    {
        return toStep(getCurrentStepIndex() + 1);
    }

    public boolean prevStep()
    {
         return toStep(getCurrentStepIndex() - 1);
    }

    public boolean toStep(int index)
    {
        if (canGoToStep(index))
        {
            return toStep(stepsList.get(index));
        }

        return false;
    }

    public boolean toStep(MeasurementStep step)
    {
        if (canGoToStep(step))
        {
            switchToStep(step);

            return true;
        }

        return false;
    }

    private void switchToStep(MeasurementStep step)
    {
        if (currentStep != null)
        {
            currentStep.executeUnload();
        }

        previousStep = currentStep;
        currentStep = step;
        currentIndex = stepsList.indexOf(step);

        currentStep.executeInitialize();
        currentStep.executeLoad();

        currentStep.executeRun();

        // Emite evento al cambiar de paso
        stepChangeEventEmitter.call(this, (Object) null);
    }

    public void initialize()
    {
        stepsList.stream()
                .forEach(step ->  step.initialize());
    }

    public void addOnStepChangeListener(IStepChangeListener listener)
    {
        stepChangeEventEmitter.addListener(listener);
    }

    public void removeOnStepChangeListener(IStepChangeListener listener)
    {
        stepChangeEventEmitter.removeListener(listener);
    }

    private void onStepChangeCaller(Object source, IStepChangeListener handler, Object... args)
    {
        handler.onChangeStep(previousStep, currentStep);
    }
}
