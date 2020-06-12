public class Location
{
    /** X координата этого места. **/
    public int xCoord;

    /** Y координата этого места. **/
    public int yCoord;


    /** Создает новое местоположение с указанными целочисленными координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создает новое местоположение с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }
    @Override
    public boolean equals(Object p1)
    {
        if (this == p1)
            return true;
        if (p1 == null || p1.getClass() != this.getClass())
            return false;
        
        
        Location location = (Location) p1;
            return xCoord == location.xCoord &&
                    yCoord == location.yCoord;
    }
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + xCoord;
        result = prime * result + yCoord;
        return result;
    }

}