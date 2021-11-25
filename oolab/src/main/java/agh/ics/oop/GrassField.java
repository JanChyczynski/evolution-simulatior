package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.sqrt;
import static java.util.Objects.isNull;

public class GrassField implements IWorldMap {
    private final Map<Vector2d, MapElementsSet> map = new HashMap<>();
    private final MapVisualiser mapVisualiser;

    public GrassField(int grassAmount) {
        int maxCoordinate = (int) sqrt(grassAmount * 10);
        for (int i = 0; i < grassAmount; i++) {
            Random rand = new Random();
            Vector2d position;
            do {
                position = new Vector2d(rand.nextInt(maxCoordinate + 1),
                                        rand.nextInt(maxCoordinate + 1));
            }
            while (isOccupied(position));
            map.put(position, new MapElementsSet());
            map.get(position).add(new Grass(position));
        }

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

    public Vector2d bottomLeftCorner(){
        Vector2d corner = null;
        for(MapElementsSet elementsSet : map.values()){
            Animal animal = (Animal) elementsSet.getInstanceOfClass(Animal.class);
            if(!isNull(animal)) {
                if (isNull(corner)) {
                    corner = animal.getPosition();
                } else {
                    corner = corner.lowerLeft(animal.getPosition());
                }
            }
        }
        return corner;
    }

    public Vector2d upperRightCorner(){
        Vector2d corner = null;
        for(MapElementsSet elementsSet : map.values()){
            Animal animal = (Animal) elementsSet.getInstanceOfClass(Animal.class);
            if(!isNull(animal)) {
                if (isNull(corner)) {
                    corner = animal.getPosition();
                } else {
                    corner = corner.upperRight(animal.getPosition());
                }
            }
        }
        return corner;
    }

    @Override
    public String toString() {
        return mapVisualiser.draw(bottomLeftCorner(), upperRightCorner());
    }

}
