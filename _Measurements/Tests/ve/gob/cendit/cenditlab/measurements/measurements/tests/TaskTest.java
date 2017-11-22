package ve.gob.cendit.cenditlab.measurements.measurements.tests;

import ve.gob.cendit.cenditlab.io.GpibConnection;
import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.measurements.*;

/**
 * Created by jarias on 21/07/17.
 */
public class TaskTest
{
    public static void main(String[] args)
    {
        try
        {
            IConnection connection =
                    GpibConnection.CreateConnection("GPIB0::10");
            VariablesBundle bundle = new VariablesBundle();
            // Variables entrada
            bundle.add("frequency-start", new Variable("FreqStart", 1.0));
            bundle.add("frequency-stop", new Variable("FreqStop", 2.5));
            bundle.add("frequency-points", new Variable("FreqPoints", 10));
            bundle.add("average-state", new Variable("Average", "ON"));
            bundle.add("average-count", new Variable("Count", 1));
            bundle.add("bandwidth", new Variable("Bandwidth", 100000));
            bundle.add("continuous", new Variable("Continuous", "OFF"));
            bundle.add("freq", new Variable("Freq", 1.2e9));
            bundle.add("fmin", new Variable("FMin", 50000000));
            bundle.add("fmax", new Variable("FMax", 150000000));
            // Variables salida
            bundle.add("nfc-array", new Variable("NF[]", null));
            bundle.add("gc-array", new Variable("GC[]", null));
            bundle.add("photu-array", new Variable("PHot", null));
            bundle.add("nf", new Variable("NF", null));
            bundle.add("max-gain", new Variable("GMax", null));
            bundle.add("delta-gain", new Variable("DeltaG", null));


            ConfigureMeasurementTask configureTask =
                    new ConfigureMeasurementTask(connection, bundle);
            MeasurementParmetersTask measurementTask =
                    new MeasurementParmetersTask(connection, bundle);

            ITaskListener taskListener = new ITaskListener()
            {
                @Override
                public void onEntry(ITask task)
                {
                    System.out.printf("Task <%s> entry\n",
                            task.toString());
                }

                @Override
                public void onExit(ITask task)
                {
                    System.out.printf("Task <%s> exit\n",
                            task.toString());
                }

                @Override
                public void onError(ITask task)
                {
                    System.out.printf("Task <%s> error\n",
                            task.toString());
                }

                @Override
                public void executed(Task task)
                {

                }
            };

            configureTask.addTaskListener(taskListener);
            measurementTask.addTaskListener(taskListener);

            connection.open();
            connection.write("*IDN?");
            String id = connection.read();

            configureTask.execute();
            Thread.sleep(4000);
            measurementTask.execute();

            connection.close();

            printVariables("Variables after execution", bundle);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void printVariables(String message, VariablesBundle bundle)
    {
        System.out.println(message);

        bundle.variables().stream()
            .forEach( v -> System.out.printf( "%s = %s\n", v.getName(), v.getValue() ) );
    }
}
