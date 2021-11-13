package agh.ics.oop;

public class RectangularMap implements IWorldMap{
    private int height, width;

    public RectangularMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }
}
