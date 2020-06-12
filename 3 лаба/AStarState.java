import java.util.*;

/**
 * Этот класс хранит базовое состояние, необходимое для алгоритма A * для вычисления
 * пути по карте. Это состояние включает в себя коллекцию «открытых путевых точек» и
 * другая коллекция "закрытых путевых точек". Кроме того, этот класс обеспечивает
 * основные операции, необходимые алгоритму поиска пути A * для его
 * обработки.
 **/
public class AStarState
{
    /** Это ссылка на карту, по которой движется алгоритм A *. **/
    private Map2D map;
    private HashMap<Location, Waypoint> open_waypoints = new HashMap<>();
	private HashMap<Location, Waypoint> closed_waypoints = new HashMap<>();


    /**
     * Инициализируйте новый объект состояния для алгоритма поиска пути A *.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Возвращает карту, по которой перемещается указатель пути A *. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Этот метод просматривает все открытые путевые точки и возвращает путевую точку
     * с минимальной общей стоимостью. Если нет открытых путевых точек, этот метод
     * возвращает <code> ноль </ code>.
     **/
    public Waypoint getMinOpenWaypoint() 
    {
        if (numOpenWaypoints() == 0)
            return null;
            
    	 float min_distance = Float.MAX_VALUE;
         Waypoint min_waypoint = null;

         for (Map.Entry<Location, Waypoint> entry : open_waypoints.entrySet()) 
         {
             Waypoint curr_waypoint = entry.getValue();
             float curr_distance = curr_waypoint.getTotalCost();
             if (curr_distance < min_distance) 
             {
                 min_distance = curr_distance;
                 min_waypoint = curr_waypoint;
             }
         }
         return min_waypoint;
    }

    /**
     * Этот метод добавляет путевую точку к (или потенциально обновляет путевую точку уже
     * в) коллекция "открытых путевых точек". Если там уже нет открытого
     * путевая точка на месте новой путевой точки, тогда новая путевая точка просто
     * добавлено в коллекцию. Однако, если уже есть путевая точка на
     * местоположение новой путевой точки, новая путевая точка заменяет только старую <em>
     * если </ em> значение "предыдущей стоимости" новой путевой точки меньше текущей
     * значение «предыдущей стоимости» путевой точки.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location loc = newWP.getLocation();

        if (open_waypoints.containsKey(loc))
        {
            Waypoint current_waypoint = open_waypoints.get(loc);
            if (newWP.getPreviousCost() < current_waypoint.getPreviousCost())
            {
                return true;
            }
            return false;
        }
        open_waypoints.put(loc, newWP);
        return true;
    }


    /** Возвращает текущее количество открытых путевых точек. **/
    public int numOpenWaypoints()
    {
        return open_waypoints.size();
    }


    /**
     * Этот метод перемещает путевую точку в указанном месте из
     * открыть список к закрытому списку.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint waypoint = open_waypoints.remove(loc);
        closed_waypoints.put(loc, waypoint);
    }

    /**
     * Возвращает true, если коллекция закрытых путевых точек содержит путевую точку
     * для указанного места.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closed_waypoints.containsKey(loc);
    }
}