package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected final Map<Vector2d, MapElementsSet> map = new HashMap<>();
    protected final MapVisualiser mapVisualiser;

    public AbstractWorldMap() {
        mapVisualiser = new MapVisualiser(this);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !map.containsKey(position)
                || map.get(position).isTraversable();
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            if(!map.containsKey(animal.getPosition())){
                map.put(animal.getPosition(), new MapElementsSet());
            }
            map.get(animal.getPosition()).add(animal);
            animal.addObserver(this);
            return true;
        }
        else {
            throw new IllegalArgumentException("Tried to place on position " + animal.getPosition().toString()
                                             + " which is blocked");
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return map.containsKey(position) && !map.get(position).isEmpty();
    }

    @Override
    public MapElementsSet objectAt(Vector2d position) {
        return map.get(position);
    }

    @Override
    public void positionChanged(Vector2d start, Vector2d end, IMapElement movedElement) {
        if (map.containsKey(start) && map.get(start).contains(movedElement)
                && canMoveTo(end)) {

            map.get(start).remove(movedElement);
            if(!map.containsKey(end)){
                map.put(end, new MapElementsSet());
            }
            map.get(end).add(movedElement);
//            return true;
        }
//        return false;
    }



    @Override
    public String toString() {
        return mapVisualiser.draw(bottomLeftCorner(), upperRightCorner());
    }
}
