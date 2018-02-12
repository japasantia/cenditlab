package ve.gob.cendit.cenditlab.io.vxi11;

import com.sun.jna.Native;

public class LinuxVxi112Library
{
    private static IVxi112 library;

    public static IVxi112 getLibrary()
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
            //System.setProperty("java.library.path",
            //        "/usr/local/lib:" + System.getProperty("java.library.path"));
            //System.loadLibrary("vxi11");
            library = Native.loadLibrary("/home/jsars/PasantiaCendit/Desarrollo/Software/vxi11-lib/vxi11/build64/libvxi11.so", IVxi112.class);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            System.exit(0);
        }

        return library;
    }
}
