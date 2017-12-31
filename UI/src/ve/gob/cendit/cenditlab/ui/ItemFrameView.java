package ve.gob.cendit.cenditlab.ui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;


public class ItemFrameView extends StackPane
{
    private static final ViewLoader viewLoader = new ViewLoader("fxml/item-frame-view.fxml");

    public static final int ADD_BUTTON = 0;
    public static final int REMOVE_BUTTON = 1;
    public static final int SELECTED_ICON = 2;

    private static final Image addIconImage = new Image(ItemFrameView.class.getResource("images/add-icon.png").toExternalForm());
    private static final Image deleteIconImage = new Image(ItemFrameView.class.getResource("images/delete-icon.png").toExternalForm());

    @FXML
    private ImageButton actionImageButton;

    @FXML
    private ImageView iconImageView;

    private Node contentNode;

    public ItemFrameView()
    {
        viewLoader.load(this, this);
    }

    public ItemFrameView(Node content)
    {
        this();

        setContent(content);
    }

    public void setContent(Node content)
    {
        contentNode = content;
        getChildren().add(0, contentNode);
    }

    public void removeContent()
    {
        getChildren().remove(contentNode);
    }

    public void showButton(int button)
    {
        switch(button)
        {
            case ADD_BUTTON:
                actionImageButton.setImage(addIconImage);
                break;
            case REMOVE_BUTTON:
                actionImageButton.setImage(deleteIconImage);
                break;
        }

        actionImageButton.setVisible(true);
    }

    public void hideButton()
    {
        actionImageButton.setVisible(false);
    }

    public void showIcon(int icon)
    {
        /*
        switch (icon)
        {
            case SELECTED_ICON:

                break;
        }
        */

        iconImageView.setVisible(true);
    }

    public void hideIcon()
    {
        iconImageView.setVisible(false);
    }

    public void setOnButtonClicked(EventHandler<MouseEvent> handler)
    {
        actionImageButton.setOnMouseClicked(handler);
    }
}
