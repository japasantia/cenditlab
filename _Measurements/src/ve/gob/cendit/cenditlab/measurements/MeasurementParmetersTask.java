package ve.gob.cendit.cenditlab.measurements;

import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.*;


public class MeasurementParmetersTask implements ITask
{
    private static final ScpiCommand[] COMMANDS=
    {
        new ScpiCommand(":INITIATE:IMMEDIATE"),
        new ScpiCommand("*WAI"),
        new ScpiCommand(":FETCH:ARRAY:CORRECTED:NFIGURE?[nfc-array]"),
        //new ScpiCommand("*WAI"),
        new ScpiCommand(":FETCH:ARRAY:CORRECTED:GAIN?[gc-array]"),
        //new ScpiCommand("*WAI"),
        new ScpiCommand(":FETCH:ARRAY:UNCORRECTED:PHOT?[photu-array]"),
        //new ScpiCommand("*WAI"),
        new ScpiCommand(":TRACE:DATA:CORRECTED:AMPLITUDE:VALUE? NFIGURE, (freq)[nf]"),
        new ScpiCommand(":TRACE:DATA:CORRECTED:AMPLITUDE:MAXIMUM? GAIN[max-gain]"),
        new ScpiCommand(":TRACE:DATA:CORRECTED:AMPLITUDE:DELTA? GAIN, (fmin)Hz, (fmax)Hz[delta-gain]")
    };

    private IConnection connection;
    private VariablesBundle variablesBundle;
    private List<ITaskListener> taskListeners;

    public MeasurementParmetersTask(IConnection connection,
               VariablesBundle bundle)
    {
        this.connection = connection;
        this.variablesBundle = bundle;

        setVariablesToCommands();
    }

    private void setVariablesToCommands()
    {
        for (ScpiCommand command : COMMANDS)
        {
            command.setVariables(variablesBundle);
        }
    }


    @Override
    public VariablesBundle variables()
    {
        return variablesBundle;
    }

    @Override
    public List<ITask> subTasks()
    {
        return null;
    }

    @Override
    public void addTaskListener(ITaskListener listener)
    {
        if (taskListeners == null)
        {
            taskListeners = new LinkedList<ITaskListener>();
        }
        taskListeners.add(listener);
    }

    @Override
    public void execute()
    {
        try
        {
            if (taskListeners != null)
            {
                taskListeners.stream()
                        .forEach(tl -> tl.onEntry(this));
            }

            for (ScpiCommand command : COMMANDS)
            {
                if (!command.isWellFormed())
                {
                    // TODO: verificar reporte de error ejecución
                    taskListeners.stream()
                            .forEach( tl -> tl.onError(this) );
                    // TODO: establecer el tipo de excepcion
                    throw new TaskExecutionException("bad formed scpi command");
                }

                String stringCommand = command.getCommand();
                connection.write(stringCommand);
                Thread.sleep(2000);

                if (command.hasOutputVariable())
                {
                    String response = connection.read();
                    Variable outVariable =
                            variablesBundle.get(command.getOutputVariableName());
                    if (outVariable != null)
                    {
                        outVariable.setValue(response);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
