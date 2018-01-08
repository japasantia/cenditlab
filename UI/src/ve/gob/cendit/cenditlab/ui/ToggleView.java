package ve.gob.cendit.cenditlab.ui;

import javafx.scene.control.ToggleButton;
import ve.gob.cendit.cenditlab.data.Options;

import java.util.Objects;

public class ToggleView extends ToggleButton
{
    private static final String DEFAULT_TEXT_ON = "ON";
    private static final String DEFAULT_TEXT_OFF = "OFF";

    private String textOn = DEFAULT_TEXT_ON;
    private String textOff = DEFAULT_TEXT_OFF;

    private Options options;

    public ToggleView()
    {
        selectedProperty()
            .addListener((observable, newValue, oldValue) -> changeOption(newValue));
    }

    public void setOptions(Options options)
    {
        if (Objects.isNull(options))
        {
            return;
        }

        this.options = options;

        textOn = options.getValues().get(0);
        textOff = options.getValues().get(1);
    }

    public Options getOptions()
    {
        return options;
    }

    public String getTextOn()
    {
        return textOn;
    }

    public String getTextOff()
    {
        return textOff;
    }

    private void changeOption(Boolean selected)
    {
        String optionText = selected ? getTextOn() : getTextOff();
        setText(optionText);

        if (options != null)
        {
            options.setSelected(optionText);
        }
    }
}
