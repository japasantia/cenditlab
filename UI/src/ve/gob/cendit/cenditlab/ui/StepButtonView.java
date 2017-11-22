package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ve.gob.cendit.cenditlab.control.MeasurementStep;


public class StepButtonView extends ToggleButton
{
    private static final String FXML_URL = "fxml/step-button-view.fxml";

     @FXML
    ImageView iconImageView;

    private MeasurementStep measurementStep;

    public StepButtonView()
    {
        ViewLoader.load(FXML_URL, this, this);
    }

    public StepButtonView(MeasurementStep step)
    {
        ViewLoader.load(FXML_URL, this, this);

        setMeasurementStep(step);
    }

    public StepButtonView(String caption, Image image)
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

    public void setMeasurementStep(MeasurementStep step)
    {
        if (step == null)
        {
            throw new IllegalArgumentException("step must not be null");
        }

        measurementStep = step;

        this.setText(measurementStep.getName());
    }

    public MeasurementStep getMeasurementStep()
    {
        return measurementStep;
    }

    @Override
    public String toString()
    {
        return String.format("StepButtonView %s", this.getText());
    }
}
