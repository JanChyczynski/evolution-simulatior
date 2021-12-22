package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

public class SimulationEngine implements IEngine, IPositionChangeObserver, Runnable {
    private final SteppeJungleMap worldMap;
    private final List<Animal> animals;
    private final Set<IPositionChangeObserver> observers;
    public final int moveDelay;

    public SimulationEngine(SteppeJungleMap worldMap, int initialPopulation) {
        observers = new HashSet<>();
        this.worldMap = worldMap;
        animals = new ArrayList<>();
        createAnimals(initialPopulation);

        moveDelay = 300;
    }

    private void createAnimals(int initialPopulation) {
        Vector2d position = new Vector2d(0,0);
        while(position != null && initialPopulation-- > 0)
        {
            position = worldMap.getRandomPositionSatisfying((p) ->!worldMap.isOccupied(p));
            if(!isNull(position)){
                animals.add(new Animal(this.worldMap, position));
            }
        }
        for(Animal animal : animals)
        {
            animal.addObserver(this);
        }
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
