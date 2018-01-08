package ve.gob.cendit.cenditlab.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HeaderFrameView extends TitledPane
{
    private static final String FXML_URL = "fxml/header-frame-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private ImageView iconImageView;

    public HeaderFrameView()
    {
        viewLoader.load(this, this);
    }

    public void setIcon(Image iconImage)
    {
        if (iconImage != null)
        {
            iconImageView.setVisible(true);
            iconImageView.setImage(iconImage);
        }
        else
        {
            iconImageView.setVisible(false);
        }
    }

    public Image getIcon()
    {
        return iconImageView.getImage();
    }
}
