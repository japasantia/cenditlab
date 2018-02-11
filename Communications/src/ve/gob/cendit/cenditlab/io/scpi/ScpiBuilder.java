package ve.gob.cendit.cenditlab.io.scpi;

import ve.gob.cendit.cenditlab.data.Data;

import java.util.Objects;

public class ScpiBuilder
{
    private Scpi scpi;

    protected ScpiBuilder(String command)
    {
        scpi = new Scpi(command);
    }

    public ScpiBuilder in(Data... inArgs)
    {
        scpi.setArguments(inArgs);

        return this;
    }

    public ScpiBuilder out(Data... outArgs)
    {
        scpi.setResults(outArgs);

        return this;
    }

    public ScpiBuilder delay(long value)
    {
        scpi.setDelay(value);

        return this;
    }

    public ScpiBuilder readMode(int value)
    {
        scpi.setReadMode(value);

        return this;
    }

    public Scpi get()
    {
        return scpi;
    }

    public static ScpiBuilder create(String command)
    {
        Objects.requireNonNull(command, "Command must not be null");

        return new ScpiBuilder(command);
    }
}
