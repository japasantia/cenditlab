package ve.gob.cendit.cenditlab.control;

import javafx.scene.Node;
import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.ui.base.ViewType;

public abstract class Component
{
    private String name;
    private String description;
    private Image iconImage;

    public Component(String name, String description, Image iconImage)
    {
        if (name == null || description == null)
        {
            throw new IllegalArgumentException("name and description must not be null");
        }

        this.name = name;
        this.description = description;
        this.iconImage = iconImage;
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

    public abstract Node getView(ViewType viewType);
}
