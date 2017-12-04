package ve.gob.cendit.cenditlab.data;

import java.util.List;

public class FrequencySetup
{
    public static final int FREQUENCY_SWEEP = 0;
    public static final int FREQUENCY_LIST = 1;
    public static final int FREQUENCY_FIXED = 2;

    public static final int BANDWIDTH_100KHZ = 0;
    public static final int BANDWIDTH_200KHZ = 1;
    public static final int BANDWIDTH_400KHZ = 2;
    public static final int BANDWIDTH_1MHZ = 3;
    public static final int BANDWIDTH_2MHZ = 4;
    public static final int BANDWIDTH_4MHZ = 5;

    public static final int AVERAGE_POINT = 0;
    public static final int AVERAGE_SWEEP = 1;

    private Options frequencyModeOptions =
            new Options("Frequency Mode", "Sweep", "List", "Fixed");

    private final Options bandwidthOptions =
            new Options("Bandwidth", "100kHz", "200kHz", "400kHz", "1MHz", "2MHz" ,"4MHz");

    private final Options averageOptions =
            new Options("Average", "ON", "OFF");

    private final Options averageMode =
            new Options("Average", "Point", "Sweep");

    private final FrequencyData maxFrequencyData =
            new FrequencyData();

    private final FrequencyData minFrequencyData =
            new FrequencyData();

    private final FrequencyData centralFrequencyData =
            new FrequencyData();

    private final FrequencyData spanFrequencyData =
            new FrequencyData();

    private final NumericData averagePointsNumericData =
            new NumericData();

    private final FrequencyData fixedFrequencyData =
            new FrequencyData();

    private List<FrequencyData> frequencyList;


    public String getFrequencyMode()
    {
        return frequencyModeOptions.getSelected();
    }

    public Options getFrequencyModeOptions()
    {
        return frequencyModeOptions;
    }

    public Options getAverageOptions()
    {
        return averageOptions;
    }

    public Options getBandwidthOptions()
    {
        return bandwidthOptions;
    }

    public FrequencyData getMaxFrequencyData()
    {
        return maxFrequencyData;
    }

    public FrequencyData getMinFrequencyData()
    {
        return minFrequencyData;
    }

    public FrequencyData getCentralFrequencyData()
    {
        return centralFrequencyData;
    }

    public FrequencyData getSpanFrequencyData()
    {
        return spanFrequencyData;
    }
    
    public FrequencyData getFixedFrequencyData()
    {
        return fixedFrequencyData;
    }
    
    public NumericData getAveragePointsNumericData()
    {
        return averagePointsNumericData;
    }

    public List<FrequencyData> getFrequecyList()
    {
        return frequencyList;
    }
}