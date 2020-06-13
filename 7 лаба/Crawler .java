import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Crawler 
{
    static LinkedList <URLDepthPair> findLink = new LinkedList <URLDepthPair>();
    static LinkedList <URLDepthPair> viewedLink = new LinkedList <URLDepthPair>();

    public static void showResult(LinkedList<URLDepthPair> viewedLink)
    {
        for (URLDepthPair c : viewedLink)
            System.out.println("Depth : " + c.getDepth() + "\tLink : " + c.getURL());
    }

    /* Составление запроса на сервер сайта*/
    public static void request(PrintWriter out,URLDepthPair pair) throws MalformedURLException 
    {
        out.println("GET " + pair.getDocPath() + " HTTP/1.1"); 
        out.println("Host: " + pair.getWebHost());
        out.println("Connection: close");
        out.println();
        out.flush(); // очищение буфера
    }

    public static void Scan(String pair, int maxDepth) throws IOException 
    {
        findLink.add(new URLDepthPair(pair, 0)); //добавляем ссылку
        while (!findLink.isEmpty()) 
        {
            URLDepthPair currentPair = findLink.removeFirst(); //удаление 1 элемента списка
            if (currentPair.depth < maxDepth)
            {
                Socket mySocket = new Socket(currentPair.getWebHost(), 80); //подключение сокета
                mySocket.setSoTimeout(5000); // устанавливает время ожидания сокета на 7 секунд
                BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream())); //считывает текст из потока
                PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true); //выводит инф-ю в буфер вывода
                request(out, currentPair);
                String line;
                while ((line = in.readLine()) != null) 
                {
                    if (line.indexOf(currentPair.URL_PREFIX) != -1) 
                    {
                        StringBuilder currentLink = new StringBuilder(); //работа с потоком ввода-вывода
                        for (int i = line.indexOf(currentPair.URL_PREFIX); line.charAt(i) != '"'; i++) 
                        {
                            currentLink.append(line.charAt(i)); //добавляет в список
                        }
                        URLDepthPair newPair = new URLDepthPair(currentLink.toString(), currentPair.depth + 1);
                        if (currentPair.check(findLink, newPair) && currentPair.check(viewedLink, newPair) && !currentPair.URL.equals(newPair.URL)) //проверка на совпадение url-адресов
                            findLink.add(newPair);
                    }
                }
                mySocket.close(); // закрытие сокета
            }
            viewedLink.add(currentPair); // Добавление ссылки в список URLs 
        }
        showResult(viewedLink);
    }
    public static void main(String[] args) 
    {
        String[] arg = new String[]{"http://chudo-ostriv.com.ua/","3"};
        try 
        {
            Scan(arg[0], Integer.parseInt(arg[1]));
        } 
        catch (NumberFormatException | IOException e) 
        {
            System.out.println("usage: java Crawler <URL><depth>");
        }
    }
}