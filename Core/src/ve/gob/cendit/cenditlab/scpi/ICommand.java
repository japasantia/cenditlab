package ve.gob.cendit.cenditlab.scpi;

import ve.gob.cendit.cenditlab.io.IConnection;

public interface ICommand
{
    void execute(IConnection connection);
}
