import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator
{
	public static final int MAX_ITERATIONS = 2000;

	/** Метод, позволяющий генератору фракталов определить наиболее «интересную»
	* область комплексной плоскости для конкретного фрактала */
	public void getInitialRange(Rectangle2D.Double range) 
	{
	    range.x = -2;
	    range.y = -2.5;
	    range.height = 4;
	    range.width = 4;
	}
	/** Метод реализует итеративную функцию для фрактала Burning ship */
	public int numIterations(double x, double y) 
	{
	    double re = 0;
		double im = 0;
		
		for(int i=0;i<MAX_ITERATIONS;i++)
		{
	        double re2 = re * re;
	        double im2 = im * im;
			if (re2 + im2 > 4)
			{
	            return i;
	        }
	        double nxtRe = re2- im2 +x;
	        double nxtIm = 2 * Math.abs(re * im) + y;
	    
	        re = nxtRe;
	        im = nxtIm;
	    }
	    return -1;
	}
	public String toString() 
	{
	    return "Burning ship";
	}	
}