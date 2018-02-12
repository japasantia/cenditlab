package ve.gob.cendit.cenditlab.io.tests;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

import ve.gob.cendit.cenditlab.io.gpib.ILinuxGpib;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibWrapper;

/**
 * Created by jsars on 05/06/17.
 */
public class LinuxGpibTests
{
    public static void main(String[] args)
    {
        test1();
    }

    public static void test1() {

        int stb;

        ILinuxGpib lib1 = LinuxGpibWrapper.get();
        ILinuxGpib lib2 = LinuxGpibWrapper.get();

        int ud1 = lib1.ibdev(0, 10, 0, 13, 1, 0);
        int ud2 = lib2.ibdev(0, 11, 0, 13, 1, 0);

        long memory = Native.malloc(1024);
        Pointer pointerBuffer = new Pointer(memory);

        stb = lib2.ibsic(ud2);
        printStatusByte(stb);

        stb = lib1.ibclr(ud1);
        printStatusByte(stb);
        pointerBuffer.setString(0, "*IDN?\n");
        stb = lib1.ibwrt(ud1, pointerBuffer, 6);
        printStatusByte(stb);
        printStatusByte(lib1.ibsta);

        pointerBuffer.clear(1024);
        stb = lib1.ibrd(ud1, pointerBuffer, 1024);
        printStatusByte(stb);
        System.out.println(pointerBuffer.getString(0));

        String errorMessage = "";
        do {
            pointerBuffer.setString(0, "SYST:ERR?\n");
            stb = lib1.ibwrt(ud1, pointerBuffer, 10);
            printStatusByte(stb);
            pointerBuffer.clear(1024);
            stb = lib1.ibrd(ud1, pointerBuffer, 1024);
            printStatusByte(stb);
            errorMessage = pointerBuffer.getString(0);
            System.out.println(errorMessage);
        }
        while (errorMessage != null && !errorMessage.contains("No error"));
    }

    private enum ErrorBits
    {
        EDVR(0),
        ECIC(1),
        ENOL(2),
        EADR(3),
        EARG(4),
        ESAC(5),
        EABO(6),
        ENEB(7),
        EDMA(8),
        EOIP(10),
        ECAP(11),
        EFSO(12),
        EBUS(14),
        ESTV(15),
        ESRQ(16),
        ETAB(20);

        public int bit;

        ErrorBits(int b)
        {
            bit = b;
        }
    }

    private enum StatusBits
    {
        DCAS(0x01),
        DTAS(0x02),
        LACS(0x04),
        ATN(0x10),
        CIC(0x20),
        REM(0x40),
        LOK(0x80),
        CMPL(0x100),
        EVENT(0x200),
        SPOLL(0x400),
        RQS(0x800),
        SRQI(0x100),
        END(0x2000),
        TIMO(0x400),
        ERR(0x8000);

        public int mask;

        StatusBits(int mask)
        {
            this.mask = mask;
        }
    }

    private static void printStatusByte(int stb)
    {
        for (StatusBits statusBit : StatusBits.values())
        {
            System.out.printf("%s = %s ",
                    statusBit.name(),
                    (statusBit.mask &  stb) != 0 ? "1 " : "0");
        }
        System.out.println();
    }
}
