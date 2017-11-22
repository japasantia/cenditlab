package ve.gob.cendit.cenditlab.tests;


import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.tasks.MathTasks2;
import ve.gob.cendit.cenditlab.tasks.Task;

public class MathTasks2Test
{
    public static void main(String[] args)
    {
        Task addTask = MathTasks2.get(MathTasks2.ADD_TASK);
        Task productTask = MathTasks2.get(MathTasks2.PRODUCT_TASK);

        Data inputA = new Data("A", 10.0);
        Data inputB = new Data("B", 12.55f);
        Data out = new Data("Out", 0);

        print("AddTask slots", addTask.getSlots().getSlots());
        print("AddTask default data", addTask.getSlots());

        addTask.getSlots().setData(0, inputA);
        addTask.getSlots().setData(1, inputB);
        addTask.execute();

        print("AddTask data after execute 1", addTask.getSlots());

        addTask.getSlots().getData(0).set(45.67f);
        addTask.getSlots().getData(1).set(69.13f);
        print("AddTask data modified", addTask.getSlots());
        addTask.execute();
        print("AddTask data after execute 2", addTask.getSlots());
    }

    public static void print(String caption, Object object)
    {
        System.out.println(caption);
        System.out.println(object);
    }
}
