package agh.ics.oop;

public class Animal {
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public void move(MoveDirection direction){
        Vector2d mapLowerLeft = new Vector2d(0,0);
        Vector2d mapUpperRight = new Vector2d(4,4);
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
        if(newPosition.follows(mapLowerLeft) && newPosition.precedes(mapUpperRight)){
            position = new Vector2d(newPosition);
        }
    }

    @Override
    public String toString() {
        return "Animal{" +
                "direction=" + orientation +
                ", position=" + position +
                '}';
    }
}
