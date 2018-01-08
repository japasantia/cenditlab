package ve.gob.cendit.cenditlab.io.visa;

import com.sun.jna.Native;

/**
 * Created by jarias on 07/07/17.
 */
public class VisaLibrary
{
    private static IVisa library = null;

    public static IVisa getLibrary()
    {
        if (library != null)
        {
            return library;
        }

        try
        {
            library = Native.loadLibrary("visa",
                    IVisa.class);
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
            System.exit(0);
        }

        return library;
    }
}
