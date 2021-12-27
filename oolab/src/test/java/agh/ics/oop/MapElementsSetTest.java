package agh.ics.oop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MapElementsSetTest {

    @Test
    void getInstanceOfClass() {
        MapElementsSet set = new MapElementsSet();
        Animal animal = new Animal(new RectangularMap(5, 5), new Vector2d(2, 2));
        Grass grass = new Grass(new Vector2d(4,2), 0);

        assertNull(set.getInstanceOfClass(Animal.class));
        assertNull(set.getInstanceOfClass(Grass.class));

        set.add(animal);
        set.add(grass);

        assert(set.getInstanceOfClass(Animal.class) == animal);
        assert(set.getInstanceOfClass(Grass.class) == grass);

    }

    @Test
    void getCoupleWithHighestEnergy(){
        MapElementsSet set = new MapElementsSet();
        IWorldMap map = new RectangularMap(10, 10);
        Animal animal1 = new Animal(map, new Vector2d(2, 2), 1);
        Animal animal2 = new Animal(map, new Vector2d(2, 2), 2);
        Animal animal3 = new Animal(map, new Vector2d(2, 2), 3);
        Grass grass = new Grass(new Vector2d(2, 2), 10);

        set.add(grass);
        set.add(animal1);
        set.add(animal2);
        set.add(animal3);

        List<IMapElement> couple = set.getCoupleWithHighestEnergy();
        assert (couple.contains(animal2));
        assert (couple.contains(animal3));
        assertEquals (2, couple.size());
    }

    @Test
    void testToString() {
        MapElementsSet set = new MapElementsSet();
        Animal animal = new Animal(new RectangularMap(5, 5), new Vector2d(2, 2));
        Grass grass = new Grass(new Vector2d(4,2), 10);

        set.add(grass);
        assertEquals(set.toString(), grass.toString());
        set.add(animal);
        assertEquals(set.toString(), animal.toString());
    }
}