package ve.gob.cendit.cenditlab.io.visa;


/**
 * Created by jarias on 20/06/17.
 */
public enum VisaAddressFields
{
    INTERFACE("INTERFACE"),
    BOARD("BOARD"),
    LOGICAL_ADDRESS("LOGICALADDRESS"),
    RESOURCE("RESOURCE"),
    PRIMARY_ADDRESS("PRIMARYADDRESS"),
    SECONDARY_ADDRESS("SECONDARYADDRESS"),
    DEVICE("DEVICE"),
    FUNCTION("FUNCTION"),
    DEVICE_NAME("DEVICENAME"),
    HOST_ADDRESS("HOSTADDRESS"),
    PORT("PORT"),
    MANUFACTURER_ID("MANUFACTURERID"),
    MODEL_CODE("MODELCODE"),
    SERIAL_NUMBER("SERIALNUMBER"),
    INTERFACE_NUMBER("INTERFACENUMBER");

    private String fieldName;

    VisaAddressFields(String name)
    {
        fieldName = name;
    }

    public String getName()
    {
        return fieldName;
    }

    public static String[] getAsArray()
    {
        return fieldNames;
    }

    private static final String[] fieldNames = new String[]
    {
            VisaAddressFields.INTERFACE.getName(),
            VisaAddressFields.BOARD.getName(),
            VisaAddressFields.LOGICAL_ADDRESS.getName(),
            VisaAddressFields.RESOURCE.getName(),
            VisaAddressFields.PRIMARY_ADDRESS.getName(),
            VisaAddressFields.SECONDARY_ADDRESS.getName(),
            VisaAddressFields.DEVICE.getName(),
            VisaAddressFields.FUNCTION.getName(),
            VisaAddressFields.DEVICE_NAME.getName(),
            VisaAddressFields.PORT.getName(),
            VisaAddressFields.MANUFACTURER_ID.getName(),
            VisaAddressFields.MODEL_CODE.getName(),
            VisaAddressFields.SERIAL_NUMBER.getName(),
            VisaAddressFields.INTERFACE_NUMBER.getName()
    };
}
