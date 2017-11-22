package ve.gob.cendit.cenditlab.tasks;


import ve.gob.cendit.cenditlab.data.Data;

import java.util.*;

public class ComponentSlots
{
    private final Slot[] slots;
    private Map<String, Data> dataMap = new HashMap<>();

    public ComponentSlots(Slot... slots)
    {
        if (slots.length == 0)
        {
            throw new IllegalArgumentException("Data slots must not be empty");
        }

        this.slots = slots;

        Arrays.stream(slots)
            .forEach(ds -> dataMap.put(ds.getName(), ds.getDefaultData()));
    }


    private Optional<Slot> findDataSlotByName(String slotName)
    {
        return Arrays.stream(slots)
            .filter(ds -> ds.getName() == slotName)
            .findFirst();
    }

    public Data getDefaultData(String slotName)
    {
        Optional<Slot> result = findDataSlotByName(slotName);

        return result.isPresent() ? result.get().getDefaultData() : null;
    }

    public Data getDefaultData(Slot slot)
    {
        return getDefaultData(slot.getName());
    }

    public boolean containsSlot(String slotName)
    {
        return findDataSlotByName(slotName)
                .isPresent();
    }

    public Slot[] getSlots()
    {
        return slots;
    }

    public Slot getDataSlot(String slotName)
    {
        Optional<Slot> result = findDataSlotByName(slotName);

        return result.isPresent() ? result.get() : null;
    }

    public Slot getDataSlot(int slotIndex)
    {
        if (slotIndex >= 0 && slotIndex < slots.length)
        {
            return slots[slotIndex];
        }

        return null;
    }

    public Data getData(String slotName)
    {
        return dataMap.get(slotName);
    }

    public Data getData(Slot slot)
    {
        return dataMap.get(slot.getName());
    }

    public Data getData(int slotIndex)
    {
        if (slotIndex >= 0 && slotIndex < slots.length)
        {
            return dataMap.get(slots[slotIndex].getName());
        }

        return null;
    }

    public void setData(String slotName, Data data)
    {
        if (dataMap.containsKey(slotName))
        {
            dataMap.put(slotName, data);
        }
    }

    public void setData(Slot slot, Data data)
    {
        setData(slot.getName(), data);
    }

    public void setData(int slotIndex, Data data)
    {
        Slot slot = getDataSlot(slotIndex);

        if (slot != null)
        {
            setData(slot.getName(), data);
        }
    }

    public void loadData(Data... dataArgs)
    {
        int size = dataArgs.length;

        if (size == 0 || size != slots.length)
        {
            throw new IllegalArgumentException("Data arguments size doesn't match with data slots size");
        }

        Iterator<Data> iterData = Arrays.asList(dataArgs).iterator();
        dataMap.keySet().stream()
            .forEach(key -> dataMap.put(key, iterData.next()));
    }

/*
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (String slotName : dataMap.keySet())
        {
            sb.append(String.format("%s: %s, ",
                    slotName, dataMap.get(slotName)));
        }


        if (sb.length() > 2)
        {
            return sb.substring(0, sb.length() - 2);
        }

        return sb.toString();
    }
 */

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        dataMap.keySet().stream()
            .forEach(slotName -> sb.append(String.format("%s\t", slotName)));

        sb.append('\n');

        dataMap.entrySet().stream()
            .forEach(entry -> sb.append(String.format("%s\t", entry.getValue())));

        sb.append('\n');

        return sb.toString();
    }
}
