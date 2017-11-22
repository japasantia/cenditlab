package ve.gob.cendit.cenditlab.measurements;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariablesBundle
{
    private Map<String, Variable> bundleMap;

    public VariablesBundle()
    {
        bundleMap = new HashMap<>();
    }

    public VariablesBundle(String[] names, Variable[] variables)
    {

    }

    public void add(String name, Variable variable)
    {
        bundleMap.put(name, variable);
    }

    public Variable get(String name)
    {
        return bundleMap.get(name);
    }

    public Variable remove(String name)
    {
        return bundleMap.remove(name);
    }

    public Collection<Variable> variables()
    {
        return bundleMap.values();
    }

    public Set<String> names()
    {
        return bundleMap.keySet();
    }
}
