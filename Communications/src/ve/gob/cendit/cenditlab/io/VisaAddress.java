package ve.gob.cendit.cenditlab.io;

/**
 * Created by jsars on 17/06/17.
 */



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
        INTERFACE           SYNTAX

        VXI INSTR           VXI[board]::VXI logical address[::INSTR]
        VXI MEMACC          VXI[board]::MEMACC
        VXI BACKPLANE       VXI[board][::mainframe logical address]::BACKPLANE
        VXI SERVANT         VXI[board]::SERVANT

        GPIB-VXI INTR       GPIB-VXI[board]::VXI logical address[::INSTR]
        GPIB-VXI MEMACC     GPIB-VXI[board]::MEMACC
        GPIB-VXI BACKPLANE  GPIB-VXI[board][::mainframe logical address]::BACKPLANE
        GPIB INSTR          GPIB[board]::primary address[::secondary address][::INSTR]
        GPIB INTFC          GPIB[board]::INTFC
        GPIB SERVANT        GPIB[board]::SERVANT

        PXI INSTR           PXI[board]::device[::function][::INSTR]

        Serial INSTR        ASRL[board][::INSTR]

        TCPIP INSTR         TCPIP[board]::host address[::LAN device name][::INSTR]
        TCPIP SOCKET        TCPIP[board]::host address::port::SOCKET

        USB INSTR           USB[board]::manufacturer ID::model
                            code::serial number[::USB interface
                               number][::INSTR]

        USB RAW             USB[board]::manufacturer ID::model
                            code::serial number[::USB interface
                            number]::RAW

 OPTIONAL STRING         DEFAULT VALUE
        SEGMENTS

        VXI0::1::INSTR          A VXI device at logical address 1 in VXI interface VXI0.
        VXI::MEMACC             Board-level register access to the VXI interface.
        VXI::1::BACKPLANE       Mainframe resource for chassis 1 on the default VXI system, GPIB-VXI2::BACKPLANE which is interface 0.
        VXI0::SERVANT           Servant/device-side resource for VXI interface 0.

        GPIB-VXI::9::INSTR      A VXI device at logical address 9 in a GPIB-VXI controlled system.
        GPIB::1::0::INSTR       A GPIB device at primary address 1 and secondary address 0 in GPIB interface 0.

        ASRL1::INSTR            A serial device attached to interface ASRL1.

        GPIB-VXI1::MEMACC       Board-level register access to GPIB-VXI interface number 1.
        GPIB2::INTFC            Interface or raw resource for GPIB interface 2.
        GPIB-VXI2::BACKPLANE    Mainframe resource for default chassis on GPIB-VXI interface 2.
        GPIB1::SERVANT          Servant/device-side resource for GPIB interface 1.

        PXI::15::INSTR          PXI device number 15 on bus 0.

        TCPIP0::1.2.3.4::999    Raw TCP/IP access to port 999 at the specified IP address.
            ::SOCKET
        TCPIP::dev@company.com  A TCP/IP device using VXI-11 located at the specified address.
            ::INSTR             This uses the default LAN Device Name of inst0.

        USB::0x1234::125::      A USB Test & Measurement class device with
        A22-5::INSTR            manufacturer ID 0x1234, model code 125, and serial
                                number A22-5. This uses the device’s first available
                                USBTMC interface. This is usually number 0.

        USB::0x5678::0x33::     A raw USB nonclass device with manufacturer ID
        SN999::1::RAW           0x5678, model code 0x33, and serial number
                                SN999.This uses the device’s interface number 1.
 */

public class VisaAddress
{
    private String visaAddress;
    private Map<VisaAddressFields, String> addressFieldsMap;

    public VisaAddress(String address)
    {
        // this(VisaAddress.ParseAddress(address));

        VisaAddress va = VisaAddress.parseAddress(address);

        this.visaAddress = va.visaAddress;
        this.addressFieldsMap = va.addressFieldsMap;
    }

    public VisaAddress(VisaAddress address)
    {
        this.visaAddress = address.visaAddress;
        this.addressFieldsMap = new HashMap<>(address.addressFieldsMap);
    }

    private VisaAddress()
    {
        addressFieldsMap = new HashMap<>();
    }

    private void setField(VisaAddressFields field, String fieldValue)
    {
        if (field == null)
        {
            throw new IllegalArgumentException("field must not be null");
        }

        addressFieldsMap.put(field, fieldValue);
    }

    public String getField(VisaAddressFields field)
    {
        if (field== null)
        {
            throw new IllegalArgumentException("field must not be null");
        }


        if (hasField(field))
        {
            return addressFieldsMap.get(field);
        }
        else
        {
            // TODO: revisar si se debe lanzar excepcion
        }

        return null;
    }

    public boolean hasField(VisaAddressFields field)
    {
        if (field == null)
        {
            throw new IllegalArgumentException("field must not be null");
        }

        return addressFieldsMap.containsKey(field);
    }

    public String getInterface()
    {
        return getField(VisaAddressFields.INTERFACE);
    }

