package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void canMoveTo() {
        GrassField field = new GrassField(1);

        assert (field.canMoveTo(new Vector2d(0,0)));

        Animal animal = new Animal(field, new Vector2d(0,0));

        assertFalse (field.canMoveTo(new Vector2d(0,0)));
        assert (field.canMoveTo(new Vector2d(1,0)));
    }

    @Test
    void place() {
        GrassField field1 = new GrassField(1);
        GrassField field2 = new GrassField(1);
        Animal animal = new Animal(field1, new Vector2d(0,0));

        field2.place(animal);

        assert (field1.isOccupied(new Vector2d(0,0)));
        assert (field2.isOccupied(new Vector2d(0,0)));

        assertThrows(IllegalArgumentException.class, ()->field1.place(animal));
        assertThrows(IllegalArgumentException.class, ()->field2.place(animal));
    }

    @Test
    void isOccupied() {
        GrassField field = new GrassField(1);

        assertFalse(field.isOccupied(new Vector2d(0,0)));

        Animal animal = new Animal(field, new Vector2d(0,0));

        assert (field.isOccupied(new Vector2d(0,0)));
    }

    @Test
    void objectAt() {
        GrassField field = new GrassField(0);

        assert(isNull(field.objectAt(new Vector2d(0,0))));

        Animal animal = new Animal(field, new Vector2d(0,0));

        assertFalse (isNull(field.objectAt(new Vector2d(0,0))));
        assert (((MapElementsSet)field.objectAt(new Vector2d(0,0))).contains(animal));
    }
}