import java.util.Scanner;
import static java.lang.Math.*;


public class Lab2
{
    public static void main(String args[])
    {
        //Ввод данных
        System.out.println("Введите координаты первой точки через пробел");
        Scanner in = new Scanner(System.in);
        String firstPoint = in.nextLine(); 
        String[] sub_str1 = firstPoint.split(" ");
        double x1 = Double.valueOf(sub_str1[0]);
        double y1 = Double.valueOf(sub_str1[1]);
        double z1 = Double.valueOf(sub_str1[2]);
        System.out.println("Введите координаты второй точки через пробел");
      
        String secondPoint = in.nextLine(); 
        String[] sub_str2 = secondPoint.split(" ");
        double x2 = Double.valueOf(sub_str2[0]);
        double y2 = Double.valueOf(sub_str2[1]);
        double z2 = Double.valueOf(sub_str2[2]);
        System.out.println("Введите координаты третьей точки через пробел");
  
        String thirdPoint = in.nextLine(); 
        String[] sub_str3 = thirdPoint.split(" ");
        double x3 = Double.valueOf(sub_str3[0]);
        double y3 = Double.valueOf(sub_str3[1]);
        double z3 = Double.valueOf(sub_str3[2]);

        Point3d FirstPoint = new Point3d(x1,y1,z1); 
        Point3d SecondPoint = new Point3d(x2,y2,z2);
        Point3d ThirdPoint = new Point3d(x3,y3,z3);
        // вызов функций


        if (Point3d.equal_points(FirstPoint,SecondPoint) || Point3d.equal_points(FirstPoint,ThirdPoint) || Point3d.equal_points(SecondPoint,ThirdPoint))
        {
            System.out.println("Невозможно вычислить площадь треугольника: значения точек равны");
        }
        else //вычисление площади треугольника
        {
            double S = computeArea(FirstPoint,SecondPoint,ThirdPoint);
            System.out.println("Площадь треугольника равна: " + S);
        }
    }


    public static double computeArea(Point3d p1, Point3d p2,Point3d p3) 
     //нахождение площади треугольника с помощью формулы Герона
    {   
        double S,p;
        double a,b,c;
        
        a = Point3d.distanceTo(p1,p2); 
        b = Point3d.distanceTo(p2,p3); 
        c = Point3d.distanceTo(p3,p1); 
        p = (a+b+c)/2;
        S = round((sqrt(p*(p-a)*(p-b)*(p-c))*100.0))/100.0;
    
        return S;
    }
}