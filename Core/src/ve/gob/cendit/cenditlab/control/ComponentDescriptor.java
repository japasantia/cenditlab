package ve.gob.cendit.cenditlab.control;

import javafx.scene.image.Image;

import java.util.Iterator;
import java.util.function.Function;

public class ComponentDescriptor
{
    private String name;
    private String description;
    private Image iconImage;

    private Function<Object, Component> componentFactory;

    public ComponentDescriptor(String name, String description, Image iconImage,
                               Function<Object, Component> componentFactory)
    {
        if (name == null || description == null)
        {
            throw new IllegalArgumentException("name and description must not be null");
        }

        if (componentFactory == null)
        {
            throw new IllegalArgumentException("componentFactory must not be null");
        }

        this.name = name;
        this.description = description;
        this.iconImage = iconImage;
        this.componentFactory = componentFactory;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Image getIcon() { return iconImage; }

    public Component create(Object arg)
    {
        return componentFactory.apply(arg);
    }
}
