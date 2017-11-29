package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.control.*;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.data.FieldsContainer;
import ve.gob.cendit.cenditlab.ui.ComponentViewFactory;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

import java.net.URL;


public class NoiseFigureAnalyzer8975A extends System
{
    private static final String NAME = "Noise Figure Analyzer 8975A";
    private static final String DESCRIPTION = "Performs noise figure measurements";

    private static final Image ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975A.class.getResource("/ve/gob/cendit/cenditlab/ui/images/task-icon.jpg").toExternalForm());

    private final Image TASK_ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975A.class.getResource("/ve/gob/cendit/cenditlab/ui/images/task-icon.jpg").toExternalForm());

    private static final ComponentViewFactory viewFactory = ComponentViewFactory.get();

    private Task[] tasksArray =
        {
                new NoiseFigureMeasureTask(),
                new NoisePowerMeasureTask()
        };

    public NoiseFigureAnalyzer8975A()
    {
        super(NAME, DESCRIPTION, ICON_IMAGE);
    }

    @Override
    public Task[] getTasks()
    {
        return tasksArray;
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

    private class NoiseFigureMeasureTask extends Task
    {
        private static final String NAME = "Noise Figure Measurement";
        private static final String DESCRIPTION = "Measures the noise figure of a two port device";


        public NoiseFigureMeasureTask()
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

    private class NoisePowerMeasureTask extends Task
    {
        private static final String NAME = "Noise Power Measurement";
        private static final String DESCRIPTION = "Measures the noise power of a two port device";

        public NoisePowerMeasureTask()
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
