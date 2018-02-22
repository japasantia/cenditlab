package ve.gob.cendit.cenditlab.io.tests;

import com.sun.jna.Pointer;
import ve.gob.cendit.cenditlab.io.vxi11.IVxi112;
import ve.gob.cendit.cenditlab.io.vxi11.LinuxVxi112Library;

public class LinuxVxi112LibraryTest
{
    public static void main(String[] args)
    {
        IVxi112 library = LinuxVxi112Library.getLibrary();

        Pointer handle = IVxi112.Vxi11_CreateHandle();

        byte[] buffer = new byte[256];
        int ret = library.Vxi11_LibraryId(buffer, buffer.length);
        System.out.println(new String(buffer));

        ret = library.Vxi11_OpenDevice(handle, "127.0.0.1", "instr0");

        ret = library.Vxi11_RegisterSRQHandler(new IVxi112.SRQCallback()
        {
            @Override
            public int callback(Pointer arg)
            {
                System.out.println("In callback [IVxi112.SRQCallback]");
                System.out.println(arg.getString(0));
                return 0;
            }
        });
    }
}
