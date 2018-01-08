package ve.gob.cendit.cenditlab.io.tests;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import ve.gob.cendit.cenditlab.io.gpib.ILinuxGpib;
import ve.gob.cendit.cenditlab.io.gpib.LinuxGpibLibrary;

import java.util.Arrays;

/**
 * Created by jsars on 05/06/17.
 */
public class LinuxGpibLibraryTest
{
    public static void main(String[] args)
    {
        test2();
    }

    public static void test1() {

        int stb;

        ILinuxGpib lib1 = LinuxGpibLibrary.getLibrary();
        ILinuxGpib lib2 = LinuxGpibLibrary.getLibrary();

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

    public static void test2()
    {
        ILinuxGpib linuxGpib = LinuxGpibLibrary.getLibrary();
        int stb;
        byte[] buffer = new byte[1024];

        int ud1 = linuxGpib.ibdev(0, 10, 0,
                ILinuxGpib.T1s, 1, 0);
        stb = linuxGpib.ibwrt(ud1, "*IDN?\n".getBytes(),6);
        printStatusByte(stb);

        stb = linuxGpib.ibrd(ud1, buffer, 1024);
        printStatusByte(stb);

        System.out.printf("IDN: %s\n", new String(buffer));

        stb = linuxGpib.ibwrt(ud1, "*IDN?\n",6 );
        printStatusByte(stb);
        Arrays.fill(buffer, (byte)0);
        stb = linuxGpib.ibrd(ud1, buffer, 1024);
        printStatusByte(stb);
        System.out.printf("IDN: %s\n", new String(buffer));


        int result[] = {-1};
        linuxGpib.ibask(ud1, 1, result);

        int udBoard = linuxGpib.ibfind("agi".getBytes());
        udBoard = linuxGpib.ibfind("agi");
/*
        stb = linuxGpib.ibbna(ud1, "agi".getBytes());
        printStatusByte(stb);
        stb = linuxGpib.ibbna(ud1, "agi");
        printStatusByte(stb);
*/

        udBoard = linuxGpib.ibdev(0,11,0,
                ILinuxGpib.T3s, 1, 0);
        //stb = linuxGpib.ibcac(udBoard, 0);
        //printStatusByte(stb);

        stb = linuxGpib.ibclr(ud1);
        printStatusByte(stb);
        stb = linuxGpib.ibclr(udBoard);
        printStatusByte(stb);

        for (int address = 0; address < 30; ++address)
        {
            short[] foundListener = {0};
            stb = linuxGpib.ibln(ud1, address, ILinuxGpib.NO_SAD, foundListener);
            printStatusByte(stb);

            if (foundListener[0] != 0)
            {
                System.out.printf("Found device at address: %s", address);
            }
        }
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
