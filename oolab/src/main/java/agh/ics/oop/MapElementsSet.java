package agh.ics.oop;

import java.util.Comparator;
import java.util.OptionalInt;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MapElementsSet extends TreeSet<IMapElement> {

    private static final String EMPTY_CELL = " ";

    public MapElementsSet() {
        super(Comparator.comparing(IMapElement::displayPriority)
                        .thenComparing(IMapElement::hashCode));

    }

    public IMapElement representingElement(){
        return this.last();
    }

    public IMapElement getInstanceOfClass(Class<?> cls){
        for(IMapElement obj : this){
            if(obj.getClass() == cls){
                return obj;
            }
        }
        return null;
    }

    public boolean isTraversable(){
        return this.stream().allMatch(IMapElement::isTraversable);
    }

    public Set<IMapElement> getEdible(){
        return this.stream().filter(IMapElement::isEdible).collect(Collectors.toSet());
    }

    public Set<IMapElement> getHungryWithHighestEnergy(){
        Set<IMapElement> hungryElements = this.stream().filter(IMapElement::isHungry).collect(Collectors.toSet());

        OptionalInt maxEnergy = hungryElements.stream().mapToInt(IMapElement::getEnergy).max();
        if(maxEnergy.isEmpty()) {
            return new TreeSet<>(Comparator.comparing(IMapElement::displayPriority)
                    .thenComparing(IMapElement::hashCode));
        }else{
            return hungryElements.stream().filter(p -> p.getEnergy() == maxEnergy.getAsInt()).collect(Collectors.toSet());
        }
    }

    @Override
    public String toString(){
        if(!this.isEmpty()){
            return this.last().toString();
        }
        return EMPTY_CELL;
    }
}
