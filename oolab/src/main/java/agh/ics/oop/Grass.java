package agh.ics.oop;

public class Grass implements IMapElement {
    private final Vector2d position;
    private int energy;

    public Grass(Vector2d position, int energy) {
        this.position = position;
        this.energy = energy;
    }

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

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public boolean isHungry() {
        return false;
    }

    @Override
    public void setEnergy(int energy){
        throw new UnsupportedOperationException();
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "*";
    }
}
