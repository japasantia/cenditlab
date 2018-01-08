package ve.gob.cendit.cenditlab.ui;

import javafx.scene.control.ChoiceBox;
import ve.gob.cendit.cenditlab.data.Options;

public class OptionsView extends ChoiceBox<String>
{
    private Options options;

    public OptionsView()
    {
        valueProperty()
                .addListener((observable, oldValue, newValue) -> changeOption(newValue));
    }

    public OptionsView(Options options)
    {
        this();

        setOptions(options);
    }

    public void setOptions(Options options)
    {
        this.options = options;

        getItems().setAll(options.getValues());

        setValue(options.getDefault());
    }

    public Options getOptions()
    {
        return options;
    }

    private void changeOption(String option)
    {
        if (options != null)
        {
            options.setSelected(option);
        }
    }
}
