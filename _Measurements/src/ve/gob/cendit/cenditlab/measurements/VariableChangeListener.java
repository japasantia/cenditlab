package ve.gob.cendit.cenditlab.measurements;

/**
 * Created by jarias on 12/07/17.
 */
public interface VariableChangeListener
{
    //boolean validate(Variable variable, Object oldValue, Object newValue);
    void changed(Variable variable, Object oldValue, Object newValue);
}
