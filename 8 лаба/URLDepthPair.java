import java.util.Objects;

/** Класс, хранящий пары <URL, глубина> для класса Crawler */
public class URLDepthPair 
{
    private String Url;
    private int Depth;

    public URLDepthPair(String url, int depth) 
    {
        Url = url;
        Depth = depth;
    }
    /** Метод, проверяющий равенство двух объектов URLDepthPair */
    @Override
    public boolean equals(Object obj) 
    {
        if (obj instanceof URLDepthPair) 
        {
            URLDepthPair o = (URLDepthPair)obj;
            return this.Url.equals(o.getURL());
        }
        return false;
    }
    /* Метод для доступа к текущему URL */
    public String getURL() 
    {
        return Url;
    }
    /* Метод для доступа к текущей глубине */
    public int getDepth() 
    {
        return Depth;
    }
}