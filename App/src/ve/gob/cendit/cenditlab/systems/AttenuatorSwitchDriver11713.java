package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.control.MeasurementManager;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.control.TaskContext;
import ve.gob.cendit.cenditlab.data.FieldsContainer;
import ve.gob.cendit.cenditlab.ui.ComponentViewFactory;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class AttenuatorSwitchDriver11713 extends System
{
    private static final String NAME = "Attenuator and Switch Driver 11713";
    private static final String DESCRIPTION = "Attenuator and switch controller and driver";

    private static final Image ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975A.class.getResource("/ve/gob/cendit/cenditlab/ui/images/asd11713c.jpg").toExternalForm());

    private final Image TASK_ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975A.class.getResource("/ve/gob/cendit/cenditlab/ui/images/task-icon.jpg").toExternalForm());


    private static final ComponentViewFactory viewFactory = ComponentViewFactory.get();

    private Task[] taskArray =
        {
            new SetAttenuationTask(),
            new SetSwitchStateTask()
        };

    public AttenuatorSwitchDriver11713()
    {
        super(NAME, DESCRIPTION, ICON_IMAGE);
    }

    @Override
    public Task[] getTasks()
    {
        return taskArray;
    }

    @Override
    public Node getView(ViewType viewType)
    {
        return viewFactory.getView(this, viewType);
    }

    @Override
    public Node getView(String viewId)
    {
        return null;
    }

    @Override
    public FieldsContainer getSetupFields()
    {
        return null;
    }

    private class SetAttenuationTask extends Task
    {
        private static final String NAME = "Attenuation Setter";
        private static final String DESCRIPTION = "Sets attenuation on the device";


        public SetAttenuationTask()
        {
            super(NAME, DESCRIPTION, TASK_ICON_IMAGE);
        }

        @Override
        public void run(TaskContext context)
        {

        }

        @Override
        public FieldsContainer getSetupFields()
        {
            return null;
        }



        @Override
        public Node getView(ViewType viewType)
        {
            return viewFactory.getView(this, viewType);
        }

        @Override
        public Node getView(String viewId)
        {
            return null;
        }
    }


    private class SetSwitchStateTask extends Task
    {
        private static final String NAME = "Change Switch State";
        private static final String DESCRIPTION = "Change switch state on selected switch";

        public SetSwitchStateTask()
        {
            super(NAME, DESCRIPTION, TASK_ICON_IMAGE);
        }

        @Override
        public void run(TaskContext context)
        {

        }

        @Override
        public FieldsContainer getSetupFields()
        {
            return null;
        }

        @Override
        public Node getView(ViewType viewType)
        {
            return viewFactory.getView(this, viewType);
        }

        @Override
        public Node getView(String viewId)
        {
            return null;
        }
    }
}
