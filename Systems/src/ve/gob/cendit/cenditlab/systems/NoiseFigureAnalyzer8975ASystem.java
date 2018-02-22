package ve.gob.cendit.cenditlab.systems;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.control.*;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.data.*;
import ve.gob.cendit.cenditlab.ui.Resources;

public class NoiseFigureAnalyzer8975ASystem extends System
{
    private static final String NAME = "Noise Figure Analyzer 8975A";
    private static final String DESCRIPTION = "Performs noise figure measurements";

    private static final Image ICON_IMAGE = Resources.TASK_ICON;

    private static final Image TASK_ICON_IMAGE = Resources.TASK_ICON;

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

    private static final Setup[] setupArray =
        { new FrequencySetup(), new EnrSetup() };

    private static final Data[] inputDataArray =
        { new ValueData(), new FrequencyData(), new EnrData(), new ListData("List Data", "0,1,2,3,4,5,6,7,8,9") };

    private static final Data[] outputDataArray =
            { new GraphData("Graph", "[0,1,2,3,4,5,6,7,8,9][9,8,7,6,5,4,3,2,1,0]") };

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
    public Setup[] getSetup()
    {
        return setupArray;
    }

    @Override
    public Data[] getData(DataDirection direction)
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
        public Setup[] getSetup()
        {
            return null;
        }

        @Override
        public Data[] getData(DataDirection direction)
        {
            switch (direction)
            {
                case INPUT:
                    return inputDataArray;
                case OUTPUT:
                    return outputDataArray;
                default:
                    return null;
            }
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
        public Setup[] getSetup()
        {
            return null;
        }

        @Override
        public Data[] getData(DataDirection direction)
        {
            switch (direction)
            {
                case INPUT:
                    return inputDataArray;
                case OUTPUT:
                    return outputDataArray;
                default:
                    return null;
            }
        }
    }
}
