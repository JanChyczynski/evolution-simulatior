package agh.ics.oop;

public interface IMapElement {
    Vector2d getPosition();

    int displayPriority();

    boolean isTraversable();

    String representationImagePath();
}
