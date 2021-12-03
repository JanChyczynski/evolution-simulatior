package agh.ics.oop;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d start, Vector2d end, IMapElement movedElement);
}
