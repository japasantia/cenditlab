package ve.gob.cendit.cenditlab.tests;


import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.data.DataBinded;
import ve.gob.cendit.cenditlab.data.DataSet;
import ve.gob.cendit.cenditlab.tasks.*;

import java.lang.System;

public class MathTasksTest
{
    public static void main(String[] args)
    {
        ITask addTask = MathTasks.get(MathTasks.ADD_TASK);
        ITask productTask = new MathTasks.ProductTask();

        DataSet inputDataSet = addTask.getDataSet(ITask.INPUT_DATA);
        DataSet outputDataSet = addTask.getDataSet(ITask.OUTPUT_DATA);

        Data inputAData = new Data("A", 10.0);
        Data inputBData = new Data("B", 15);

        inputDataSet.setData("Input A", inputAData);
        inputDataSet.setData("Input B", inputBData);

        DataBinded productResult = new DataBinded("Add Result", outputDataSet.getData("Output"));

        addTask.execute();

        print("Input DataSet", inputDataSet);
        print("Output DataSet", outputDataSet);

        print("Binded Output", productResult);

        inputDataSet = productTask.getDataSet(ITask.INPUT_DATA);
        outputDataSet = productTask.getDataSet(ITask.OUTPUT_DATA);
    }

    public static void print(String caption, Object object)
    {
        System.out.println(caption);
        System.out.println(object);
    }
}
