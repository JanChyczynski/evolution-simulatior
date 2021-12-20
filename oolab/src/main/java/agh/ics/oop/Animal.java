package agh.ics.oop;

import agh.ics.oop.gui.Genome;

import java.util.HashSet;
import java.util.Set;

public class Animal implements IMapElement, IPositionChangePublisher {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private final IWorldMap map;
    private final Genome genom;

    private final Set<IPositionChangeObserver> observers;

    public static final Vector2d MAP_LOWER_LEFT = new Vector2d(0,0);
    public static final Vector2d MAP_UPPER_RIGHT = new Vector2d(4,4);

    public Animal(IWorldMap map){
        this(map, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        position = initialPosition;
        genom = new Genome();

        observers = new HashSet<>();
        this.map.place(this);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int displayPriority() {
        return 1;
    }

    @Override
    public boolean isTraversable() {
        return false;
    }

    @Override
    public String representationImagePath() {
        return "src/main/resources/up.png";
    }

    public void move(){
        move(MoveDirection.fromInt(genom.getRandomGene()));
    }

    public void move(MoveDirection direction){
        orientation = orientation.rotate(direction);
        switch (direction) {
            case FORWARD, BACKWARD -> moveForward();
        }
    }

    private void moveForward() {
        Vector2d newPosition = new Vector2d(position);
        newPosition = position.add(orientation.toUnitVector());
        if(map.canMoveTo(newPosition)){
            Vector2d oldPosition = position;
            position = newPosition;
            if(!oldPosition.equals(newPosition)){
                notifyAllObservers(newPosition, oldPosition);
            }
        }
    }

    @Override
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void notifyAllObservers(Vector2d newPosition, Vector2d oldPosition) {
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }

    @Override
    public String toString() {
        return switch (orientation){
            case NORTH -> "^";
            case NORTH_EAST -> "/";
            case EAST ->  ">";
            case SOUTH_EAST -> "\\";
            case SOUTH -> "v";
            case SOUTH_WEST -> "/";
            case WEST -> "<";
            case NORTH_WEST -> "\\";
        };
    }
}
