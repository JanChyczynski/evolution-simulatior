package agh.ics.oop;


import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

import static java.util.Objects.isNull;

public class MapBoundary implements IPositionChangeObserver {
    private final SortedMap<Vector2d, Integer> horizontal = new TreeMap<Vector2d, Integer>(Comparator
            .comparing(Vector2d::x)
            .thenComparing(Vector2d::y));
    private final SortedMap<Vector2d, Integer> vertical = new TreeMap<Vector2d, Integer>(Comparator
            .comparing(Vector2d::y)
            .thenComparing(Vector2d::x));

    public void add(Vector2d position){
        for (SortedMap<Vector2d, Integer> map : Arrays.asList(horizontal, vertical)){
            map.merge(position, 1, Integer::sum); //I can't believe this syntax is so weird, where's my map[position]++?;
        }
    }
    public void remove(Vector2d position) {
        for (SortedMap<Vector2d, Integer> map : Arrays.asList(horizontal, vertical)) {
            map.merge(position, -1, Integer::sum);
            if (map.get(position) <= 0) {
                map.remove(position);
            }
        }
    }

    public Vector2d bottomLeftCorner(){
        return new Vector2d(
                horizontal.isEmpty()? 0 : horizontal.firstKey().x(),
                vertical.isEmpty()? 0 : vertical.firstKey().y()
        );
    }

    public Vector2d upperRightCorner(){
        return new Vector2d(
                horizontal.isEmpty()? 0 : horizontal.lastKey().x(),
                vertical.isEmpty()? 0 : vertical.lastKey().y()
        );
    }

    @Override
    public void positionChanged(Vector2d start, Vector2d end, IMapElement movedElement) {
        remove(start);
        add(end);
    }
}

//    private final Map<MapDirection, Integer> boundaryCoordinate = new EnumMap<MapDirection, Integer>(MapDirection.class);
//    private final Map<MapDirection, Integer> objectsOnBoundaryCounter = new EnumMap<MapDirection, Integer>(MapDirection.class);
//
//    private boolean isOnBoundary(MapDirection direction, IMapElement element){
//
//    }
//
//    @Override
//    public void positionChanged(Vector2d start, Vector2d end, IMapElement movedElement) {
//        for(MapDirection direction : MapDirection.values()){
//            if(direction.toUnitVector().multiply(boundaryCoordinate[direction]) == new Vector2d(direction.toUnitVector().x() * start.x(), direction.toUnitVector().y() * start.y()))
//        }
//    }