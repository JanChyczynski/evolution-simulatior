package agh.ics.oop;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public MapDirection next(){
        return switch (this) {
            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;
            case WEST -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
        };
    }
    public MapDirection previous(){
        return switch (this) {
            case NORTH -> NORTH_WEST;
            case NORTH_EAST -> NORTH;
            case EAST -> NORTH_EAST;
            case SOUTH_EAST -> EAST;
            case SOUTH -> SOUTH_EAST;
            case SOUTH_WEST -> SOUTH;
            case WEST -> SOUTH_WEST;
            case NORTH_WEST -> WEST;
        };
    }

    public int toInt(){
        return switch (this){
            case NORTH -> 0;
            case NORTH_EAST -> 1;
            case EAST -> 2;
            case SOUTH_EAST -> 3;
            case SOUTH -> 4;
            case SOUTH_WEST -> 5;
            case WEST -> 6;
            case NORTH_WEST -> 7;
        };
    }

    static public MapDirection fromInt(int number){
        return switch (number){
            case 0 -> NORTH;
            case 1 -> NORTH_EAST;
            case 2 -> EAST;
            case 3 -> SOUTH_EAST;
            case 4 -> SOUTH;
            case 5 -> SOUTH_WEST;
            case 6 -> WEST;
            case 7 -> NORTH_WEST;
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };
    }

    public MapDirection rotate (MapDirection delta){
        return fromInt((this.toInt()+delta.toInt())%MapDirection.values().length);
    }

    public MapDirection rotate (MoveDirection delta){
        return this.rotate(delta.toMapDirection());
    }
    public Vector2d toUnitVector(){
        return switch (this) {
            case NORTH -> new Vector2d(0,1);
            case NORTH_EAST -> new Vector2d(1,1);
            case EAST  -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0,-1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
            case WEST  -> new Vector2d(-1,0);
            case NORTH_WEST  -> new Vector2d(-1,1);
        };
    }

}
