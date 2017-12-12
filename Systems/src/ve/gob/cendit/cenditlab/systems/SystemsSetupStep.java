package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.SystemsSetupStepView;


public class SystemsSetupStep extends MeasurementStep
{
    private boolean blocked = false;
    private SystemsSetupStepView systemsSetupView;

    private System[] systemsArray;

    public SystemsSetupStep(String name, System... systems)
    {
        super(name);

        systemsArray = systems;
    }

    @Override
    public Node getView()
    {
        return systemsSetupView;
    }

    @Override
    public boolean canEnterFromStep(MeasurementStep step)
    {
        return ! isBlocked();
    }

    @Override
    public boolean canExitToStep(MeasurementStep step)
    {
        return ! isBlocked();
    }

    @Override
    public void initialize()
    {
        systemsSetupView = new SystemsSetupStepView();
    }

    @Override
    public void load()
    {
        systemsSetupView.loadSystems(systemsArray);
    }

    @Override
    public void run()
    {

    }

    @Override
    public void unload()
    {
        systemsSetupView.unloadSystems();
    }

    public void setBlocked(boolean value)
    {
        blocked = value;
    }

    public boolean isBlocked()
    {
        return blocked;
    }
}
