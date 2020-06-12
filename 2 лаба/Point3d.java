import static java.lang.Math.*;

public class Point3d 
{
    private double xCoord;
    private double yCoord;
    private double zCoord;

    public Point3d(double x, double y, double z) 
    //Конструктор инициализации
    {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    public Point3d() 
    //Конструктор по умолчанию/вызовите конструктор с 2 параметрами
    {
        this(0.0,0.0,0.0);
    }
    public double getX()
    //возвращение координаты Х
    {
        return xCoord;
    }
    public double getY()
    //возвращение координаты Y
    {
        return yCoord;
    }
    public double getZ()
    //возвращение координаты Z
    {
        return zCoord;
    }
    public void setX(double val)
    //установка значения координаты X
    {
        xCoord = val;
    }
    public void setY(double val)
    //установка значения координаты Y
    {
        yCoord = val;
    }
    public void setZ(double val)
    //установка значения координаты Z
    {
        zCoord = val;
    }
    public static boolean equal_points(Point3d p1, Point3d p2)
    {
        return ((((p1.xCoord == p2.xCoord))&&(p1.yCoord == p2.yCoord))&&(p1.zCoord == p2.zCoord));
    }
    public static double distanceTo(Point3d p1,Point3d p2)
    //нахождение длины между 2 точками
    {
        return round((sqrt(pow(p2.xCoord - p1.xCoord, 2) + pow(p2.yCoord - p1.yCoord, 2) + pow(p2.zCoord - p1.zCoord, 2)))*100.0)/100.0;
    }
}