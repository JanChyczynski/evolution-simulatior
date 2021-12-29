package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class SimulationEngine implements IEngine, IPositionChangeObserver, Runnable, IPausable {
    private final SteppeJungleMap worldMap;
    private final Set<Animal> animals;
    private final Set<Animal> deadAnimals;
    private final Set<IPositionChangeObserver> positionObservers;
    private final Set<IStatisticsObserver> statisticsObservers;
    public final int dayDelay;
    private final int startEnergy, moveEnergy, loveMinEnergy;
    private int day;
    private boolean paused;

    public SimulationEngine(SteppeJungleMap worldMap, SimulationParameters params){
        this(worldMap, params.initialPopulation(), params.startEnergy(), params.moveEnergy(), params.delay());
    }

    public SimulationEngine(SteppeJungleMap worldMap, int initialPopulation,
                            int startEnergy, int moveEnergy, int dayDelay) {
        positionObservers = new HashSet<>();
        statisticsObservers = new HashSet<>();
        this.worldMap = worldMap;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        loveMinEnergy = startEnergy/2;
        this.dayDelay = dayDelay;
        day = 0;
        paused = false;

        animals = new HashSet<>();
        deadAnimals = new HashSet<>();
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
            animal.addPositionObserver(this);
            animal.setBirthDay(day);
        }
    }

    @Override
    public void run() {
        for(day = 0;true; day++) {
            for (Iterator<Animal> iterator = animals.iterator(); iterator.hasNext(); ) {
                Animal animal = iterator.next();
                if (animal.getEnergy() <= 0) {
                    worldMap.remove(animal);
                    deadAnimals.add(animal);
                    animal.setDeathDay(day);
                    iterator.remove();
                    continue;
                }
                animal.move();
                animal.setEnergy(animal.getEnergy() - moveEnergy);
            }
            handleEating();
            handleReproduction();
            worldMap.growGrass();
            updateStatistics();
            notifyAllPositionObservers(new Vector2d(0,0), new Vector2d(0,0), null);
            try{
                Thread.sleep(dayDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            while(isPaused()){
                synchronized (this){

                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
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
                   lovers.get(1).getEnergy() >= loveMinEnergy ) {
                    Animal child = ((Animal) lovers.get(0)).makeLove((Animal) lovers.get(1));
                    animals.add(child);
                    child.setBirthDay(day);
                    child.addPositionObserver(this);
                }

            }
        }
    }

    private void updateStatistics() {
        statisticsObservers.forEach(observer -> observer.statisticsChanged(new StatisticsEntry(day, animals.size(),
                worldMap.getPlantsNumber(), getAverageEnergy(), getAverageLifespan(), getAverageChildrenNumber(), getTopGenome())));
    }

    private double getAverageEnergy() {
        return animals.stream().mapToInt(Animal::getEnergy).average().orElse(0);
    }

    private double getAverageChildrenNumber() {
        return animals.stream().mapToInt(Animal::getChildrenNumber).average().orElse(0);
    }

    private double getAverageLifespan() {
        return deadAnimals.stream().mapToInt(a -> a.getDeathDay().getAsInt() - a.getBirthDay()).average().orElse(0);
    }

    public synchronized Genome getTopGenome() {
        if(animals.isEmpty())
        {
            return new Genome();
        }
        return animals.stream()
                .collect(Collectors.groupingBy(Animal::getGenome, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
    }

    public void addStatisticsObserver(IStatisticsObserver observer){
        statisticsObservers.add(observer);
    }

    public void removeStatisticsObserver(IStatisticsObserver observer){
        statisticsObservers.remove(observer);
    }

    @Override
    public void addPositionObserver(IPositionChangeObserver observer){
        positionObservers.add(observer);
    }

    @Override
    public void removePositionObserver(IPositionChangeObserver observer){
        positionObservers.remove(observer);
    }

    private void notifyAllPositionObservers(Vector2d newPosition, Vector2d oldPosition, IMapElement movedElement) {
        for(IPositionChangeObserver observer : positionObservers){
            observer.positionChanged(oldPosition, newPosition, movedElement);
        }
    }

    @Override
    public void positionChanged(Vector2d start, Vector2d end, IMapElement movedElement) {

    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void pause() {
        this.paused = true;
    }

    @Override
    public void resume(){
        paused = false;
        synchronized (this){
            this.notify();
        }
    }

    public int getDay() {
        return day;
    }

}
