package agh.ics.oop;

public interface IPositionChangePublisher {
    void addPositionObserver(IPositionChangeObserver observer);

    void removePositionObserver(IPositionChangeObserver observer);
}
