package agh.ics.oop;

import java.util.Random;

import static java.lang.Math.sqrt;
import static java.util.Objects.isNull;

public class GrassField extends AbstractWorldMap {
    MapBoundary boundary;

    public GrassField(int grassAmount) {
        boundary = new MapBoundary();

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

    }

    @Override
    public boolean place(Animal animal) {
        boundary.add(animal.getPosition());
        animal.addObserver(boundary);
        return super.place(animal);
    }

    @Override
    public Vector2d bottomLeftCorner(){
        return boundary.bottomLeftCorner();
    }

    @Override
    public Vector2d upperRightCorner(){
        return boundary.upperRightCorner();
    }
}


//    @Override
//    public Vector2d bottomLeftCorner(){
//        Vector2d corner = null;
//        for(MapElementsSet elementsSet : map.values()){
//            Animal animal = (Animal) elementsSet.getInstanceOfClass(Animal.class);
//            if(!isNull(animal)) {
//                if (isNull(corner)) {
//                    corner = animal.getPosition();
//                } else {
//                    corner = corner.lowerLeft(animal.getPosition());
//                }
//            }
//        }
//        return corner;
//    }
//
//    @Override
//    public Vector2d upperRightCorner(){
//        Vector2d corner = null;
//        for(MapElementsSet elementsSet : map.values()){
//            Animal animal = (Animal) elementsSet.getInstanceOfClass(Animal.class);
//            if(!isNull(animal)) {
//                if (isNull(corner)) {
//                    corner = animal.getPosition();
//                } else {
//                    corner = corner.upperRight(animal.getPosition());
//                }
//            }
//        }
//        return corner;
//    }
