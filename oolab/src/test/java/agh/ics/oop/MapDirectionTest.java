package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    @Test
    void testNextReturnsClockwiseSuccessor(){
        assertEquals(MapDirection.NORTH.next(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.next(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.next(), MapDirection.WEST);
        assertEquals(MapDirection.WEST.next(), MapDirection.NORTH);
    }
    @Test
    void tesPreviousReturnsClockwisePredecessor(){
        assertEquals(MapDirection.NORTH.previous(), MapDirection.WEST);
        assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.previous(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.previous(), MapDirection.NORTH);
    }
}