package agh.ics.oop;

public enum MoveDirection {
    FORWARD,
    FORWARD_RIGHT,
    RIGHT,
    BACKWARD_RIGHT,
    BACKWARD,
    BACKWARD_LEFT,
    LEFT,
    FORWARD_LEFT;

    public MapDirection toMapDirection(){
        return switch (this){
            case FORWARD -> MapDirection.NORTH;
            case FORWARD_RIGHT -> MapDirection.NORTH_EAST;
            case RIGHT -> MapDirection.EAST;
            case BACKWARD_RIGHT -> MapDirection.SOUTH_EAST;
            case BACKWARD -> MapDirection.SOUTH;
            case BACKWARD_LEFT -> MapDirection.SOUTH_WEST;
            case LEFT -> MapDirection.WEST;
            case FORWARD_LEFT -> MapDirection.NORTH_WEST;
        };
    }
}