    /*
    public String getBoard()
    {
        return getName(VisaAddressFields.BOARD);
    }
    */

    public int getBoard()
    {
        return Integer.parseInt(
                getField(VisaAddressFields.BOARD));
    }

    public String getLogicalAddress()
    {
        return getField(VisaAddressFields.LOGICAL_ADDRESS);
    }

    public String getResource()
    {
        return getField(VisaAddressFields.RESOURCE);
    }

    public int getPrimaryAddress()
    {
        return Integer.parseInt(
                getField(VisaAddressFields.PRIMARY_ADDRESS));
    }

    public int getSecondaryAddress()
    {
        return Integer.parseInt(
                getField(VisaAddressFields.SECONDARY_ADDRESS));
    }

    public String getDevice()
    {
        return getField(VisaAddressFields.DEVICE);
    }

    public String getFunction()
    {
        return getField(VisaAddressFields.FUNCTION);
    }

    public String getDeviceName()
    {
        return getField(VisaAddressFields.DEVICE_NAME);
    }

    public String getHostAddress() { return getField(VisaAddressFields.HOST_ADDRESS); }

    public String getPort()
    {
        return getField(VisaAddressFields.PORT);
    }

    public String getManufacturerId()
    {
        return getField(VisaAddressFields.MANUFACTURER_ID);
    }

    public String getModelCode()
    {
        return getField(VisaAddressFields.MODEL_CODE);
    }

    public String getSerialNumber()
    {
        return getField(VisaAddressFields.SERIAL_NUMBER);
    }

    public String getInterfaceNumber()
    {
        return getField(VisaAddressFields.INTERFACE_NUMBER);
    }

    public boolean isVxi()
    {
        return getInterface().equals("VXI");
    }

    public boolean isPxi()
    {
        return getInterface().equals("PXI");
    }

    public boolean isGpibVxi()
    {
        return getInterface().equals("GPIB-VXI");
    }

    public boolean isGpib()
    {
        return getInterface().equals("GPIB");
    }

    public boolean isAsrl()
    {
        return getInterface().equals("ASRL");
    }

    public boolean isUsb()
    {
        return getInterface().equals("USB");
    }

    public boolean isTcpIp()
    {
        return getInterface().equals("TCPIP");
    }

    public static boolean isValid(String address)
    {
        if (address == null)
        {
            throw new IllegalArgumentException("address must be a not null string");
        }

        Stream<String> patternsStream =
                Arrays.asList(VisaAddressPatterns.getAsArray()).stream();

        return patternsStream.anyMatch(pattern -> address.matches(pattern));
    }

    public static VisaAddress parseAddress(String address)
    {
        if (address == null)
        {
            throw new IllegalArgumentException("address must be a not null string");
        }

        for (String pattern : VisaAddressPatterns.getAsArray())
        {
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(address);

            if (matcher.find())
            {
                return generateVisaAddress(address, matcher);
            }
        }

        // TODO: generar clase de Excepcion Visa
       // La direccion no es valida. Se lanza excepcion
        throw new InvalidVisaAddressException("Invalid visa address");
    }

    private static VisaAddress generateVisaAddress(String address, Matcher matcher)
    {
        VisaAddress visaAddress = new VisaAddress();
        // TODO: revisar esta asignacion
        visaAddress.visaAddress = address;

        for (VisaAddressFields field : VisaAddressFields.values())
        {
            try
            {
                String fieldName = field.getName();
                String fieldValue = matcher.group(fieldName);
                if (fieldValue != null)
                {
                    visaAddress.setField(field, fieldValue);
                }
            }
            catch (Exception ex)
            {
                // El campo de direccion no existe
                // continuar
            }
        }

        return visaAddress;
    }
}
class VisaAddressPatterns
{
    /*Colocar nombre a los grupos regex
    https://stackoverflow.com/questions/415580/regex-named-groups-in-java
    "(?<login>\\w+) (?<id>\\d+)"
    matcher.group("login") ==> TEST
    */

    public final static String VXI_INSTR =
            "^(?<INTERFACE>VXI)(?<BOARD>\\d+)?::(?<LOGICALADDRESS>\\d+)(::(?<RESOURCE>INSTR))?";
    public final  static String VXI_MEMACC =
            "^(?<INTERFACE>VXI)(?<BOARD>\\d+)?::(?<RESOURCE>MEMACC)";
    public final static String VXI_BACKPLANE =
            "^(?<INTERFACE>VXI)(?<BOARD>\\d+)?(::(?<LOGICALADDRESS>\\d+))?::(?<RESOURCE>BACKPLANE)";
    public final static String VXI_SERVANT =
            "^(?<INTERFACE>VXI)(?<BOARD>\\d+)?::(?<RESOURCE>SERVANT)";

