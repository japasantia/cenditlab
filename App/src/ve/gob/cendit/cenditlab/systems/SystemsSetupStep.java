package ve.gob.cendit.cenditlab.systems;

import ve.gob.cendit.cenditlab.app.CenditLabApplication;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.MeasurementStep;
import ve.gob.cendit.cenditlab.ui.SystemsSetupStepView;
import ve.gob.cendit.cenditlab.ui.SystemsSetupStepViewProto1;


public class SystemsSetupStep extends MeasurementStep
{
    private boolean blocked = false;
    private SystemsSetupStepViewProto1 systemsSetupView;

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
        systemsSetupView = new SystemsSetupStepViewProto1();
    }

    @Override
    public void load()
    {
        CenditLabApplication.getApp().setCenterContainer(systemsSetupView);
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
