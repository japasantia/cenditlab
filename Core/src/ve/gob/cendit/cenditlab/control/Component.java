package ve.gob.cendit.cenditlab.control;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.data.Setup;

import java.util.List;

public abstract class Component
{
    private ComponentDescriptor componentDescriptor;

    public Component(ComponentDescriptor descriptor)
    {
        setComponentDescriptor(descriptor);
    }

    protected void setComponentDescriptor(ComponentDescriptor descriptor)
    {
        if (descriptor == null)
        {
            throw new IllegalArgumentException("descriptor must not be null");
        }

        componentDescriptor = descriptor;
    }

    public ComponentDescriptor getComponentDescriptor()
    {
        return componentDescriptor;
    }

    public String getName()
    {
        return componentDescriptor.getName();
    }

    public String getDescription()
    {
        return componentDescriptor.getDescription();
    }

    public Image getIcon()
    {
        return componentDescriptor.getIcon();
    }

    public abstract List<Data> getData();

    public abstract List<Setup> getSetup();
}
