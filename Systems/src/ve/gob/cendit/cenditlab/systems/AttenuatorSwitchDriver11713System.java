package ve.gob.cendit.cenditlab.systems;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.control.*;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.data.*;


public class AttenuatorSwitchDriver11713System extends System
{
    private static final String NAME = "Attenuator and Switch Driver 11713";
    private static final String DESCRIPTION = "Attenuator and switch controller and driver";

    private static final Image ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975ASystem.class.getResource("/ve/gob/cendit/cenditlab/ui/images/asd11713c.jpg").toExternalForm());

    private static final Image TASK_ICON_IMAGE =
            new Image(NoiseFigureAnalyzer8975ASystem.class.getResource("/ve/gob/cendit/cenditlab/ui/images/task-icon.jpg").toExternalForm());

    private static final ComponentDescriptor systemDescriptor = new ComponentDescriptor(
        NAME,
        DESCRIPTION,
        ICON_IMAGE,
        arg -> new AttenuatorSwitchDriver11713System());

    private static final ComponentDescriptor[] taskDescriptors =
        {
            SetSwitchStateTask.descriptor,
            SetAttenuationTask.descriptor
        };

    private static final Setup[] setupArray =
        { new FrequencySetup(), new EnrSetup() };

    private static final Data[] inputDataArray =
        { new ValueData(), new FrequencyData(), new EnrData(), new ListData("List Data", "0,1,2,3,4,5,6,7,8,9") };

    private static final Data[] outputDataArray =
        { new GraphData("Graph", "[0,1,2,3,4,5,6,7,8,9][9,8,7,6,5,4,3,2,1,0]") };

    public AttenuatorSwitchDriver11713System()
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

    private static class SetAttenuationTask extends Task
    {
        private static final ComponentDescriptor descriptor = new ComponentDescriptor(
                "Attenuation Setter",
                "Sets attenuation on the device",
                TASK_ICON_IMAGE,
                arg -> new SetAttenuationTask());

        public SetAttenuationTask()
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

    private static final class SetSwitchStateTask extends Task
    {
        public static final ComponentDescriptor descriptor = new ComponentDescriptor(
                "Change Switch State",
                "Change switch state on selected switch",
                TASK_ICON_IMAGE,
                arg -> new SetSwitchStateTask());

        public SetSwitchStateTask()
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
