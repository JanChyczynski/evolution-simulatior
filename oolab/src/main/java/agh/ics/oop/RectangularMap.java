package agh.ics.oop;

public class RectangularMap extends AbstractWorldMap{
    private final int height, width;

    public RectangularMap(int height, int width) {
        this.height = height;
        this.width = width;

    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return inBounds(position) &&
                super.canMoveTo(position);
    }

    private boolean inBounds(Vector2d position){
        return (position.follows(new Vector2d(0,0)) &&
                position.precedes(new Vector2d(width, height)));
    }

    @Override
    public Vector2d bottomLeftCorner(){
        return new Vector2d(0,0);
    }

    @Override
    public Vector2d upperRightCorner(){
        return new Vector2d(width, height);
    }
}
