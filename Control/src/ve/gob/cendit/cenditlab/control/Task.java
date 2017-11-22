package ve.gob.cendit.cenditlab.control;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.data.Field;
import ve.gob.cendit.cenditlab.data.FieldsContainer;

import java.util.List;

public abstract class Task extends Component
{
    public Task(String name, String description, Image iconImage)
    {
        super(name, description, iconImage);
    }

    public abstract void execute();

    public abstract FieldsContainer getSetupFields();
}
