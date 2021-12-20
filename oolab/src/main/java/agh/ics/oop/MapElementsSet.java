package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

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

    @Override
    public String toString(){
        if(!this.isEmpty()){
            return this.last().toString();
        }
        return EMPTY_CELL;
    }
}
