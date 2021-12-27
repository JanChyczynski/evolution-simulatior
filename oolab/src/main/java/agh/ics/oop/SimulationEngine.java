package agh.ics.oop;

import javafx.collections.transformation.SortedList;

import java.util.*;

import static java.util.Objects.isNull;

public class SimulationEngine implements IEngine, IPositionChangeObserver, Runnable {
    private final SteppeJungleMap worldMap;
    private final Set<Animal> animals;
    private final Set<IPositionChangeObserver> observers;
    public final int moveDelay;
    private final int startEnergy, moveEnergy, loveMinEnergy;

    public SimulationEngine(SteppeJungleMap worldMap, int initialPopulation,
                            int startEnergy, int moveEnergy) {
        observers = new HashSet<>();
        this.worldMap = worldMap;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        loveMinEnergy = startEnergy/2;
        moveDelay = 300;

        animals = new HashSet<>();
        createAnimals(initialPopulation);
    }

    private void createAnimals(int initialPopulation) {
        Vector2d position = new Vector2d(0,0);
        while(position != null && initialPopulation-- > 0)
        {
            position = worldMap.getRandomPositionSatisfying((p) ->!worldMap.isOccupied(p));
            if(!isNull(position)){
                animals.add(new Animal(this.worldMap, position, startEnergy));
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
            for (Iterator<Animal> iterator = animals.iterator(); iterator.hasNext(); ) {
                Animal animal = iterator.next();
                if (animal.getEnergy() <= 0) {
                    worldMap.remove(animal);
                    iterator.remove();
                }
                animal.move();
                animal.setEnergy(animal.getEnergy() - moveEnergy);
            }
            handleEating();
            handleReproduction();
            worldMap.growGrass();
            try{
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleEating() {
        for(MapElementsSet elements : worldMap.map.values()){
            Set<IMapElement> edibles = elements.getEdible();
            int energySum = edibles.stream().mapToInt(IMapElement::getEnergy).sum();
            Set<IMapElement> eaters = elements.getHungryWithHighestEnergy();
            for (IMapElement eater : eaters){
                eater.setEnergy(eater.getEnergy()+energySum/eaters.size());
            }
            if(!eaters.isEmpty()) {
                for(IMapElement edible : edibles){
                    worldMap.remove(edible);
                }
            }
        }
    }

    private void handleReproduction(){
        for(MapElementsSet elements : worldMap.map.values()){
            List<IMapElement> lovers = elements.getCoupleWithHighestEnergy();
            if(!isNull(lovers)){
                if(lovers.size() != 2){
                    throw new IllegalStateException("MapElementSet returned couple which size is not 2");
                }
                if(lovers.get(0).getEnergy() >= loveMinEnergy &&
                   lovers.get(0).getEnergy() >= loveMinEnergy ) {
                    Animal child = ((Animal) lovers.get(0)).makeLove((Animal) lovers.get(1));
                    animals.add(child);
                }

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
