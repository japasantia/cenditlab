package ve.gob.cendit.cenditlab.systems;

import ve.gob.cendit.cenditlab.app.CenditLabApplication;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
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
        CenditLabApplication.getApp().setCenterContainer(systemsSetupView);
        systemsSetupView.setSystems(systemsArray);
    }

    @Override
    public void run()
    {

    }

    @Override
    public void unload()
    {
        systemsSetupView.clearSystems();
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
