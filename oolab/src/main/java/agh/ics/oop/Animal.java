package agh.ics.oop;

import java.util.HashSet;
import java.util.Set;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    private final Set<IPositionChangeObserver> observers;

    public static final Vector2d MAP_LOWER_LEFT = new Vector2d(0,0);
    public static final Vector2d MAP_UPPER_RIGHT = new Vector2d(4,4);

    public Animal(IWorldMap map){
        this(map, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        position = initialPosition;
        observers = new HashSet<>();
        this.map.place(this);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void move(MoveDirection direction){

        Vector2d newPosition = new Vector2d(position);
        switch (direction) {
            case FORWARD -> newPosition = position.add(orientation.toUnitVector());
            case BACKWARD -> newPosition = position.add(orientation.toUnitVector().opposite());
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
        }
        if(map.canMoveTo(newPosition)){
            for(IPositionChangeObserver observer : observers){
                observer.positionChanged(position, newPosition);
            }
            position = new Vector2d(newPosition);
        }
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    @Override
    public String toString() {
        return switch (orientation){
            case NORTH -> "^";
            case EAST ->  ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }
}
