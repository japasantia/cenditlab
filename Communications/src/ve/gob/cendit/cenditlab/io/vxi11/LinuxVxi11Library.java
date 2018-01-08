package ve.gob.cendit.cenditlab.io.vxi11;

import com.sun.jna.Native;

/**
 * Created by jarias on 6/23/17.
 */
public class LinuxVxi11Library
{
    private static IVxi11 library;

    public static IVxi11 getLibrary()
    {
        if (library != null)
        {
            // Si la libreria ya ha sido cargada
            return library;
        }

        // Se carga la libreria por primera vez
        try
        {
            // TODO: revisar el proceso de carga libreria
            // Primero carga libreria, segun
            // https://stackoverflow.com/questions/25978763/jna-library-and-native-library-not-found-error
            //NativeLibrary.addSearchPath("vxi11", "/usr/local/lib");
            System.setProperty("java.library.path",
                    "/usr/local/lib:"
                            + System.getProperty("java.library.path"));
            //System.loadLibrary("vxi11");
            library =
                Native.loadLibrary("vxi11", IVxi11.class);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            System.exit(0);
        }

        return library;
    }
}
