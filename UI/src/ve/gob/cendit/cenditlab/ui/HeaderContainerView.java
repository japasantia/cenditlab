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

public class HeaderContainerView extends TitledPane
{
    private static final String FXML_URL = "fxml/header-container-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private VBox rootVBox;

    // TODO: determionar el mejor contenedor para area top
    @FXML
    private Pane topPane;

    @FXML
    private ScrollPane centerPane;

    @FXML
    private ImageView iconImageView;

    public HeaderContainerView()
    {
        viewLoader.load(this, this);
    }

    public void setCaption(String value)
    {
        this.setText(value);
    }

    public String getCaption()
    {
        return this.getText();
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

    public void setTop(Node node)
    {
        topPane.getChildren().add(node);
    }

    public Node getTop()
    {
        return topPane.getChildren().get(0);
    }


    public void clearTop()
    {
        topPane.getChildren().clear();
    }

    public void setCenter(Node node)
    {
        clearCenter();
        centerPane.setContent(node);
    }

    public Node getCenter()
    {
        return centerPane.getContent();
    }

    public void clearCenter()
    {
        centerPane.setContent(null);
    }

    public void setTopVisible(boolean value)
    {
        topPane.setVisible(value);
    }

    public void setCenterVisible(boolean value)
    {
        centerPane.setVisible(value);
    }
}
