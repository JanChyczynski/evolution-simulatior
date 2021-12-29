package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public class Animal implements IMapElement, IPositionChangePublisher, ITrackable {
    private MapDirection orientation;
    private Vector2d position;
    private final IWorldMap map;
    private Genome genome;
    private int energy;
    private int birthDay;
    private OptionalInt deathDay;
    private final List<Animal> children;
    public boolean visited;
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
        orientation = MapDirection.fromInt(genome.getRandomGene());
        deathDay = OptionalInt.empty();
        children = new ArrayList<>();
        visited = false;

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
        Animal child = new Animal(map, getPosition(),
                getEnergy()/BIRTH_ENERGY_RATIO + lover.getEnergy()/BIRTH_ENERGY_RATIO, newGenome);
        addChild(child);
        lover.addChild(child);
        return child;
    }

    public void addChild(Animal child){
        synchronized (children)
        {
            children.add(child);
        }
    }

    public int getChildrenNumber() {
        return children.size();
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public OptionalInt getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(int deathDay) {
        setDeathDay(OptionalInt.of(deathDay));
    }
    public void setDeathDay(OptionalInt deathDay) {
        this.deathDay = deathDay;
    }

    public Genome getGenome() {
        return genome;
    }


    @Override
    public int getNewChildrenNumber(int startDay) {
        synchronized (children)
        {
            return ((int) children.stream().filter(a -> a.getBirthDay() >= startDay).count());
        }
    }

    @Override
    public int getNewAncestorsNumber(int startDay) {
        return getNewAncestorsSet(startDay).size();
    }

    public HashSet<Animal> getNewAncestorsSet(int startDay){
        synchronized (children){
            HashSet<Animal> set = (HashSet<Animal>) children.stream().filter(a -> a.getBirthDay() >= startDay).collect(Collectors.toSet());
            for (Animal child : children) {
                if (child.getBirthDay() >= startDay) {
                    HashSet<Animal> childAncestors = child.getNewAncestorsSet(startDay);
                    if(set.size() >= childAncestors.size()) //merging smaller set into bigger for better complexity
                    {
                        set.addAll(childAncestors);
                    }
                    else
                    {
                        childAncestors.addAll(set);
                        set = childAncestors;
                    }
                }
            }
           return set;
        }
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
