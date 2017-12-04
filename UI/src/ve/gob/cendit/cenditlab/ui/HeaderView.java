package ve.gob.cendit.cenditlab.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class HeaderView extends GridPane
{
    private static final String FXML_URL = "fxml/header-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    ImageView iconImageView;

    @FXML
    Label captionLabel;

    public HeaderView()
    {
        viewLoader.load(this, this);
    }

    public HeaderView(String caption, Image icon)
    {
        this();
        setCaption(caption);
        setIcon(icon);
    }

    public void setCaption(String value)
    {
        captionLabel.setText(value);
    }

    public void setIcon(Image image)
    {
        if (image != null)
        {
            iconImageView.setVisible(true);
            iconImageView.setImage(image);
        }
        else
        {
            iconImageView.setVisible(false);
        }
    }

    public String getCaption()
    {
        return captionLabel.getText();
    }

    public Image getImage()
    {
        return iconImageView.getImage();
    }
}
