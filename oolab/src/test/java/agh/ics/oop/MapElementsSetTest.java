package agh.ics.oop;

import org.junit.jupiter.api.Test;

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