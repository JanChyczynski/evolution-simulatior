package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private IWorldMap map;

    public static final Vector2d MAP_LOWER_LEFT = new Vector2d(0,0);
    public static final Vector2d MAP_UPPER_RIGHT = new Vector2d(4,4);

    public Animal(IWorldMap map){
        this(map, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        position = initialPosition;
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
        switch (direction){
            case FORWARD:
                newPosition = position.add(orientation.toUnitVector());
                break;
            case BACKWARD:
                newPosition = position.add(orientation.toUnitVector().opposite());
                break;
            case RIGHT:
                orientation = orientation.next();
                break;
            case LEFT:
                orientation = orientation.previous();
                break;
        }
        if(map.move(position, newPosition)){
            position = new Vector2d(newPosition);
        }
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
