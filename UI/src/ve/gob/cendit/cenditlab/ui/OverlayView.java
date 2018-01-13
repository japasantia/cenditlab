package ve.gob.cendit.cenditlab.ui;

import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class OverlayView extends StackPane
{
    private static final ViewLoader viewLoader = new ViewLoader("fxml/overlay-view.fxml");

    @FXML
    private Pane innerPane;

    public OverlayView()
    {
        viewLoader.load(this, this);
    }

    public OverlayView(Node content)
    {
        this();

        add(content, Position.CENTER, Position.CENTER, 0.0, 0.0);
    }

    public void setContent(Node content)
    {
        innerPane.getChildren().add(content);
    }

    public Node getContent()
    {
        return innerPane.getChildren().get(0);
    }

    public void add(Node node, Position xPosition, Position yPosition)
    {
        add(node, xPosition, yPosition, 0.0, 0.0);
    }

    public void add(Node node, Position xPosition, Position yPosition,
                    double xOffset, double yOffset)
    {

        positionNode(node, xPosition, yPosition, xOffset, yOffset);

        getChildren().add(node);
    }

    public void remove(Node node)
    {
        getChildren().remove(node);
    }

    public void clear()
    {
        getChildren().clear();
    }
    
    private void positionNode(Node node, Position xPosition, Position yPosition,
                              double xOffset, double yOffset)
    {
        switch (xPosition)
        {
            case CENTER:
                setXPropertyToCenter(node, xOffset);
                break;
            case LEFT:
                setXPropertyToLeft(node, xOffset);
                break;
            case RIGHT:
                setXPropertyToRight(node, xOffset);
                break;
        }

        switch (yPosition)
        {
            case CENTER:
                setYPropertyToCenter(node, yOffset);
                break;
            case TOP:
                setYPropertyToTop(node, yOffset);
                break;
            case BOTTOM:
                setYPropertyToBottom(node, yOffset);
                break;
        }   
    }

    private void setXPropertyToCenter(Node node, double xOffset)
    {
        // node.setLayoutX(this.getWidth() / 2.0 + xOffset);
        node.translateXProperty().unbind();
        node.translateXProperty().bind(this.widthProperty().divide(2).add(xOffset));
    }

    private void setXPropertyToLeft(Node node, double xOffset)
    {
        // AnchorPane.setLeftAnchor(node, xOffset);
        // node.setLayoutX(xOffset);
        node.translateXProperty().unbind();
        node.translateXProperty().set(0.0);
    }

    private void setXPropertyToRight(Node node, double xOffset)
    {
        // AnchorPane.setRightAnchor(node, xOffset);
        // node.setLayoutX(this.getWidth() + xOffset);
        node.translateXProperty().unbind();
        node.translateXProperty().bind(this.widthProperty().add(xOffset));
    }

    private void setYPropertyToCenter(Node node, double yOffset)
    {
        // node.setLayoutY(this.getHeight() / 2.0 + yOffset);
        node.translateYProperty().unbind();
        node.translateYProperty().bind(this.heightProperty().divide(2).add(yOffset));
    }

    private void setYPropertyToTop(Node node, double yOffset)
    {
        // AnchorPane.setTopAnchor(node, yOffset);
        // node.setLayoutY(yOffset);
        node.translateYProperty().unbind();
        node.translateYProperty().set(0.0);
    }

    private void setYPropertyToBottom(Node node, double yOffset)
    {
        // AnchorPane.setBottomAnchor(node, yOffset);
        // node.setLayoutY(this.getHeight() + yOffset);
        node.translateYProperty().unbind();
        node.translateYProperty().bind(this.heightProperty().add(yOffset));
    }

    public class OverlayItem
    {
        private Position xPosition;
        private Position yPosition;
        
        public OverlayItem(@NamedArg("child") Node child, 
                           @NamedArg("xPosition") Position xPosition,
                           @NamedArg("yPosition") Position yPosition,
                           @NamedArg("xOffset") Double xOffset,
                           @NamedArg("yOffset") Double yOffset)
        {
            positionNode(child, xPosition, yPosition, 
                    xOffset, yOffset);
        }
    }
    
    public enum Position
    {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        CENTER
    }
}
