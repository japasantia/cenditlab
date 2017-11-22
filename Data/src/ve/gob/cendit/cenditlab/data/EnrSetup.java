package ve.gob.cendit.cenditlab.data;

public class EnrSetup
{
    private Options enrModeOptions =
            new Options("Modo ENR", "Tabla", "Spot");

    private Options spotModeOptions =
            new Options("Modo Spot", "ENR", "Thot");

    private Options commonEnrTableOptions =
            new Options("Tabla comun", "ON", "OFF");

    private Options snsTcoldOptions =
            new Options("SNS Tcold", "ON", "OFF");

    private Options userTcoldOptions =
            new Options("Tcold usuario", "ON", "OFF");

    private Options noiseSourcePreferenceOptions =
            new Options("Fuente de ruido", "Normal", "SNS");

    private Options autoLoadEnrOptions =
            new Options("Carga automatica ENR", "ON", "OFF");

    private EnrField enrField =
            new EnrField();

    private TemperatureField userTcoldField =
            new TemperatureField();

    public Options getEnrModeOptions()
    {
        return enrModeOptions;
    }

    public Options getSpotModeOptions() { return spotModeOptions; }

    public Options getCommonEnrTableOptions()
    {
        return commonEnrTableOptions;
    }

    public Options getSnsTcoldOptions()
    {
        return snsTcoldOptions;
    }

    public Options getUserTcoldOptions()
    {
        return userTcoldOptions;
    }

    public Options getNoiseSourcePreferenceOptions()
    {
        return noiseSourcePreferenceOptions;
    }

    public Options getAutoLoadEnrOptions()
    {
        return autoLoadEnrOptions;
    }

    public EnrField getEnrField()
    {
        return enrField;
    }

    public TemperatureField getUserTcoldField()
    {
        return userTcoldField;
    }

    @Override
    public String toString()
    {
        return String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s",
                getEnrModeOptions(),
                getSpotModeOptions(),
                getCommonEnrTableOptions(),
                getSnsTcoldOptions(),
                getUserTcoldOptions(),
                getNoiseSourcePreferenceOptions(),
                getAutoLoadEnrOptions(),
                getEnrField(),
                getUserTcoldField());
    }
}
