package agh.ics.oop;

import java.util.HashSet;
import java.util.Set;

public class Animal implements IMapElement, IPositionChangePublisher {
    private MapDirection orientation;
    private Vector2d position;
    private final IWorldMap map;
    private Genome genome;
    private int energy;
    private int childrenNumber;
    private int birthDay;
    private int deathDay;

    static final int BIRTH_ENERGY_RATIO = 4;

    private final Set<IPositionChangeObserver> observers;

    public Animal(IWorldMap map, Vector2d initialPosition, int energy, Genome genome) {
        this(map, initialPosition, energy);
        this.genome = genome;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int energy) {
        this(map, initialPosition);
        this.energy = energy;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        position = initialPosition;
        genome = new Genome();
        childrenNumber = 0;
        orientation = MapDirection.fromInt(genome.getRandomGene());

        observers = new HashSet<>();
        this.map.place(this);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public int displayPriority() {
        return 1;
    }

    @Override
    public boolean isTraversable() {
        return true;
    }

    @Override
    public boolean isEdible() {
        return false;
    }

    @Override
    public boolean isHungry() {
        return true;
    }

    @Override
    public String representationImagePath() {
        return "src/main/resources/elon.png";
    }

    public void move(){
        move(MoveDirection.fromInt(genome.getRandomGene()));
    }

    public void move(MoveDirection direction){
        orientation = orientation.rotate(direction);
        switch (direction) {
            case FORWARD, BACKWARD -> moveForward();
        }
    }

    private void moveForward() {
        Vector2d newPosition = new Vector2d(position);
        newPosition = position.add(orientation.toUnitVector());
        if(map.canMoveTo(newPosition)){
            Vector2d oldPosition = position;
            position = newPosition;
            if(!oldPosition.equals(newPosition)){
                notifyAllObservers(newPosition, oldPosition);
            }
        }
    }

    @Override
    public void addPositionObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    @Override
    public void removePositionObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    private void notifyAllObservers(Vector2d newPosition, Vector2d oldPosition) {
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public int getEnergy() {
        return energy;
    }

    @Override
    public boolean isHorny() { return true; }

    public Animal makeLove(Animal lover) {
        Genome newGenome = genome.combined(lover.genome, Genome.SIZE*lover.getEnergy()/getEnergy());
        setEnergy(getEnergy() - getEnergy()/BIRTH_ENERGY_RATIO);
        lover.setEnergy(lover.getEnergy() - lover.getEnergy()/lover.BIRTH_ENERGY_RATIO);
        childrenNumber++;
        lover.setChildrenNumber(lover.getChildrenNumber()+1);
        return new Animal(map, getPosition(), getEnergy()/BIRTH_ENERGY_RATIO + lover.getEnergy()/BIRTH_ENERGY_RATIO, newGenome);
    }


    public int getChildrenNumber() {
        return childrenNumber;
    }

    private void setChildrenNumber(int i) {
        childrenNumber = i;
    }


    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }

    public Genome getGenome() {
        return genome;
    }

    @Override
    public String toString() {
        return switch (orientation){
            case NORTH -> "^";
            case NORTH_EAST -> "/";
            case EAST ->  ">";
            case SOUTH_EAST -> "\\";
            case SOUTH -> "v";
            case SOUTH_WEST -> "/";
            case WEST -> "<";
            case NORTH_WEST -> "\\";
        };
    }
}
