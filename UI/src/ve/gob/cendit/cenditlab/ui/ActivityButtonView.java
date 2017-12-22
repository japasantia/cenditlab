package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ve.gob.cendit.cenditlab.control.Activity;


public class ActivityButtonView extends ToggleButton
{
    private static final String FXML_URL = "fxml/activity-button-view.fxml";

    private static final ViewLoader viewLoader = new ViewLoader(FXML_URL);

     @FXML
    ImageView iconImageView;

    private Activity activity;

    public ActivityButtonView()
    {
        viewLoader.load(this, this);
    }

    public ActivityButtonView(Activity activity)
    {
        ViewLoader.load(FXML_URL, this, this);

        setActivity(activity);
    }

    public ActivityButtonView(String caption, Image image)
    {
        ViewLoader.load(FXML_URL, this, this);

        this.setText((caption != null ? caption : ""));

        if (image != null)
        {
            iconImageView.setDisable(false);
            iconImageView.setImage(image);
        }
        else
        {
            iconImageView.setDisable(true);
        }
    }

    public void setActivity(Activity activity)
    {
        if (activity == null)
        {
            throw new IllegalArgumentException("activity must not be null");
        }

        this.activity = activity;

        this.setText(activity.getName());
    }

    public Activity getActivity()
    {
        return activity;
    }

    @Override
    public String toString()
    {
        return String.format("ActivityButtonView %s", this.getText());
    }
}
