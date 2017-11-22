package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends Button
{
    private static final String FXML_URL = "fxml/image-button.fxml";

    @FXML
    private ImageView iconImageView;

    public ImageButton()
    {
        ViewLoader.load(FXML_URL, this, this);

        iconImageView.fitWidthProperty().bind(this.prefWidthProperty());
        iconImageView.fitHeightProperty().bind(this.prefHeightProperty());

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
