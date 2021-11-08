package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void move() {
        Animal animal = new Animal();
        assertEquals(animal.getPosition(), new Vector2d(2,2));
        assertEquals(animal.getOrientation(), MapDirection.NORTH);

        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(2,3));
        assertEquals(animal.getOrientation(), MapDirection.NORTH);

        animal.move(MoveDirection.BACKWARD);
        assertEquals(animal.getPosition(), new Vector2d(2,2));
        assertEquals(animal.getOrientation(), MapDirection.NORTH);

        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getPosition(), new Vector2d(2,2));
        assertEquals(animal.getOrientation(), MapDirection.EAST);

        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(3,2));
        assertEquals(animal.getOrientation(), MapDirection.EAST);

        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getPosition(), new Vector2d(3,2));
        assertEquals(animal.getOrientation(), MapDirection.NORTH);

        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getOrientation(), MapDirection.WEST);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getOrientation(), MapDirection.SOUTH);
    }

    @Test
    void shouldNotLeaveMap(){
        Vector2d mapLowerLeft = new Vector2d(0,0);
        Vector2d mapUpperRight = new Vector2d(4,4);

        Animal animal = new Animal();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                animal.move(MoveDirection.FORWARD);
            }
            assertTrue(animal.getPosition().follows(mapLowerLeft)
                    && animal.getPosition().precedes(mapUpperRight));
            animal.move(MoveDirection.LEFT);
        }
    }
}