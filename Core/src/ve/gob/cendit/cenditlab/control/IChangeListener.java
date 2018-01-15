package ve.gob.cendit.cenditlab.control;

public interface IChangeListener<TSOURCE, TDATA>
{
    void onChange(TSOURCE source, TDATA oldObject, TDATA newObject);
}
