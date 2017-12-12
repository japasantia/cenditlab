package ve.gob.cendit.cenditlab.data;


interface IEventCaller<T>
{
    void call(Object source, T handler, Object... args);
}