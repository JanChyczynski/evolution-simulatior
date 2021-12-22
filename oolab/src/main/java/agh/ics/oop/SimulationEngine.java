package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimulationEngine implements IEngine, IPositionChangeObserver, Runnable {
    private final SteppeJungleMap worldMap;
    private final List<Animal> animals;
    private final Set<IPositionChangeObserver> observers;
    public final int moveDelay;

    public SimulationEngine(SteppeJungleMap worldMap, Vector2d[] initialPositions) {
        observers = new HashSet<>();
        this.worldMap = worldMap;
        animals = new ArrayList<>();
        for(Vector2d position : initialPositions){
            animals.add(new Animal(this.worldMap, position));
        }
        for(Animal animal : animals)
        {
            animal.addObserver(this);
        }
        moveDelay = 300;
    }

    @Override
    public void run() {
        while(true) {
            worldMap.growGrass();
            for(Animal animal : animals){
                animal.move();
            }
            try{
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void notifyAllObservers(Vector2d newPosition, Vector2d oldPosition, IMapElement movedElement) {
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition, movedElement);
        }
    }

    @Override
    public void positionChanged(Vector2d start, Vector2d end, IMapElement movedElement) {
        notifyAllObservers(start, end, movedElement);
    }
}
