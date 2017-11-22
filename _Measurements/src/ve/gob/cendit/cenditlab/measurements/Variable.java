package ve.gob.cendit.cenditlab.measurements;


import org.jetbrains.annotations.Contract;

import java.util.LinkedList;
import java.util.List;

public class Variable
{
    private String name;
    private Object value;
    private List<VariableChangeListener> changeListeners;
    private VariableConverter<Object, Object> converter;

    @Contract("null, _ -> fail")
    public Variable(String name, Object value)
    {
        // El nombre de la variable no puede ser nulo
        if (name == null)
        {
            // TODO: revisar excepcion
            throw new IllegalArgumentException(
                    "Variable name must not be null");
        }

        this.name = name;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return value.toString();
    }

    public void setValue(Object value)
    {
        // Preserva momentaneamente el antiguo valor
        Object oldValue = this.value;
        // Actualiza con nuevo valor
        this.value = value;

        // Reportar a cada escucha que ha cambiado el valor
        callChangeListeners(oldValue, this.value);

    }

    public Object getValue()
    {
        return value;
    }

    public String getName()
    {
        return name;
    }

    public <F, T> T convert(VariableConverter<F, T> converter)
    {
        return converter.convert((F)value);
    }

    public void addChangeListener(VariableChangeListener listener)
    {
        if (listener == null)
        {
            // TODO: revisar excepciones argumento nulp
            throw new IllegalArgumentException("Variable listener must not be null");
        }

        if (changeListeners == null)
        {
            changeListeners = new LinkedList<VariableChangeListener>();
        }

        changeListeners.add(listener);
    }

    public void removeChangeListener(VariableChangeListener listener)
    {
        if (changeListeners != null)
        {
            changeListeners.remove(listener);
        }
    }

    public void removeAllListeners()
    {
        if (changeListeners != null)
        {
            changeListeners.clear();
        }
    }

    protected void callChangeListeners(Object oldValue, Object newValue)
    {
        if (changeListeners == null)
        {
            return;
        }

        changeListeners.stream()
            .forEach(listener ->
                listener.changed(this, oldValue, newValue));

    }

    protected void validateThrowExceptionIfFail(Object oldValue, Object newValue)
    {
        /*
        boolean success = changeListeners.stream()
                .noneMatch(listener -> listener.validate(this, oldValue, newValue) == false);

        if (!success)
        {
            // TODO: falla validacion, lanzar excepcion
        }
        */
    }

    // TODO: clarificar el proceso de conversion
    protected void applyConverter()
    {
        if (converter != null)
        {
            value = converter.convert(value);
        }
    }
}