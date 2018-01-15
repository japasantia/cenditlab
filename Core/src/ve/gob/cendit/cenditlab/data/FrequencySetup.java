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

    private Options bandwidthOptions =
            new Options("Bandwidth", "100kHz", "200kHz", "400kHz", "1MHz", "2MHz" ,"4MHz");

    private Options averageOptions =
            new Options("Average", "ON", "OFF");

    private Options averageMode =
            new Options("Average", "Point", "Sweep");

    private FrequencyData maxFrequencyData =
            new FrequencyData();

    private FrequencyData minFrequencyData =
            new FrequencyData();

    private FrequencyData centralFrequencyData =
            new FrequencyData();

    private FrequencyData spanFrequencyData =
            new FrequencyData();

    private NumericData averagePointsNumericData =
            new NumericData();

    private FrequencyData fixedFrequencyData =
            new FrequencyData();

    private List<FrequencyData> frequencyList;

    private boolean updateEnabled;

    public FrequencySetup()
    {
        updateEnabled = true;

        minFrequencyData.addUpdateListener(source -> minMaxFrequenciesUpdate());
        maxFrequencyData.addUpdateListener(source -> minMaxFrequenciesUpdate());

        centralFrequencyData.addUpdateListener(source -> centralSpanFrequenciesUpdate());
        spanFrequencyData.addUpdateListener(source -> centralSpanFrequenciesUpdate());
    }

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

    public List<FrequencyData> getFrequecyDataList()
    {
        return frequencyList;
    }

    public void setUpdateEnabled(boolean value)
    {
        updateEnabled = value;
    }

    public boolean isUpdateEnabled()
    {
        return updateEnabled;
    }

    private void minMaxFrequenciesUpdate()
    {
        if ( ! isUpdateEnabled() )
        {
            return;
        }

        setUpdateEnabled(false);

        float centralFrequency =
                (maxFrequencyData.getMagnitude() + minFrequencyData.getMagnitude()) / 2.0f;

        float spanFrequency =
                (maxFrequencyData.getMagnitude() - minFrequencyData.getMagnitude());

        centralFrequencyData.setMagnitude(centralFrequency);
        spanFrequencyData.setMagnitude(spanFrequency);

        setUpdateEnabled(true);
    }

    private void centralSpanFrequenciesUpdate()
    {
        if ( ! isUpdateEnabled() )
        {
            return;
        }

        setUpdateEnabled(false);

        float maxFrequency =
                centralFrequencyData.getMagnitude() + spanFrequencyData.getMagnitude() / 2.0f;
        float minFrequency =
                centralFrequencyData.getMagnitude() - spanFrequencyData.getMagnitude() / 2.0f;

        maxFrequencyData.setMagnitude(maxFrequency);
        minFrequencyData.setMagnitude(minFrequency);

        setUpdateEnabled(true);
    }
}