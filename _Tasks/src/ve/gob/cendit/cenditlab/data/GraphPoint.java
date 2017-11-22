package ve.gob.cendit.cenditlab.data;


public class GraphPoint
{
    public Number x;
    public Number y;

    public GraphPoint(Object x, Object y)
    {
        if (x != null && y != null && x instanceof Number && y instanceof Number)
        {
            this.x = (Number)x;
            this.y = (Number)y;
        }
        else
        {
            throw new IllegalArgumentException("x and y must not be null and must be Number or Number subclass");
        }
    }

    public GraphPoint(Number x, Number y)
    {
        this.x = x;
        this.y = y;
    }

    public void set(Number x, Number y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(Number value)
    {
        x = value;
    }

    public void setY(Number value)
    {
        y = value;
    }

    public Number getX()
    {
        return x;
    }

    public Number getY()
    {
        return y;
    }

    @Override
    public String toString()
    {
        return String.format("%.3f, %.3f", getX().floatValue(), getY().floatValue());
    }
}
