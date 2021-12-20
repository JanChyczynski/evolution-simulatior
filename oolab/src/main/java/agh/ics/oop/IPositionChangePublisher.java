package agh.ics.oop;

public interface IPositionChangePublisher {
    void addObserver(IPositionChangeObserver observer);

    void removeObserver(IPositionChangeObserver observer);
}
