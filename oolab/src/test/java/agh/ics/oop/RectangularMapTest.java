package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    @Test
    void canMoveTo() {
        RectangularMap field = new RectangularMap(10, 10);

        assert (field.canMoveTo(new Vector2d(0,0)));

        Animal animal = new Animal(field, new Vector2d(0,0));

        assertFalse (field.canMoveTo(new Vector2d(0,0)));
        assertFalse (field.canMoveTo(new Vector2d(-1,0)));
        assertFalse (field.canMoveTo(new Vector2d(0,11)));
        assert (field.canMoveTo(new Vector2d(1,0)));
        assert (field.canMoveTo(new Vector2d(10,10)));
    }

    @Test
    void place() {
        RectangularMap field1 = new RectangularMap(10, 10);
        RectangularMap field2 = new RectangularMap(10, 10);
        Animal animal = new Animal(field1, new Vector2d(0,0));

        field2.place(animal);

        assert (field1.isOccupied(new Vector2d(0,0)));
        assert (field2.isOccupied(new Vector2d(0,0)));
    }

    @Test
    void isOccupied() {
        RectangularMap field = new RectangularMap(10, 10);

        assertFalse(field.isOccupied(new Vector2d(0,0)));

        Animal animal = new Animal(field, new Vector2d(0,0));

        assert (field.isOccupied(new Vector2d(0,0)));
        assertFalse (field.isOccupied(new Vector2d(-1,0)));
        assertFalse (field.isOccupied(new Vector2d(0,11)));
    }

    @Test
    void objectAt() {
        RectangularMap field = new RectangularMap(10, 10);

        assert(isNull(field.objectAt(new Vector2d(0,0))));

        Animal animal = new Animal(field, new Vector2d(0,0));

        assert(isNull(field.objectAt(new Vector2d(-1,0))));
        assert(isNull(field.objectAt(new Vector2d(0,11))));
        assertFalse (isNull(field.objectAt(new Vector2d(0,0))));
        assert (((MapElementsSet)field.objectAt(new Vector2d(0,0))).contains(animal));
    }

}