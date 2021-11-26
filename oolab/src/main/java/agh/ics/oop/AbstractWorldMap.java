package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

abstract public class AbstractWorldMap implements IWorldMap{
    protected final Map<Vector2d, MapElementsSet> map = new HashMap<>();
    protected final MapVisualiser mapVisualiser;

    public AbstractWorldMap() {
        mapVisualiser = new MapVisualiser(this);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !map.containsKey(position)
                || isNull(map.get(position).getInstanceOfClass(Animal.class));
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            if(!map.containsKey(animal.getPosition())){
                map.put(animal.getPosition(), new MapElementsSet());
            }
            map.get(animal.getPosition()).add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return map.containsKey(position) && !map.get(position).isEmpty();
    }

    @Override
    public Object objectAt(Vector2d position) {
        return map.get(position);
    }

    @Override
    public boolean move(Vector2d start, Vector2d end) {
        if (map.containsKey(start) && !isNull(map.get(start).getInstanceOfClass(Animal.class))
                && canMoveTo(end)) {
            Animal movedAnimal = (Animal) map.get(start).getInstanceOfClass(Animal.class);
            map.get(start).remove(movedAnimal);
            if(!map.containsKey(end)){
                map.put(end, new MapElementsSet());
            }
            map.get(end).add(movedAnimal);
            return true;
        }
        return false;
    }

    abstract public Vector2d bottomLeftCorner();

    abstract public Vector2d upperRightCorner();

    @Override
    public String toString() {
        return mapVisualiser.draw(bottomLeftCorner(), upperRightCorner());
    }
}
