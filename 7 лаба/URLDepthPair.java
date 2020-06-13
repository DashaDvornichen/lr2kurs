import java.util.LinkedList;
import java.net.MalformedURLException; //позволяет обработать исключния для неверного URL-адреса
import java.net.URL;

/** Класс, хранящий пары <URL, глубина> для класса Crawler */
public class URLDepthPair 
{
    public static final String URL_PREFIX = "http:";

    public String URL;
    public int depth;

    public URLDepthPair (String URL, int depth)
    {
        this.URL = URL;
        this.depth = depth;
    }
    /* Метод для доступа к строке вида <глубина, URL> */
    @Override
    public String toString() {
        
        String stringDepth = Integer.toString(depth);
        return stringDepth + '\t' + depth;
    }
    /* Метод возвращает хост текущего URL */
    public String getWebHost() throws MalformedURLException 
    {
        URL host = new URL(URL);
        return host.getHost();
    }
    /* Метод преобразует текущий URL в путь к файлу */
    public String getDocPath() throws MalformedURLException 
    {
        URL path = new URL(URL);
        return path.getPath();
    }
    
    /* Метод для доступа к текущей глубине */
    public int getDepth() 
    {
        return depth;
    }
    /* Метод для доступа к текущему URL */
    public String getURL() 
    {
        return URL;
    }
    public static boolean check(LinkedList<URLDepthPair> resultLink, URLDepthPair pair) //проверяет совпадение url-адресов
    {
        boolean isAlready = true;
        for (URLDepthPair c : resultLink)
            if (c.getURL().equals(pair.getURL()))
                isAlready = false;
        return isAlready;
    }
}