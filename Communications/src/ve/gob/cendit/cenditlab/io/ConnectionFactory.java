package ve.gob.cendit.cenditlab.io;

import com.sun.jna.Platform;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by jarias on 31/08/17.
 */
public class ConnectionFactory
{
    public static IConnection CreateConnection(VisaAddress visaAddress)
    {
        if (Platform.isWindows())
        {
            throw new NotImplementedException();
        }
        else if (Platform.isLinux())
        {
            if (visaAddress.isGpib())
            {
                return new LinuxGpibConnection(visaAddress);
            }
            else if (visaAddress.isVxi())
            {
                return new LinuxVxi11Connection(visaAddress);
            }
            else
            {
                throw new RuntimeException("Not supported operating visa address interface");
            }
        }
        else
        {
            throw new RuntimeException("Not supported operating system");
        }
    }
}
