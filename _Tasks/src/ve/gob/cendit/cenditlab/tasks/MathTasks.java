package ve.gob.cendit.cenditlab.tasks;

import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.data.DataSet;

/**
 * Created by root on 22/08/17.
 */
public class MathTasks
{
    public static final String ADD_TASK = "Add Task";
    public static final String PRODUCT_TASK = "Product Task";

    public static ITask get(String name)
    {
        switch (name)
        {
            case MathTasks.ADD_TASK:
                return new MathTasks.AddTask();

            case MathTasks.PRODUCT_TASK:
                return new MathTasks.ProductTask();

            default:
                throw new IllegalArgumentException("Invalid task name");
        }
    }

    public static class AddTask implements ITask
    {
        private Data inputAData = new Data("Input A");
        private Data inputBData = new Data("Input B");
        private Data outputData = new Data("Output");

        private DataSet inputDataSet = new DataSet(inputAData, inputBData);
        private DataSet outputDataSet = new DataSet(outputData);

        @Override
        public String getName()
        {
            return ADD_TASK;
        }

        @Override
        public DataSet getDataSet(String category)
        {
            switch (category)
            {
                case ITask.INPUT_DATA:
                    return inputDataSet;

                case ITask.OUTPUT_DATA:
                    return outputDataSet;

                default:
                    throw new IllegalArgumentException("Invalid task data set category");
            }
        }

        @Override
        public void execute()
        {
            Number a = (Number)inputAData.get();
            Number b = (Number)inputBData.get();

            outputData.set(a.doubleValue() + b.doubleValue());
        }
    }

    public static class ProductTask implements ITask
    {
        private Data inputAData = new Data("Input A");
        private Data inputBData = new Data("Input B");
        private Data outputData = new Data("Output");

        private DataSet inputDataSet = new DataSet(inputAData, inputBData);
        private DataSet outputDataSet = new DataSet(outputData);

        @Override
        public String getName()
        {
            return PRODUCT_TASK;
        }

        @Override
        public DataSet getDataSet(String category)
        {
            switch (category)
            {
                case ITask.INPUT_DATA:
                    return inputDataSet;

                case ITask.OUTPUT_DATA:
                    return outputDataSet;

                default:
                    throw new IllegalArgumentException("Invalid task data set category");
            }
        }

        @Override
        public void execute()
        {
            Number a = (Number) inputAData.get();
            Number b = (Number) inputBData.get();

            outputData.set(a.doubleValue() * b.doubleValue());
        }
    }

}
