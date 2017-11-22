package ve.gob.cendit.cenditlab.measurements;
import org.jetbrains.annotations.Contract;

import java.util.LinkedList;
import java.util.List;

public class VariableGeneric<T>
{
    private String name;
    private T value;
    private List<VariableChangeListener> changeListeners;
    private VariableConverter<Object, Object> converter;
    private Convert convert;


    @Contract("null, _ -> fail")
    public VariableGeneric(String name, T value)
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

    public void setValue(T value)
    {
        if (changeListeners != null)
        {
            // Preserva momentaneamente el antiguo valor
            Object oldValue = this.value;
            // Actualiza con nuevo valor
            this.value = value;

            // Ejecuta validacion, lanza excepcion si falla
            validateThrowExceptionIfFail(oldValue, this.value);

            // Reportar a cada escucha que ha cambiado el valor
            callChangeListeners(oldValue, this.value);
        }
    }

    public <F> F getValue()
    {
        return convert.convert(value);
    }

    public <F> F getValue(VariableConverter<T, F> converter)
    {
        if (converter == null)
        {
            throw new IllegalArgumentException("Variable converter must not be null");
        }

        return converter.convert(value);
    }

    public String getName()
    {
        return name;
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

    public void removeListener(VariableChangeListener listener)
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
        /*
        changeListeners.stream()
                .forEach(listener ->
                        listener.changed(this, oldValue, newValue));
        */
    }

    protected void validateThrowExceptionIfFail(Object oldValue, Object newValue)
    {
        if (changeListeners == null)
        {
            return;
        }
        /*
        boolean success = changeListeners.stream()
                .noneMatch(listener -> listener.validate(this, oldValue, newValue) == false);

        if (!success)
        {
            // TODO: falla validacion, lanzar excepcion
        }
        */
    }
}

interface Convert
{
    <T> T convert(Object object);
}