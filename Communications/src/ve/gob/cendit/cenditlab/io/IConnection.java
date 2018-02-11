package ve.gob.cendit.cenditlab.io;

public interface IConnection
{
    void open();
    void close();

    String read();
    int read(byte[] buffer);
    int read(byte[] buffer, int offset, int length);

    int write(String data);
    int write(byte[] buffer);
    int write(byte[] buffer, int offset, int length);

    void waitForCompletion();
}
