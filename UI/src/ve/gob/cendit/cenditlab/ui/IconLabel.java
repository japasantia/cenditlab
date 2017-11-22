package ve.gob.cendit.cenditlab.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconLabel extends Label
{
    private static final String FXML_URL = "fxml/icon-label.fxml";

    @FXML
    private ImageView iconImageView;

    public IconLabel()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    public void setImage(Image image)
    {
        if (image != null)
        {
            iconImageView.setDisable(false);
            iconImageView.setImage(image);
        }
        else
        {
            iconImageView.setDisable(true);
        }
    }

    public Image getImage()
    {
        return iconImageView.getImage();
    }
}
