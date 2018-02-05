package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ve.gob.cendit.cenditlab.control.ComponentDescriptor;

public class IconView extends HBox
{
    private static final String FXML_URL = "fxml/icon-view.fxml";
    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

    @FXML
    private Label nameLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private ImageView iconImageView;


    public IconView()
    {
        viewLoader.load(this, this);
    }

    public IconView(ComponentDescriptor descriptor)
    {
        this();

        setComponentDescriptor(descriptor);
    }

    public void setComponentDescriptor(ComponentDescriptor descriptor)
    {
        setName(descriptor.getName());
        setDescription(descriptor.getDescription());
        setIcon(descriptor.getIcon());
    }

    public IconView(String name, String description, Image image)
    {
        viewLoader.load(this, this);

        setName(name);
        setIcon(image);
    }

    public void setName(String value)
    {
        setLabelText(nameLabel, value);
    }

    public String getName()
    {
        return nameLabel.getText();
    }

    public void setDescription(String value)
    {
        setLabelText(descriptionLabel, value);
    }

    public String getDescription()
    {
        return descriptionLabel.getText();
    }

    public void setIcon(Image image)
    {
        if (image != null)
        {
            iconImageView.setVisible(true);
            iconImageView.setImage(image);
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

    private void setLabelText(Label label, String text)
    {
        if (text != null)
        {
            label.setVisible(true);
            label.setText(text);
        }
        else
        {
            label.setVisible(false);
        }
    }
}
