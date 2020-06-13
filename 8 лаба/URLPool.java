import java.util.LinkedList;

/** Этот класс сохраняет пары <URL, глубина> для дальнейшего поиска */
public class URLPool 
{

    private LinkedList<URLDepthPair> findLink = new LinkedList<URLDepthPair>();
    private LinkedList<URLDepthPair> viewedLink = new LinkedList<URLDepthPair>();
   
    private int Depth;
    private int Wait;
    private int Threads;

    public URLPool(String url, int depth, int threads) 
    {
        findLink.add(new URLDepthPair(url, depth));
        Depth = depth;
        Threads = threads;
    }
    /** Метод, который выполняет поиск ссылок по заданной ссылке и убирает данную ссылку
     *  из списка найденных */
    public synchronized URLDepthPair get() throws InterruptedException 
    {
        if (findLink.size() == 0) 
        {
            Wait++;
            if (Wait == Threads) 
            {
                getSites();
                System.exit(0);
            }
            wait();
        }
        return findLink.removeFirst(); //удаление 1 элемента списка
    }
    /** Метод, который добавляет ссылку в список найденных ссылок и ожидающий ее обработку */
    public synchronized void addNotProcessed(URLDepthPair pair) 
    {
        findLink.add(pair);
        if (Wait > 0) 
        {
            Wait--;
            notify();
        }
    }
    /** Метод, который выводит на экран обработанные ссылки и подсчитывает их общее количество */
    public void getSites() 
    {
        System.out.println("Depth: " + Depth);
        for (int i = 0; i < viewedLink.size(); i++) 
        {
            System.out.println( Depth - viewedLink.get(i).getDepth() + " " +  viewedLink.get(i).getURL());
        }
        System.out.println("Links visited: " + viewedLink.size());
    }
    /** Метод, который добавляет ссылку в список использованных */
    public void addProcessed(URLDepthPair pair) 
    {
        viewedLink.add(pair);
    }
    /** Метод, который возвращает список обработанных ссылок */
    public LinkedList<URLDepthPair> getResult()
    {
        return viewedLink;
    }
    /** Метод, который возвращает список найденных ссылок */
    public LinkedList<URLDepthPair> getNotProcessed() 
    {
        return findLink;
    }
}