package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class ItemView extends IndicatorsView
{
    private static final ViewLoader viewLoader = new ViewLoader("fxml/item-view.fxml");

    @FXML
    ImageView selectedImageView;

    public ItemView(Node content)
    {
        viewLoader.load(this, this);

        setContent(content);
    }

    public void setSelected(boolean value)
    {
        selectedImageView.setVisible(value);
    }

    public boolean isSelected()
    {
        return selectedImageView.isVisible();
    }
}