    public final static String GPIB_VXI_INSTR =
            "^(?<INTERFACE>GPIB-VXI)(?<BOARD>\\d+)?::(?<LOGICALADDRESS>\\d+)(?<RESOURCE>::(INSTR))?";
    public final static String GPIB_VXI_MEMACC =
            "^(?<INTERFACE>GPIB-VXI)(?<BOARD>\\d+)?::(?<RESOURCE>MEMACC)";
    public final static String GPIB_VXI_BACKPLANE =
            "^(?<INTERFACE>GPIB-VXI)(?<BOARD>\\d+)?(::(?<LOGICALADDRESS>\\d+))?::(?<RESOURCE>BACKPLANE)";

    public final static String GPIB_INSTR =
            "^(?<INTERFACE>GPIB)(?<BOARD>\\d+)?::(?<PRIMARYADDRESS>\\d+)(::(?<SECONDARYADDRESS>\\d+))?(::(?<RESOURCE>INSTR))?";
    public final static String GPIB_INTFC =
            "^(?<INTERFACE>GPIB)(?<BOARD>\\d+)?::(?<RESOURCE>INTFC)";
    public final static String GPIB_SERVANT =
            "^(?<INTERFCE>GPIB)(?<BOARD>\\d+)?::(?<RESOURCE>SERVANT)";

    public final static String PXI_INSTR_1 =
            "^(?<INTERFACE>PXI)(?<BOARD>\\d+)?::(?<DEVICE>\\d+)(::(?<FUNCTION>\\d+))?(::(?<RESOURCE>INSTR))?";
    public final  static String PXI_INSTR_2 =
            "^(?<INTERFACE>PXI)(?<BOARD>\\d+)?::((?<DEVICE>\\d+)-)?(?<FUNCTION>\\d+)(.(\\d+))?(::(?<RESOURCE>INSTR))?";
    public final static String PXI_MEMACC =
            "^(?<INTERFACE>PXI)(?<BOARD>\\d+)?::(?<RESOURCE>MEMACC)";

    public final static String SERIAL_INSTR =
            "^(?<INTERFACE>ASRL)(?<BOARD>\\d+)?(::(?<RESOURCE>INSTR))?";

    public final static String TCPIP_INSTR_1 =
            "^(?<INTERFACE>TCPIP)(?<BOARD>\\d+)?::(?<HOSTADDRESS>\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})(::(?<DEVICENAME>[^:]*))?(::(?<RESOURCE>INSTR))?";
    public final static String TCPIP_INSTR_2 =
            "^(?<INTERFACE>TCPIP)(?<BOARD>\\d+)?::(?<HOSTADDRESS>\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})::(?<PORT>\\d{1,5})::(?<RESOURCE>SOCKET)";

    public final static String USB_INSTR =
            "^(?<INTERFACE>USB)(?<BOARD>\\d+)?::(?<MANUFACTURERID>[^:]*)::(?<MODELCODE>[^:]*)::(?<SERIALNUMBER>[^:]*)(::(?<INTERFACENUMBER>\\d+))?(::(?<RESOURCE>INSTR))?";
    public final  static String USB_INSTR_RAW =
            "^(?<INTERFACE>USB)(?<BOARD>\\d+)?::(?<MANUFACTURERID>[^:]*)::(?<MODELCODE>[^:]*)::(?<SERIALNUMBER>[^:]*)(::(?<INTERFACENUMBER>\\d+))?(::(?<RESOURCE>RAW))?";

    private static final String[] patterns = new String[]
    {
        VisaAddressPatterns.VXI_INSTR,
        VisaAddressPatterns.VXI_MEMACC,
        VisaAddressPatterns.VXI_BACKPLANE,
        VisaAddressPatterns.VXI_SERVANT,

        VisaAddressPatterns.GPIB_VXI_INSTR,
        VisaAddressPatterns.GPIB_VXI_MEMACC,
        VisaAddressPatterns.GPIB_VXI_BACKPLANE,

        VisaAddressPatterns.GPIB_INSTR,
        VisaAddressPatterns.GPIB_INTFC,
        VisaAddressPatterns.GPIB_SERVANT,

        VisaAddressPatterns.PXI_INSTR_1,
        VisaAddressPatterns.PXI_INSTR_2,
        VisaAddressPatterns.PXI_MEMACC,

        VisaAddressPatterns.SERIAL_INSTR,

        VisaAddressPatterns.TCPIP_INSTR_1,
        VisaAddressPatterns.TCPIP_INSTR_2,

        VisaAddressPatterns.USB_INSTR,
        VisaAddressPatterns.USB_INSTR_RAW
    };

   public static String[] getAsArray()
    {
        return patterns;
    }
}

enum VisaInterfaceType
{
    VXI("VXI"),
    GPIB_VXI("GPIBVXI"),
    GPIB("GPIB"),
    PXI("PXI"),
    TCPIP("TCPIP"),
    ASRL("ASRL"),
    USB("USB"),
    NONE("NONE");

    private String type;

    VisaInterfaceType(String type)
    {
        this.type = type;
    }
}

enum VisaResourceType
{
    INSTR,
    MEMACC,
    BACKPLANE,
    SERVANT,
    INTFC,
    SOCKET,
    RAW,
    NONE
}
