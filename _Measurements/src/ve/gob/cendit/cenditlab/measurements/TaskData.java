package ve.gob.cendit.cenditlab.measurements;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
public interface ITaskData
{
    String getName();
    ITask getTask();

    List<String> getValueNames();

    List<Value> getValues();
}
*/

public class TaskData
{
    private ITask2 ownerTask;
    private List<Value> valuesList;

    public TaskData(ITask2 owner)
    {
        ownerTask = owner;
        valuesList = new ArrayList<>(1);
    }

    public TaskData(ITask2 owner, Value... values)
    {
        ownerTask = owner;
        addAll(values);
    }

    public static TaskData fromNames(ITask2 owner, String... names)
    {
        TaskData taskData = new TaskData(owner);

        Stream.of(names)
            .forEach( name -> taskData.add(new Value(name)) );

        return taskData;
    }

    public ITask2 getOwner()
    {
        return ownerTask;
    }

    public List<Value> getValues()
    {
        return Collections.unmodifiableList(valuesList);
    }

    public List<String> getValueNames()
    {
        return valuesList.stream()
                .map(value -> value.getName())
                .collect(Collectors.toList());
    }

    public void add(Value value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("Added value must not be null");
        }

        valuesList.add(value);
    }

    public void addAll(Value... values)
    {
        if (values == null)
        {
            throw new IllegalArgumentException("Added values must not be null");
        }

        valuesList.stream()
            .forEach(value -> add(value));
    }

}