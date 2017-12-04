package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class IconView extends VBox
{
    private static final String FXML_URL = "fxml/icon-view.fxml";
    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);


    private static final String DEFAULT_ICON = "/ve/gob/cendit/cenditlab/ui/images/system-icon.png";
    private static final Image DEFAULT_IMAGE =
            new Image(IconView.class.getResource(DEFAULT_ICON).toExternalForm());
    private static final String DEFAULT_CAPTION = "";

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label captionLabel;

    public IconView()
    {
        this(DEFAULT_CAPTION, DEFAULT_IMAGE);
    }

    public IconView(String caption, Image image)
    {
        viewLoader.load(this, this);

        setCaption(caption);
        setIcon(image);
    }

    public void setCaption(String caption)
    {
        caption = (caption != null ? caption : DEFAULT_CAPTION);
        captionLabel.setText(caption);
    }

    public void setIcon(Image image)
    {
        image = (image != null ? image : DEFAULT_IMAGE);
        iconImageView.setImage(image);
    }

    public Image getIcon()
    {
        return iconImageView.getImage();
    }
}
