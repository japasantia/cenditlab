package ve.gob.cendit.cenditlab.data;

/**
 * Created by jarias on 23/08/17.
 */
public enum DataDirection
{
    INPUT("Input"),
    OUTPUT("Output"),
    BIDIRECTIONAL("Bidirectional");

    private String name;

    DataDirection(String name)
    {
        this.name = name;
    }
}
