package ve.gob.cendit.cenditlab.control;

public interface IEventCaller<T>
{
    void call(Object source, T handler, Object... args);
}