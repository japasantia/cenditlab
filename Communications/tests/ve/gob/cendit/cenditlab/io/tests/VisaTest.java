package ve.gob.cendit.cenditlab.io.tests;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import ve.gob.cendit.cenditlab.io.IVisa;
import ve.gob.cendit.cenditlab.io.VisaLibrary;

/**
 * Created by jarias on 07/07/17.
 */
public class VisaTest
{
    public static void main(String args[])
    {
        //test1();
        test2();
    }

    private static void test1()
    {
        IVisa library = VisaLibrary.getLibrary();

        int status;
        byte[] buf = new byte[256];
        int[] retCnt = new int[1];

        long memory = Native.malloc(4);
        Pointer defaultRM = new Pointer(memory);

        memory = Native.malloc(4);
        Pointer device = new Pointer(memory);

        status = library.viOpenDefaultRM(defaultRM);

        status = library.viOpen(defaultRM,
                "GPIB0::10::0::INSTR",
                IVisa.VI_NULL, IVisa.VI_NULL,
                device);

        status = library.viWrite(device, "*IDN?".getBytes(), 5, retCnt);
        status = library.viRead(device, buf, buf.length, retCnt);

        status = library.viClose(device);
    }

    private static void test2()
    {
        IVisa library = VisaLibrary.getLibrary();

        int status;
        byte[] buf = new byte[256];
        int[] retCnt = new int[1];
        int[] list = new int[10];

        int[] defaultRM = new int[1];
        int[] device = new int[1];

        status = library.viOpenDefaultRM(defaultRM);

        status = library.viFindRsrc(defaultRM[0], "GPIB*",
                list, retCnt, buf);

        status = library.viFindNext(defaultRM[0], list);

        status = library.viOpen(defaultRM[0],
                //"TCPIP0::192.168.1.10::gpib0,10",
                "GPIB0::10::0::INSTR",
                IVisa.VI_NULL, IVisa.VI_NULL,
                device);

        status = library.viWrite(device[0], "*IDN?".getBytes(), 5, retCnt);
        status = library.viRead(device[0], buf, buf.length, retCnt);

        status = library.viClose(device);
    }
}
