package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HeaderContainerView extends TitledPane
{
    private static final String FXML_URL = "fxml/header-container-view.fxml";

    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

    @FXML
    private HBox topHBox;

    @FXML
    private ScrollPane centerScrollPane;

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

    public void addTop(Node... nodes)
    {
        topHBox.getChildren().addAll(nodes);
    }


    public void removeTop(Node... nodes)
    {
        topHBox.getChildren().addAll(nodes);
    }

    public void setCenterContent(Node node)
    {
        centerScrollPane.setContent(node);
    }

    public Node getCenterContent ()
    {
        return centerScrollPane.getContent();
    }

    public void clearTop()
    {
        topHBox.getChildren().clear();
    }

    public void clearCenter()
    {
        centerScrollPane.setContent(null);
    }

    public void setTopVisible(boolean value)
    {
        topHBox.setVisible(value);
    }

    public void setCenterVisible(boolean value)
    {
        centerScrollPane.setVisible(value);
    }
}
