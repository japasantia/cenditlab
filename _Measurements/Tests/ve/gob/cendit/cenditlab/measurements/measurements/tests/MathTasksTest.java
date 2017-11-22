package ve.gob.cendit.cenditlab.measurements.measurements.tests;

import ve.gob.cendit.cenditlab.measurements.ITask2;
import ve.gob.cendit.cenditlab.measurements.MathTasks;
import ve.gob.cendit.cenditlab.measurements.TaskData;
import ve.gob.cendit.cenditlab.measurements.Value;

public class MathTasksTest
{
    public static void main(String[] args)
    {
        ITask2 mathTask = MathTasks.sumTask;

        mathTask.getInputData().getValues().get(0).set("1.2, 3.5, 6.7, 8.9, 10.4");
        mathTask.Execute();

        Value resultValue = new Value("Result");
        resultValue.set(mathTask.getOutputData().getValues().get(0));

        printTaskData(mathTask.getInputData());
        printTaskData(mathTask.getOutputData());

        System.out.print(String.format("%s: %s", resultValue.getName(), resultValue.get().toString()));
    }

    private static void printTaskData(TaskData taskData)
    {
        System.out.println(String.format("TaskData from: %s", taskData.getOwner().getName()));

        taskData.getValues().stream()
            .forEach(value ->
                    System.out.println(String.format("%s: %s", value.getName(), value.get())));
    }
}
