package ve.gob.cendit.cenditlab.control;

import ve.gob.cendit.cenditlab.data.DataContainer;

public abstract class System extends Component
{
    public System(ComponentDescriptor descriptor)
    {
        super(descriptor);
    }

    public abstract ComponentDescriptor[] getTaskDescriptors();
}
