package ve.gob.cendit.cenditlab.io.gpib;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class LibGpibWrapper
{

    public static ve.gob.cendit.cenditlab.io.gpib.ILinuxGpib ILinuxGpib = null;

    public static ILinuxGpib get()
    {

        if (ILinuxGpib != null)
            return ILinuxGpib;

        ILinuxGpib = (ILinuxGpib) Native.loadLibrary("gpib", ILinuxGpib.class);

        return ILinuxGpib;
    }

    public static final int NO_SAD = 0;
    public static final int ALL_SAD = -1;
}

class Address4882
{

    private short primaryAddress;
    private short secondaryAddress;

    public Address4882(short primaryAddress, short secondaryAddress) {
        this.primaryAddress = primaryAddress;
        this.secondaryAddress = secondaryAddress;
    }

    public short getPrimaryAddress() {
        return primaryAddress;
    }

    public short getSecondaryAddress() {
        return secondaryAddress;
    }

    public short toShort() {
        return Address4882.MakeAddress(primaryAddress, secondaryAddress);
    }

    public static short MakeAddress(int primaryAddress, int secondaryAddress)
    {
        short address;

        address = (short)(primaryAddress & 0xFF);
        address |= (short)((secondaryAddress << 8) & 0xFF00);
        return address;
    }
}

class LibGpibTest {

    public static void main(String[] args) {

        int status;

        ILinuxGpib linuxGpib = LibGpibWrapper.get();

        int udNfa = linuxGpib.ibdev(0, 10,0,
                12, 1, 0);

        int udGpib = linuxGpib.ibdev(0, 11, 0,
                12, 1, 0);
        
        status = linuxGpib.ibsic(udGpib);
        status = linuxGpib.ibsic(udNfa);

        long memory = Native.malloc(256);
        Pointer buffer = new Pointer(memory);

        status = linuxGpib.ibln(udNfa, 10,
                LibGpibWrapper.ALL_SAD, buffer);

        System.out.println(buffer.getShortArray(0, 30).toString());

        status = linuxGpib.ibloc(udNfa);
        status = linuxGpib.ibpct(udGpib);

        status = linuxGpib.ibrsc(udGpib, 1);

        status = linuxGpib.ibsic(udGpib);
        status = linuxGpib.ibsic(udNfa);

        buffer.setString(0, "*IDN?\n");
        status = linuxGpib.ibwrt(udNfa, buffer, 6);

        buffer.clear(256);
        status = linuxGpib.ibrd(udNfa, buffer, 256);

        status = linuxGpib.ibloc(udNfa);

        System.out.println("Received: " + buffer.getString(0));
    }
}
