package ve.gob.cendit.cenditlab.systems;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;
import ve.gob.cendit.cenditlab.control.System;
import ve.gob.cendit.cenditlab.control.Task;
import ve.gob.cendit.cenditlab.control.TaskContext;
import ve.gob.cendit.cenditlab.data.*;

import java.util.ArrayList;
import java.util.List;


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

    private List<Setup> setupList = new ArrayList<>();

    public AttenuatorSwitchDriver11713System()
    {
        super(systemDescriptor);

        setupList.add(new FrequencySetup());
        setupList.add(new EnrSetup());
    }

    @Override
    public ComponentDescriptor[] getTaskDescriptors()
    {
        return taskDescriptors;
    }

    @Override
    public List<Setup> getSetup()
    {
        return setupList;
    }

    @Override
    public List<Data> getData()
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
        public List<Setup> getSetup()
        {
            return null;
        }

        @Override
        public List<Data> getData()
        {
            return null;
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
        public List<Setup> getSetup()
        {
            return null;
        }

        @Override
        public List<Data> getData()
        {
            return null;
        }
    }
}
