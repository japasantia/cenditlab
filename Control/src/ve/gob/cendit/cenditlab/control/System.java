package ve.gob.cendit.cenditlab.control;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.data.FieldsContainer;

public abstract class System extends Component
{
    public System(String name, String description, Image iconImage)
    {
        super(name, description, iconImage);
    }

    public abstract FieldsContainer getSetupFields();
    public abstract Task[] getTasks();
}
