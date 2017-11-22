package ve.gob.cendit.cenditlab.tasks;


import ve.gob.cendit.cenditlab.data.Data;
import ve.gob.cendit.cenditlab.data.DataDirection;

public class MathTasks2
{
    public static final String ADD_TASK = "Add Task";
    public static final String PRODUCT_TASK = "Product Task";

    public static Task get(String name)
    {
        switch (name)
        {
            case MathTasks.ADD_TASK:
                return new MathTasks2.AddTask();

            case MathTasks.PRODUCT_TASK:
                return new MathTasks2.ProductTask();

            default:
                throw new IllegalArgumentException("Invalid task name");
        }
    }

    public static class AddTask extends Task
    {
        private Data inputA = new  Data("InputA", 0.0f);
        private Data inputB = new Data("InputB", 0.0f);
        private Data output = new Data("Output", 0.0f);

        private Slot slotA = new Slot("InputA", inputA, DataDirection.INPUT);
        private Slot slotB = new Slot("InputB", inputB, DataDirection.INPUT);
        private Slot slotOut = new Slot("Output", output, DataDirection.OUTPUT);

        private final ComponentSlots componentSlots =
                new ComponentSlots(slotA, slotB, slotOut);

        public AddTask()
        {
            setName(MathTasks2.ADD_TASK);
            setSlots(componentSlots);
        }

        @Override
        public void execute()
        {
            Number a = (Number) componentSlots.getData(0).get();
            Number b = (Number) componentSlots.getData(1).get();
            componentSlots.getData(2).set(a.floatValue() + b.floatValue());
        }
    }

    public static class ProductTask extends Task
    {
        public ProductTask()
        {
            super(MathTasks2.PRODUCT_TASK,
                new Slot("InputA", new Data("InputA", 0.0)),
                new Slot("InputB", new Data("InputA", 0.0)),
                new Slot("Output", new Data("Output", 0.0), DataDirection.OUTPUT)
            );
        }

        @Override
        public void execute()
        {
            Number a = (Number) getSlots().getData(0).get();
            Number b = (Number) getSlots().getData(1).get();
            getSlots().getData(2).set(a.floatValue() * b.floatValue());
        }
    }
}
