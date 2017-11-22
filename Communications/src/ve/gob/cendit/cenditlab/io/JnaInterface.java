package ve.gob.cendit.cenditlab.io;

/**
 * Created by jsars on 05/06/17.
 */

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * JnaInterface for JNA test
 * @author J. Arias
 */
public interface JnaInterface extends Library
{
    JnaInterface INSTANCE =
            (JnaInterface) Native.loadLibrary(
                    (Platform.isWindows() ? "msvcrt" : "c"),
                    JnaInterface.class);
    void printf(String format, Object... args);
    int sprintf(byte[] buffer, String format, Object... args);
    int scanf(String format, Object... args);
}
