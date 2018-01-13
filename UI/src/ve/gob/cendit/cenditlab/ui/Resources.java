package ve.gob.cendit.cenditlab.ui;

import javafx.scene.image.Image;

import java.net.URL;

public final class Resources
{
    public static final Image ADD_ICON = loadImage("images/add-icon.png");
    public static final Image CHECKMARK_ICON = loadImage("images/checkmark-icon.png");
    public static final Image DELETE_ICON = loadImage("images/delete-icon.png");

    private static Image loadImage(String name)
    {
        URL url = Resources.class.getResource(name);
        return new Image(url.toExternalForm());
    }
}
