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
	private JButton saveButton;
    private JButton resetButton;
    private JComboBox ComboBox;
    private int rowsRemaining;
    private int DisplaySize;
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
        DisplaySize = size;
        Fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        Fractal.getInitialRange(range);
        Display = new JImageDisplay(DisplaySize, DisplaySize); 
    }
    public void createAndShowGUI() 
    /** Метод, который инициализирует графический интерфейс Swing */
    {
    	Display.setLayout(new BorderLayout());
        JFrame JimageDisplay = new JFrame("Fractal Explorer");
        JimageDisplay.add(Display, BorderLayout.CENTER);
        
        resetButton = new JButton("Reset Image");
        ButtonHandler handler = new ButtonHandler();
        resetButton.addActionListener(handler);
        JimageDisplay.add(resetButton, BorderLayout.SOUTH);
        
        MouseHandler click = new MouseHandler();
        Display.addMouseListener(click);
        JimageDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ComboBox = new JComboBox();
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

        saveButton = new JButton("Save Image");
        JPanel BottomPanel = new JPanel();
        BottomPanel.add(saveButton);
        BottomPanel.add(resetButton);
        JimageDisplay.add(BottomPanel, BorderLayout.SOUTH);
        
        
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        JimageDisplay.pack();
        JimageDisplay.setVisible(true);
        JimageDisplay.setResizable(false);
    }
    
    private void enableUI(boolean val) 
    {
        ComboBox.setEnabled(val);
        resetButton.setEnabled(val);
        saveButton.setEnabled(val);
    }
    private class FractalWorker extends SwingWorker<Object, Object> 
    {
        int coordY;
        int[] rgbVal;
        private FractalWorker(int y) 
        {
            coordY = y;
        }
        protected Object doInBackground() 
        {
            rgbVal = new int[DisplaySize];
            for (int i = 0; i < rgbVal.length; i++) 
            {
                double xCoord = Fractal.getCoord(range.x, range.x + range.width, DisplaySize, i);
                double yCoord = Fractal.getCoord(range.y, range.y + range.height, DisplaySize, coordY);
                int iter = Fractal.numIterations(xCoord, yCoord);
                if (iter == -1)
                    rgbVal[i] = 0;
                else 
                {
                    float hue = 0.7f + (float) iter / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    rgbVal[i] = rgbColor;
                }
            }
            return null;
        }
        protected void done() {
            for (int i = 0; i < rgbVal.length; i++) 
            {
                Display.drawPixel(i, coordY, rgbVal[i]);
            }
            Display.repaint(0, 0, coordY, DisplaySize, 1); // Метод, который позволяет указать область для перерисовки фрактала
            rowsRemaining--;
            if (rowsRemaining == 0) 
            {
                enableUI(true);
            }
        }
    }
    private void drawFractal() 
    /** Метод, который должен циклически проходить через каждый пиксель в отображении */
    {
    	enableUI(false);
    	rowsRemaining = DisplaySize;
        for (int x = 0; x < DisplaySize; x++) 
        {
        	FractalWorker drawRow = new FractalWorker(x);
            drawRow.execute();
        }
            
    }
    private class ButtonHandler implements ActionListener 
    /** Внутренний класс для обработки событий 
     * java.awt.event.ActionListener от кнопки сброса */
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
        	if (rowsRemaining != 0)
        		return;
            int x = e.getX();
            double xCoord = Fractal.getCoord(range.x, range.x + range.width, DisplaySize, x);
            int y = e.getY();
            double yCoord = Fractal.getCoord(range.y, range.y + range.height, DisplaySize, y);
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