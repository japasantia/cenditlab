package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.control.Component;

public class ComponentIconView extends GridPane
{
    private static final String FXML_URL = "fxml/component-icon-view.fxml";

    private static final ViewLoader viewLoader =
            new ViewLoader(FXML_URL);

    @FXML
    private ImageView componentImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView selectedImageLabel;

    private Component component;

    public ComponentIconView()
    {
        viewLoader.load(this, this);
    }

    public ComponentIconView(Component component)
    {
        this();

        setComponent(component);
    }

    public ComponentIconView(String name, String description)
    {
        this();

        setName(name);
        setDescription(description);
    }


    public void setComponent(Component component)
    {
        // TODO: check for null component (exception?)
        if (component == null)
        {
            throw new IllegalArgumentException("component must not be null");
        }

        setName(component.getName());
        setDescription(component.getDescription());
    }

    public Component getComponent()
    {
        return component;
    }

    public void setName(String value)
    {
        nameLabel.setText(value);
    }

    public void setDescription(String value)
    {
        descriptionLabel.setText(value);
    }

    public void setSelected(boolean value)
    {
        selectedImageLabel.setVisible(value);
    }

    public boolean getSelected()
    {
        return selectedImageLabel.isVisible();
    }

    public void toggleSelected()
    {
        selectedImageLabel.setVisible( ! selectedImageLabel.isVisible() );
    }
}
