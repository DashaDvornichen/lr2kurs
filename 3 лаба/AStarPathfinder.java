import java.util.HashMap;
import java.util.HashSet;


/**
 * Этот класс содержит реализацию алгоритма поиска пути A *. 
 * алгоритм реализован как статический метод, так как алгоритм поиска пути
 * на самом деле не нужно поддерживать какое-либо состояние между вызовами
 * алгоритма
 */
public class AStarPathfinder
{
    /**
     * Эта константа содержит максимальный предел отсечения для стоимости путей. Если
     * конкретная путевая точка превышает этот предел стоимости, путевая точка
     * отбрасывается
     **/
    public static final float COST_LIMIT = 1e6f;

    
    /**
     * Попытки вычислить путь, который перемещается между началом и концом
     * местоположения указанной карты. Если путь может быть найден, путевая точка
     * возвращается <em> последний </ em> шаг в пути; эта точка может быть
     * используется, чтобы идти назад к начальной точке. Если путь не найден,
     * <code> null </ code> возвращается.
     **/
    public static Waypoint computePath(Map2D map)
    {
        // Переменные, необходимые для поиска A *.
        AStarState state = new AStarState(map);
        Location finishLoc = map.getFinish();

        // Установить начальную путевую точку, чтобы начать поиск A *.
        Waypoint start = new Waypoint(map.getStart(), null);
        start.setCosts(0, estimateTravelCost(start.getLocation(), finishLoc));
        state.addOpenWaypoint(start);

        Waypoint finalWaypoint = null;
        boolean foundPath = false;
        
        while (!foundPath && state.numOpenWaypoints() > 0)
        {
            // Найти «лучшую» (т.е. самую дешевую) путевую точку на данный момент.
            Waypoint best = state.getMinOpenWaypoint();
            
            // Если лучшая локация - финишная, то все готово!
            if (best.getLocation().equals(finishLoc))
            {
                finalWaypoint = best;
                foundPath = true;
            }
            
            // Добавить/обновить всех соседей текущего лучшего местоположения. Это
            // эквивалентно выполнению всех «следующих шагов» из этого места.
            takeNextStep(best, state);
            
            // Наконец, переместите это место из списка «открыто» в «закрыто»
            // список.
            state.closeWaypoint(best.getLocation());
        }
        
        return finalWaypoint;
    }

    /**
     * Этот статический вспомогательный метод берет путевую точку и генерирует все действительные
     * шаги "от этой путевой точки. Новые путевые точки добавляются к" открытой
     * waypoints "коллекция переданного A * объекта состояния.
     **/
    private static void takeNextStep(Waypoint currWP, AStarState state)
    {
        Location loc = currWP.getLocation();
        Map2D map = state.getMap();
        
        for (int y = loc.yCoord - 1; y <= loc.yCoord + 1; y++)
        {
            for (int x = loc.xCoord - 1; x <= loc.xCoord + 1; x++)
            {
                Location nextLoc = new Location(x, y);
                
                // Если «следующая локация» находится вне карты, пропустите ее.
                if (!map.contains(nextLoc))
                    continue;
                
                // Если это следующее местоположение, пропустите его.
                if (nextLoc == loc)
                    continue;
                
                // Если это место уже находится в «закрытом» наборе
                // затем продолжаем со следующего местоположения.
                if (state.isLocationClosed(nextLoc))
                    continue;

                // Сделать путевую точку для этого «следующего местоположения».
                
                Waypoint nextWP = new Waypoint(nextLoc, currWP);
                
                // Хорошо, мы обманываем и используем оценку стоимости для вычисления фактического
                // стоимость из предыдущей ячейки. Затем мы добавляем в стоимость от
                // ячейка карты, на которую мы ступаем, для включения барьеров и т. д.

                float prevCost = currWP.getPreviousCost() +
                    estimateTravelCost(currWP.getLocation(),
                                       nextWP.getLocation());

                prevCost += map.getCellValue(nextLoc);
                
                // Пропустить это «следующее место», если оно слишком дорого.
                if (prevCost >= COST_LIMIT)
                    continue;
                
                nextWP.setCosts(prevCost,
                    estimateTravelCost(nextLoc, map.getFinish()));

                // Добавить путевую точку к множеству открытых путевых точек. Если там
                // случается, уже является путевой точкой для этого местоположения, новый
                // путевая точка заменяет только старую путевую точку, если она дешевле
                // чем старый.
                state.addOpenWaypoint(nextWP);
            }
        }
    }
    
    /**
     * Оценивает стоимость поездки между двумя указанными местами.
     * Фактическая стоимость - это прямое расстояние между
     * два места.
     **/
    private static float estimateTravelCost(Location currLoc, Location destLoc)
    {
        int dx = destLoc.xCoord - currLoc.xCoord;
        int dy = destLoc.yCoord - currLoc.yCoord;
        
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}