package agh.ics.oop;

import java.util.HashSet;
import java.util.Set;

public class MapElementsSet extends HashSet<Object>{
    private static final String EMPTY_CELL = " ";

//    Set<Object> set = new HashSet<Object>();

    public Object getInstanceOfClass(Class<?> cls){
        for(Object obj : this){
            if(obj.getClass() == cls){
                return obj;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        Animal animal = (Animal) getInstanceOfClass(Animal.class);
        if(animal != null)
        {
            return animal.toString();
        }
        Grass grass = (Grass) getInstanceOfClass(Grass.class);
        if(grass != null)
        {
            return grass.toString();
        }
        return EMPTY_CELL;
    }
}
