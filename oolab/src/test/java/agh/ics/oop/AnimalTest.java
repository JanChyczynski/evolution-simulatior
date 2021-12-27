package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void move() {
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        while (!animal.getOrientation().equals(MapDirection.NORTH))
        {
            animal.move(MoveDirection.FORWARD_RIGHT);
        }
        assertEquals(animal.getPosition(), new Vector2d(2,2));
        assertEquals(animal.getOrientation(), MapDirection.NORTH);

        animal.move(MoveDirection.FORWARD);
        assertEquals(animal.getPosition(), new Vector2d(2,3));
        assertEquals(animal.getOrientation(), MapDirection.NORTH);

        animal.move(MoveDirection.BACKWARD);
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.RIGHT);
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
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                animal.move(MoveDirection.FORWARD);
            }
            assertTrue(animal.getPosition().follows(map.bottomLeftCorner())
                    && animal.getPosition().precedes(map.upperRightCorner()));
            animal.move(MoveDirection.LEFT);
        }
    }
}