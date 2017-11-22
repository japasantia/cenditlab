package ve.gob.cendit.cenditlab.measurements;

import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.*;

public class ConfigureMeasurementTask implements ITask
{
    private static final ScpiCommand[] COMMANDS =
    {
        new ScpiCommand("*RST"),
        new ScpiCommand("*CLS"),
        new ScpiCommand(":SENSE:FREQUENCY:START (frequency-start) GHz"),
        new ScpiCommand(":SENSE:FREQUENCY:STOP (frequency-stop) GHz"),
        new ScpiCommand(":SENSE:CORRECTION:COLLECT:ACQUIRE STANDARD"),
        new ScpiCommand("*WAI"),
        new ScpiCommand(":SENSE:SWEEP:POINTS (frequency-points)"),
        new ScpiCommand(":SENSE:AVERAGE:STATE (average-state)"),
        new ScpiCommand(":SENSE:AVERAGE:COUNT (average-count)"),
        new ScpiCommand(":SENSE:BANDWIDTH (bandwidth)"),
        new ScpiCommand(":INITIATE:CONTINUOUS:ALL (continuous)"),
    };

    private IConnection connection;
    private List<ITaskListener> taskListeners;

    private VariablesBundle variablesBundle;
    private List<ScpiCommand> commands;

    public ConfigureMeasurementTask(IConnection connection,
                                    VariablesBundle variablesBundle)
    {
        this.connection = connection;
        this.variablesBundle = variablesBundle;

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
        if (listener == null)
        {
            // TODO: revisar excepcion argumento null
            throw new IllegalArgumentException("listener must not be null");
        }

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
                if ( ! command.isWellFormed() )
                {
                    // TODO: verificar reporte de error ejecución
                    taskListeners.stream()
                            .forEach(tl -> tl.onError(this));
                    // TODO: establecer el tipo de excepcion
                    throw new TaskExecutionException("bad formed scpi command");
                }

                String stringCommand = command.getCommand();
                connection.write(stringCommand);
                Thread.sleep(2000);

                if (command.hasOutputVariable())
                {
                    String response = connection.read();
                    Variable outVariable = variablesBundle.get(command.getOutputVariableName());
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
