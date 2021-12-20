package agh.ics.oop;

public class Grass implements IMapElement {
    private final Vector2d position;

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int displayPriority() {
        return 0;
    }

    @Override
    public boolean isTraversable() {
        return true;
    }

    @Override
    public String representationImagePath() {
        return "src/main/resources/grass.png";
    }

    public Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
