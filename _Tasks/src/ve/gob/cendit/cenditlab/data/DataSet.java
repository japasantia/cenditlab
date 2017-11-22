package ve.gob.cendit.cenditlab.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class DataSet
{
    private List<Data> valuesList = new ArrayList<>(1);

    private DataSet()
    {

    }

    public DataSet(Data... data)
    {
        if (data.length == 0)
        {
            throw new IllegalArgumentException("Empty input data");
        }

        Arrays.stream(data)
            .forEach(v -> valuesList.add(v));
    }

    public boolean containsData(String name)
    {
        return valuesList.stream()
            .anyMatch(v -> v.getName() == name);
    }

    public Data getData(String name)
    {
        Optional<Data> result = valuesList.stream()
            .filter(v -> v.getName() == name)
            .findFirst();

        return result.isPresent() ? result.get() : null;
    }

    public void setData(String name, Data data)
    {
        valuesList.stream()
                .filter(v -> v.getName() == name)
                .findFirst()
                .ifPresent(v -> {
                    valuesList.remove(v);
                    valuesList.add(data);
                });
    }

    public void setValue(String name, Object value)
    {
        Optional<Data> result = valuesList.stream()
            .filter(v -> v.getName() == name)
            .findFirst();

        if(result.isPresent())
        {
            result.get().set(value);
        }
    }

    public List<Data> getValues()
    {
        return valuesList;
    }

    public List<String> getValueNames()
    {
        return valuesList.stream()
            .map(v -> v.getName()).collect(Collectors.toList());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        valuesList.stream()
            .forEach(v -> sb.append(String.format("%s, ", v.toString())));

        // Elimina la última coma y el ultimo espacio
        if (sb.length() > 1)
        {
            return sb.substring(0, sb.length() - 2);
        }

        return sb.toString();
    }
}
