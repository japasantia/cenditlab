package ve.gob.cendit.cenditlab.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import ve.gob.cendit.cenditlab.data.EnrData;
import ve.gob.cendit.cenditlab.data.Options;
import ve.gob.cendit.cenditlab.data.TemperatureData;

public class EnrSpotSetupView extends GridPane
{
    private static final ViewLoader viewLoader = new ViewLoader("fxml/enr-spot-setup-view.fxml");

    @FXML
    private OptionsView spotModeOptionsView;

    @FXML
    private ValueView spotEnrValueView;

    private Options spotModeOptions;

    private EnrData spotEnrData;
    private TemperatureData spotEnrTemperatureData;

    public EnrSpotSetupView()
    {
        viewLoader.load(this, this);

        setSpotEnrData(new TemperatureData());
        setSpotEnrData(new EnrData());

        spotModeOptionsView.valueProperty()
            .addListener((observable, oldValue, newValue) ->
                    changeSpotEnrValueView(newValue));
    }

    public void setSpotModeOptions(Options options)
    {
        spotModeOptions = options;

        spotModeOptionsView.setOptions(options);
    }

    public Options getSpotModeOptions()
    {
        return spotModeOptions;
    }

    public void setSpotEnrData(EnrData data)
    {
        spotEnrData = data;
        spotEnrValueView.setData(data);
    }

    public void setSpotEnrData(TemperatureData data)
    {
        spotEnrTemperatureData = data;
        spotEnrValueView.setData(data);
    }

    private void changeSpotEnrValueView(String spotMode)
    {
        if (spotMode == null)
            return;

        switch (spotMode)
        {
            case "ENR":
                setSpotEnrData(spotEnrData);
                break;

            case "Thot":
                setSpotEnrData(spotEnrTemperatureData);
                break;
        }
    }
}
