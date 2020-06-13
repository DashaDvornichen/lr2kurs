import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;


public class JImageDisplay extends JComponent 
{
	private BufferedImage img;
	public BufferedImage getImage() 
	{
	    return img;
	}
	public JImageDisplay(int x, int y) 
	/** Конструктор класса **/
	{
		img = new BufferedImage(x,y,TYPE_INT_RGB); //ширина высота и тип изображения
		setPreferredSize(new Dimension(x, y));
	}
	public void paintComponent (Graphics g)
	/** Метод для отрисовки фракталов **/ 
	{
		super.paintComponent (g);
		g.drawImage (img, 0, 0, img.getWidth(), img.getHeight(), null);
	}
	public void drawPixel (int x, int y, int rgbColor)
	/** Метод для установки всех пикселей изображения в определенный цвет */
	{
		img.setRGB(x, y, rgbColor);
	}
}