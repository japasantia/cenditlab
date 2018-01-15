package ve.gob.cendit.cenditlab.ui;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class ItemView extends OverlayView
{
    private static final ViewLoader viewLoader = new ViewLoader("fxml/item-view.fxml");

    private ImageView iconImageView;

    public ItemView()
    {
        viewLoader.load(this, this);
        initialize();
    }

    public ItemView(Node content)
    {
        viewLoader.load(this, this);

        setContent(content);

        initialize();
    }

    private void initialize()
    {
        iconImageView = new ImageView(Resources.CHECKMARK_ICON);

        add(iconImageView, Position.LEFT, Position.CENTER, 0.0, 0.0);

        setSelected(false);
    }

    public void setSelected(boolean value)
    {
        iconImageView.setVisible(value);
    }

    public boolean isSelected()
    {
        return iconImageView.isVisible();
    }
}
