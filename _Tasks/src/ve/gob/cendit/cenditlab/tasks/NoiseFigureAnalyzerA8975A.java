package ve.gob.cendit.cenditlab.tasks;

import ve.gob.cendit.cenditlab.io.IConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jarias on 24/08/17.
 */
public class NoiseFigureAnalyzerA8975A extends Instrument
{
    private Task[] tasks =
    {
        new MeasureNoiseFigure(),
        new MeasureNoisePower(),
        new MeasureYFactor(),
        new MeasureENR()
    };

    public NoiseFigureAnalyzerA8975A(String name, String description)
    {
        super(name, description);
    }

    @Override
    public IConnection getConnection()
    {
        return null;
    }

    @Override
    public List<Task> getTasks()
    {
        return Arrays.asList(tasks);
    }

    private class SimulatorConnection implements IConnection
    {
        @Override
        public void open()
        {  }

        @Override
        public void close()
        { }

        @Override
        public String read()
        {
            return null;
        }

        @Override
        public int read(byte[] bytes, int offset, int length)
        {
            return 0;
        }

        @Override
        public int write(String s)
        {
            return 0;
        }

        @Override
        public int write(byte[] bytes, int offset, int length)
        {
            return 0;
        }
    }

    private class MeasureNoiseFigure extends Task
    {
        public MeasureNoiseFigure()
        { }

        @Override
        public void execute()
        {

        }
    }

    private class MeasureNoisePower extends Task
    {
        public MeasureNoisePower()
        { }

        @Override
        public void execute()
        {

        }
    }

    private class MeasureYFactor extends Task
    {
        public MeasureYFactor()
        { }

        @Override
        public void execute()
        {

        }
    }

    private class MeasureENR extends Task
    {
        public MeasureENR()
        { }

        @Override
        public void execute()
        { }
    }
}
