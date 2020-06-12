public class Map2D
{
    /** Ширина карты. **/
    private int width;

    /**Высота карты **/
    private int height;

    /**
     * Фактические данные карты, по которым должен ориентироваться алгоритм поиска пути.
     **/
    private int[][] cells;

    /** Начальное местоположение для выполнения поиска пути A *. **/
    private Location start;

    /** Конечное местоположение для выполнения поиска пути A *. **/
    private Location finish;


    /** Создает новую 2D карту с указанными шириной и высотой. **/
    public Map2D(int width, int height)
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException(
                    "width and height must be positive values; got " + width +
                    "x" + height);
        }
        
        this.width = width;
        this.height = height;
        
        cells = new int[width][height];
        
        // Создание некоторых координат для начала и конца.
        start = new Location(0, height / 2);
        finish = new Location(width - 1, height / 2);
    }


    /**
     * Этот вспомогательный метод проверяет указанные координаты, чтобы увидеть, являются ли они
     * в пределах карты. Если координаты находятся не на карте
     * затем метод генерирует исключение <code> IllegalArgumentException </ code>.
     **/
    private void checkCoords(int x, int y)
    {
        if (x < 0 || x > width)
        {
            throw new IllegalArgumentException("x must be in range [0, " + 
                    width + "), got " + x);
        }
        
        if (y < 0 || y > height)
        {
            throw new IllegalArgumentException("y must be in range [0, " + 
                    height + "), got " + y);
        }
    }    
    
    /** Возвращает ширину карты **/
    public int getWidth()
    {
        return width;
    }
    
    /** Возвращает высоту карты **/
    public int getHeight()
    {
        return height;
    }
    
    /**
     * Возвращает true, если указанные координаты содержатся на карте.
     **/
    public boolean contains(int x, int y)
    {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }
    
    
    /** Returns true if the location is contained within the map area. **/
    public boolean contains(Location loc)
    {
        return contains(loc.xCoord, loc.yCoord);
    }
    
    /** Возвращает сохраненное значение для указанной ячейки. **/
    public int getCellValue(int x, int y)
    {
        checkCoords(x, y);
        return cells[x][y];
    }
    
    /** Возвращает сохраненное значение для указанной ячейки. **/
    public int getCellValue(Location loc)
    {
        return getCellValue(loc.xCoord, loc.yCoord);
    }
    
    /** Sets the cost value for the specified cell. **/
    public void setCellValue(int x, int y, int value)
    {
        checkCoords(x, y);
        cells[x][y] = value;
    }
    
    /**
     * Возвращает начальное местоположение для карты. Это где начало сгенерированного пути
     **/
    public Location getStart()
    {
        return start;
    }
    
    /**
     * Возвращает начальное местоположение для карты. Это где начало сгенерированного пути
     **/
    public void setStart(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc cannot be null");
        
        start = loc;
    }

    /**
     * Возвращает конечное местоположение для карты. Это где конец сгенерированного пути
     **/
    public Location getFinish()
    {
        return finish;
    }
    
    /**
     * Возвращает конечное местоположение для карты. Это где конец сгенерированного пути
     **/
    public void setFinish(Location loc)
    {
        if (loc == null)
            throw new NullPointerException("loc cannot be null");
        
        finish = loc;
    }
}