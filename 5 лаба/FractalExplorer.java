import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FractalExplorer 
{
	  
    private int display_size;
    /* Целое число «размер экрана», которое является 
        шириной и высотой отображения в пикселях */
	
    private JImageDisplay Display;
    /* Ссылка JImageDisplay, для обновления отображения в разных методах в
        процессе вычисления фрактала */

    private FractalGenerator Fractal; 
    //Объект FractalGenerator

    private Rectangle2D.Double range;
    /* Объект Rectangle2D.Double, указывающий диапазон
        комплексной плоскости, которая выводится на экран */
   
    public FractalExplorer(int size) 
    /* Конструктор, который принимает значение размера отображения в качестве аргумента, 
        затем сохраняет это значение в соответствующем поле, а также инициализирует объекты диапазона и фрактального генератора */
    {
        display_size = size;
        Fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        Fractal.getInitialRange(range);
        Display = new JImageDisplay(display_size, display_size); 
    }
    /** Метод, который инициализирует графический интерфейс Swing */
    public void createAndShowGUI() 
    {
        Display.setLayout(new BorderLayout());
        JFrame JimageDisplay = new JFrame("Fractal Explorer");
        JimageDisplay.add(Display, BorderLayout.CENTER);
        JButton resetButton = new JButton("Reset Image");
        ButtonHandler handler = new FractalExplorer.ButtonHandler();
        resetButton.addActionListener(handler);
        JimageDisplay.add(resetButton, BorderLayout.SOUTH);

        MouseHandler click = new FractalExplorer.MouseHandler();
        Display.addMouseListener(click);
        JimageDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComboBox ComboBox = new JComboBox();
        FractalGenerator mandelbrotFractal = new Mandelbrot();
        ComboBox.addItem(mandelbrotFractal);
        FractalGenerator tricornFractal = new Tricorn();
        ComboBox.addItem(tricornFractal);
        FractalGenerator burningShipFractal = new BurningShip();
        ComboBox.addItem(burningShipFractal);
        //кнопка выбора
        ButtonHandler fractalChooser = new ButtonHandler();
        ComboBox.addActionListener(fractalChooser);
        JPanel DisplayPanel = new JPanel();
        JLabel myLabel = new JLabel("Fractal:");
        DisplayPanel.add(myLabel);
        DisplayPanel.add(ComboBox);
        JimageDisplay.add(DisplayPanel, BorderLayout.NORTH);
        JButton saveButton = new JButton("Save Image");
        JPanel myBottomPanel = new JPanel();
        myBottomPanel.add(saveButton);
        myBottomPanel.add(resetButton);
        JimageDisplay.add(myBottomPanel, BorderLayout.SOUTH);

        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        JimageDisplay.pack();
        JimageDisplay.setVisible(true);
        JimageDisplay.setResizable(false);
    }
    /** Метод, который должен циклически проходить через каждый пиксель в отображении */
    private void drawFractal() 
    { 
        for (int x = 0; x < display_size; x++) 
        {
            for (int y = 0; y < display_size; y++) 
            { 
                double xCoord = Fractal.getCoord(range.x, range.x + range.width, display_size, x);
                double yCoord = Fractal.getCoord(range.y, range.y + range.height, display_size, y);
                int iteration = Fractal.numIterations(xCoord, yCoord);

                if (iteration == -1) 
                { 
                    Display.drawPixel(x, y, 0);
                }
                else 
                {
                    float hue = 0.7f + (float) iteration / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    Display.drawPixel(x, y, rgbColor);
                }
            }
        }
        Display.repaint(); 
    }
    /** Внутренний класс для обработки событий 
     * java.awt.event.ActionListener от кнопки сброса */
    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            String command = e.getActionCommand();
            if (e.getSource() instanceof JComboBox) 
            {
                JComboBox mySource = (JComboBox) e.getSource();
                Fractal = (FractalGenerator) mySource.getSelectedItem();
                Fractal.getInitialRange(range);
                drawFractal();
            }
            else if (command.equals("Reset Image")) 
            {
                Fractal.getInitialRange(range);
                drawFractal();
            }
            else if (command.equals("Save Image")) 
            {
                JFileChooser myFileChooser = new JFileChooser();
                FileFilter extensionFilter = new FileNameExtensionFilter("PNG Images", "png");
                myFileChooser.setFileFilter(extensionFilter);
                myFileChooser.setAcceptAllFileFilterUsed(false);
                int userSelection = myFileChooser.showSaveDialog(Display);
                if (userSelection == JFileChooser.APPROVE_OPTION) 
                {
                    //для  загрузки и сохранения изображения
                    java.io.File file = myFileChooser.getSelectedFile();
                    String file_name = file.toString();
                    try 
                    {
                        BufferedImage displayImage = Display.getImage();
                        javax.imageio.ImageIO.write(displayImage, "png", file);
                    }
                    catch (Exception exception) 
                    {
                        JOptionPane.showMessageDialog(Display, exception.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else return;
            }
        }
    }

    private class MouseHandler extends MouseAdapter 
    {
        @Override 
        public void mouseClicked(MouseEvent e) 
        {
            int x = e.getX();
            double xCoord = Fractal.getCoord(range.x, range.x + range.width, display_size, x);
            int y = e.getY();
            double yCoord = Fractal.getCoord(range.y, range.y + range.height, display_size, y);
            Fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
    public static void main(String[] args)
    {
        FractalExplorer DisplayExplorer = new FractalExplorer(650);
        DisplayExplorer.createAndShowGUI();
        DisplayExplorer.drawFractal();
    }
}