package ve.gob.cendit.cenditlab.measurements;

/**
 * Created by jarias on 12/07/17.
 */
public interface VariableConverter<F, T>
{
    T convert(F value);
}
