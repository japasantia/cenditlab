package ve.gob.cendit.cenditlab.systems;

import javafx.scene.Node;
import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.control.*;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.data.DataContainer;
import ve.gob.cendit.cenditlab.ui.ComponentViewFactory;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public class NoiseFigureAnalyzer8975ASystem extends System
{
    private static final String NAME = "Noise Figure Analyzer 8975A";
    private static final String DESCRIPTION = "Performs noise figure measurements";

    private static final Image ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975ASystem.class.getResource("/ve/gob/cendit/cenditlab/ui/images/task-icon.jpg").toExternalForm());

    private static final Image TASK_ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975ASystem.class.getResource("/ve/gob/cendit/cenditlab/ui/images/task-icon.jpg").toExternalForm());

    private static final ComponentViewFactory viewFactory = ComponentViewFactory.get();

    private static final ComponentDescriptor systemDescriptor = new ComponentDescriptor(
            NAME,
            DESCRIPTION,
            ICON_IMAGE,
            arg -> new NoiseFigureAnalyzer8975ASystem());

    private static final ComponentDescriptor[] taskDescriptors =
        {
                NoiseFigureMeasureTask.descriptor,
                NoisePowerMeasureTask.descriptor
        };

    public NoiseFigureAnalyzer8975ASystem()
    {
        super(systemDescriptor);
    }

    @Override
    public ComponentDescriptor[] getTaskDescriptors()
    {
        return taskDescriptors;
    }

    @Override
    public Node getView(ViewType viewType)
    {
        return viewFactory.getView(this, viewType);
    }

    @Override
    public DataContainer getSetupData()
    {
        return null;
    }

    private static class NoiseFigureMeasureTask extends Task
    {
        private static final ComponentDescriptor descriptor = new ComponentDescriptor(
                "Noise Figure Measurement",
                "Measures the noise figure of a two port device",
                TASK_ICON_IMAGE,
                arg -> new NoiseFigureMeasureTask());

        public NoiseFigureMeasureTask()
        {
            super(descriptor);
        }

        @Override
        public void run(TaskContext context)
        {

        }

        @Override
        public DataContainer getSetupData()
        {
            return null;
        }

        @Override
        public Node getView(ViewType viewType)
        {
            return viewFactory.getView(this, viewType);
        }
    }

    private static class NoisePowerMeasureTask extends Task
    {
        private static final ComponentDescriptor descriptor = new ComponentDescriptor(
                "Noise Power Measurement",
                "Measures the noise power of a two port device",
                TASK_ICON_IMAGE,
                arg -> new NoisePowerMeasureTask());

        public NoisePowerMeasureTask()
        {
            super(descriptor);
        }

        @Override
        public void run(TaskContext context)
        {

        }

        @Override
        public DataContainer getSetupData()
        {
            return null;
        }

        @Override
        public Node getView(ViewType viewType)
        {
            return viewFactory.getView(this, viewType);
        }
    }
}
