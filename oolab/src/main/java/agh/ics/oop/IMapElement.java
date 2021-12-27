package agh.ics.oop;

public interface IMapElement {
    Vector2d getPosition();

    default MapDirection getOrientation(){
        return MapDirection.NORTH;
    }

    int displayPriority();

    boolean isTraversable();

    boolean isEdible();

    boolean isHungry();

    void setEnergy(int energy);

    int getEnergy();

    boolean isHorny();

    String representationImagePath();
}
