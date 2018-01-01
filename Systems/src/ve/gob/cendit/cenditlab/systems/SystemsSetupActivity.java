package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import ve.gob.cendit.cenditlab.control.ActivityFlow;
import ve.gob.cendit.cenditlab.control.Activity;
import ve.gob.cendit.cenditlab.control.IApplicationController;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.ui.SystemsSetupActivityView;

public class SystemsSetupActivity extends Activity
{
    private boolean blocked = false;
    private SystemsSetupActivityView systemsSetupView;

    private System[] systemsArray;

    public SystemsSetupActivity(String name, IApplicationController applicationController, System... systems)
    {
        super(name, applicationController);

        systemsArray = systems;
    }

    @Override
    public Node getView()
    {
        return systemsSetupView;
    }

    public boolean canEnterFromActivity(Activity activity)
    {
        return ! isBlocked();
    }

    @Override
    public boolean canExitToActivity(Activity activity)
    {
        return ! isBlocked();
    }

    @Override
    public void initialize()
    {
        systemsSetupView = new SystemsSetupActivityView();
        systemsSetupView.setSystems(systemsArray);
    }

    @Override
    public void load()
    {
        systemsSetupView.load();
        getApplicationController().setMainView(systemsSetupView);
    }

    @Override
    public void run()
    {

    }

    @Override
    public void unload()
    {
        systemsSetupView.unload();
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
