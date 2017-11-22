package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class HeaderContainerView extends TitledPane
{
    private static final String FXML_URL = "fxml/header-container-view.fxml";

    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

    @FXML
    private HBox topHBox;

    @FXML
    private Pane centerPane;

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

    public void addTop(Node... nodes)
    {
        topHBox.getChildren().addAll(nodes);
    }


    public void removeTop(Node... nodes)
    {
        topHBox.getChildren().addAll(nodes);
    }

    public void setCenter(Node node)
    {
        clearCenter();
        centerPane.getChildren().add(node);
    }

    public Node getCenter()
    {
        return centerPane.getChildren().get(0);
    }

    public void clearTop()
    {
        topHBox.getChildren().clear();
    }

    public void clearCenter()
    {
        centerPane.getChildren().clear();
    }

    public void setTopVisible(boolean value)
    {
        topHBox.setVisible(value);
    }

    public void setCenterVisible(boolean value)
    {
        centerPane.setVisible(value);
    }
}
