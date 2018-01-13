package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class OverlayView extends StackPane
{
    private static final ViewLoader viewLoader = new ViewLoader("fxml/overlay-view.fxml");

    private static final int HORZ_POS_MASK = 0x0F;
    private static final int VERT_POS_MASK = 0xF0;

    private static final int CENTER = 0x00;
    private static final int LEFT = 0x01;
    private static final int RIGHT = 0x02;
    private static final int TOP = 0x10;
    private static final int BOTTOM = 0x20;

    public static final int CENTER_CENTER = CENTER | CENTER;
    public static final int CENTER_LEFT = CENTER | LEFT;
    public static final int CENTER_RIGHT = CENTER | RIGHT;

    public static final int TOP_CENTER = TOP | CENTER;
    public static final int TOP_LEFT = TOP | LEFT;
    public static final int TOP_RIGHT = TOP | RIGHT;

    public static final int BOTTOM_CENTER = BOTTOM | CENTER;
    public static final int BOTTOM_LEFT = BOTTOM | LEFT;
    public static final int BOTTOM_RIGHT = BOTTOM | RIGHT;

    @FXML
    private AnchorPane anchorPane;

    public OverlayView()
    {
        viewLoader.load(this, this);
    }

    public OverlayView(Node content)
    {
        this();

        addContent(content, CENTER_CENTER);
    }
    public void addContent(Node node, int position)
    {
        addContent(node, position, 0.0, 0.0);
    }

    public void addContent(Node node, int position, double offsetX, double offsetY)
    {
        int xPosition = HORZ_POS_MASK & position;
        int yPosition = VERT_POS_MASK & position;
        
        switch (xPosition) 
        {
            case CENTER:
                setXPropertyToCenter(node, offsetX);
                break;
            case LEFT:
                setXPropertyToLeft(node, offsetX);
                break;
            case RIGHT:
                setXPropertyToRight(node, offsetX);
                break;
        }

        switch (yPosition)
        {
            case CENTER:
                setYPropertyToCenter(node, offsetY);
                break;
            case TOP:
                setYPropertyToTop(node, offsetY);
                break;
            case BOTTOM:
                setYPropertyToBottom(node, offsetY);
                break;
        }

        getChildren().add(node);
    }

    public void removeContent(Node node)
    {
        getChildren().remove(node);
    }

    public void clearContent()
    {
        getChildren().clear();
    }

    private void setXPropertyToCenter(Node node, double offsetX)
    {
        node.translateXProperty().bind(this.widthProperty().divide(2).add(offsetX));
    }

    private void setXPropertyToLeft(Node node, double offsetX)
    {
        node.translateXProperty().set(0.0);
    }

    private void setXPropertyToRight(Node node, double offsetX)
    {
        node.translateXProperty().bind(this.widthProperty().add(offsetX));
    }

    private void setYPropertyToCenter(Node node, double offsetY)
    {
        node.translateYProperty().bind(this.heightProperty().divide(2).add(offsetY));
    }

    private void setYPropertyToTop(Node node, double offsetY)
    {
        node.translateYProperty().set(0.0);
    }

    private void setYPropertyToBottom(Node node, double offsetY)
    {
        node.translateYProperty().bind(this.heightProperty().add(node.getBoundsInParent().getHeight()).add(offsetY));
    }
}
