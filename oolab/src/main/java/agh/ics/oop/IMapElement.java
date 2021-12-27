package agh.ics.oop;

public interface IMapElement {
    Vector2d getPosition();

    int displayPriority();

    boolean isTraversable();

    boolean isEdible();

    boolean isHungry();

    void setEnergy(int energy);

    int getEnergy();

    boolean isHorny();

    String representationImagePath();
}
