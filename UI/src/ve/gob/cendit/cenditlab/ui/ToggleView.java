package ve.gob.cendit.cenditlab.ui;

import javafx.scene.control.ToggleButton;

public class ToggleView extends ToggleButton
{
    private static final String DEFAULT_TEXT_ON = "ON";
    private static final String DEFAULT_TEXT_OFF = "OFF";

    private String textOn = DEFAULT_TEXT_ON;
    private String textOff = DEFAULT_TEXT_OFF;

    public ToggleView()
    {
        selectedProperty()
            .addListener((observable, newValue, oldValue) ->
                setText(newValue ? getTextOn() : getTextOff()));
    }

    public void setTextOn(String value)
    {
        textOn = value;
    }

    public String getTextOn()
    {
        return textOn;
    }

    public void setTextOff(String value)
    {
        textOff = value;
    }

    public String getTextOff()
    {
        return textOff;
    }


}
