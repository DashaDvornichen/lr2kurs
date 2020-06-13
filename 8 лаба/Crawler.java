import java.io.*;
import java.net.*;

public class Crawler implements Runnable 
{
    final static int AnyDepth = 0;
    private URLPool urlPool;

    @Override
    public void run()
    {
        try 
        {
            Scan();
        } 
        catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
    /** Конструктор для инициализации пула */
    public Crawler(URLPool pool) 
    {
        this.urlPool = pool;
    }

    private  void  Scan() throws IOException, InterruptedException 
    {
        try 
        {
            while (true) 
            {
                Process(urlPool.get());
            }
        }
        catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
        }
    }
    /** Метод, который выполняет сканирование начальной ссылки, затем отправляет найденные ссылки в пул
     * и переносит их из списка найденных в обработанные */
    private void Process(URLDepthPair pair) throws IOException
    {
        URL url = new URL(pair.getURL());
        URLConnection connection = url.openConnection();
        String redirect = connection.getHeaderField("Location");
        if (redirect != null) 
        {
            connection = new URL(redirect).openConnection();
        }
        urlPool.addProcessed(pair);
        if (pair.getDepth() == 0) return;
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream())); //считывает текст из потока
        String line;
        while ((line = in.readLine()) != null) 
        {
            String prefix = "http";
            while (line.contains("a href=\"" + prefix)) 
            {
                line = line.substring(line.indexOf("a href=\"" + prefix) + 8);
                String link = line.substring(0, line.indexOf('\"'));
                if(link.contains(" "))
                    link = link.replace(" ", "%20");
                if (urlPool.getNotProcessed().contains(new URLDepthPair(link, AnyDepth)) ||
                    urlPool.getResult().contains(new URLDepthPair(link, AnyDepth))) continue;
                urlPool.addNotProcessed(new URLDepthPair(link, pair.getDepth() - 1));
            }
        }
        in.close();
    }
    public static void main(String args[]) throws IOException, InterruptedException 
    {
        args = new String[]{"http://chudo-ostriv.com.ua/","2","2"};
        URLPool pool = new URLPool(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        for (int i = 0; i < Integer.parseInt(args[2]); i++) 
        {
            Crawler crawler = new Crawler (pool);
            new Thread(crawler).start();
            System.out.println("Crawler " + i + " running");
        }
    }
}