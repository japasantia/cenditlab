package ve.gob.cendit.cenditlab.tasks;


import ve.gob.cendit.cenditlab.io.IConnection;
import ve.gob.cendit.cenditlab.io.VisaAddress;

import java.util.List;

public abstract class Instrument
{
    final private String name;
    final private String description;

    private boolean isSimulatorMode = false;

    public Instrument(String name, String description)
    {
        if (name == null)
        {
            throw new IllegalArgumentException("Instrument's name must not be null");
        }

        if (description == null)
        {
            throw new IllegalArgumentException("Instrument's description must not be null");
        }

        this.name = name;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setSimulation(boolean value)
    {
        isSimulatorMode = value;
    }

    public boolean isSimulated()
    {
        return isSimulatorMode;
    }

    public IConnection connect(VisaAddress visaAddress)
    {
        // Hook, retorna null
        return null;
    }

    public boolean tryConnect(VisaAddress visaAddress, IConnection connection)
    {
        // Hook, retorna false;
        return false;
    }

    public abstract IConnection getConnection();

    public abstract List<Task> getTasks();
}
