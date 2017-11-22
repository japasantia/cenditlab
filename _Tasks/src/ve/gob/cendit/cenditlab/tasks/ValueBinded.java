package ve.gob.cendit.cenditlab.tasks;


public class ValueBinded extends Value
{
    private Value valueBinded;

    public ValueBinded(String name, Value value)
    {
        super(name);
        valueBinded = value;
    }

    @Override
    public Object set(Object newValue)
    {
        return valueBinded.set(newValue);
    }

    @Override
    public Object set(Value newValue)
    {
        return valueBinded.set(newValue);
    }

    @Override
    public Object get()
    {
        return valueBinded.get();
    }
}
