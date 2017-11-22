package ve.gob.cendit.cenditlab.measurements;

import java.util.Arrays;

/**
 * Created by jarias on 17/08/17.
 */
public class MathTasks
{
    public static ITask2 sumTask = new SumTask();
}

class SumTask implements ITask2
{
    private static String TASK_NAME = "Sum Task";

    private TaskData inputData;
    private TaskData outputData;

    public SumTask()
    {
        inputData = TaskData.fromNames(this, "Input Array");
        outputData = TaskData.fromNames(this, "Output Array");
    }

    @Override
    public String getName()
    {
        return TASK_NAME;
    }

    @Override
    public TaskData getInputData()
    {
        return inputData;
    }

    @Override
    public TaskData getOutputData()
    {
        return outputData;
    }

    @Override
    public void Execute()
    {
        Value input = inputData.getValues().get(0);
        Value output = outputData.getValues().get(0);

        String[] inputArray = input.get().toString().split(",");
        float sum = 0.0f;

        for (String data : inputArray)
        {
            try
            {
                sum += Float.parseFloat(data);
            }
            catch (Exception ex)
            {}
        }

        output.set(sum);
    }
}

