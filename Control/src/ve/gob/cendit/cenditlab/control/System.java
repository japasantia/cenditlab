package ve.gob.cendit.cenditlab.control;

import javafx.scene.image.Image;
import ve.gob.cendit.cenditlab.data.DataContainer;

public abstract class System extends Component
{
    public System(String name, String description, Image iconImage)
    {
        super(name, description, iconImage);
    }

    public abstract DataContainer getSetupData();
    public abstract Task[] getTasks();
}
