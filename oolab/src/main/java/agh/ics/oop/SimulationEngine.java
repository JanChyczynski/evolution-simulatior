package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {
    private final MoveDirection[] moveDirections;
    private final IWorldMap worldMap;
    private final List<Animal> animals;

    public SimulationEngine(MoveDirection[] moveDirections, IWorldMap worldMap, Vector2d[] initialPositions) {
        this.moveDirections = moveDirections;
        this.worldMap = worldMap;
        animals = new ArrayList<Animal>();
        for(Vector2d position : initialPositions){
            animals.add(new Animal(this.worldMap, position));
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < moveDirections.length; i++) {
            animals.get(i % animals.size()).move(moveDirections[i]);
        }
    }
}
