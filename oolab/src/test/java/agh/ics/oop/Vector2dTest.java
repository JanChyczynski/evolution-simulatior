package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void equals() {
        //given:
        Vector2d a = new Vector2d(1, 1);
        Vector2d b = new Vector2d(1, 1);
        Vector2d c = new Vector2d(0, 1);

        //then:
        assertEquals(a, b);
        assertEquals(b, a);
        assertNotEquals(a, c);
        assertNotEquals(c, a);
    }

    @Test
    void precedes() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(1, 1);
        Vector2d c = new Vector2d(0, 4);

        //then:
        assertTrue(b.precedes(a));
        assertFalse(a.precedes(b));
        assertFalse(a.precedes(c));
        assertFalse(c.precedes(a));
    }

    @Test
    void follows() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(1, 1);
        Vector2d c = new Vector2d(0, 4);

        //then:
        assertTrue(a.follows(b));
        assertFalse(b.follows(a));
        assertFalse(a.follows(c));
        assertFalse(c.follows(a));
    }

    @Test
    void upperRight() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(1, 1);
        Vector2d c = new Vector2d(0, 4);

        //then:
        assertEquals(a.upperRight(b), a);
        assertEquals(b.upperRight(a), a);
        assertEquals(a.upperRight(c), new Vector2d(2, 4));
        assertEquals(c.upperRight(a), new Vector2d(2, 4));
        assertEquals(b.upperRight(c), new Vector2d(1, 4));
        assertEquals(c.upperRight(b), new Vector2d(1, 4));

    }

    @Test
    void lowerLeft() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(1, 1);
        Vector2d c = new Vector2d(0, 4);

        //then:
        assertEquals(a.lowerLeft(b), b);
        assertEquals(b.lowerLeft(a), b);
        assertEquals(a.lowerLeft(c), new Vector2d(0, 3));
        assertEquals(c.lowerLeft(a), new Vector2d(0, 3));
        assertEquals(b.lowerLeft(c), new Vector2d(0, 1));
        assertEquals(c.lowerLeft(b), new Vector2d(0, 1));
    }

    @Test
    void add() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(1, 1);
        Vector2d c = new Vector2d(0, 4);

        //then:
        assertEquals(a.add(b), new Vector2d(3, 4));
        assertEquals(b.add(a), new Vector2d(3, 4));
        assertEquals(a.add(c), new Vector2d(2, 7));
        assertEquals(c.add(a), new Vector2d(2, 7));
        assertEquals(b.add(c), new Vector2d(1, 5));
        assertEquals(c.add(b), new Vector2d(1, 5));
    }

    @Test
    void subtract() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(1, 1);
        Vector2d c = new Vector2d(0, 4);

        //then:
        assertEquals(a.subtract(b), new Vector2d(1, 2));
        assertEquals(b.subtract(a), new Vector2d(-1, -2));
        assertEquals(a.subtract(c), new Vector2d(2, -1));
        assertEquals(c.subtract(a), new Vector2d(-2, 1));
        assertEquals(b.subtract(c), new Vector2d(1, -3));
        assertEquals(c.subtract(b), new Vector2d(-1, 3));
    }

    @Test
    void opposite() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(-1, 1);
        Vector2d c = new Vector2d(0, 4);
        Vector2d d = new Vector2d(0, 0);
        Vector2d e = new Vector2d(-1, -5);

        //then:
        assertEquals(a.opposite(), new Vector2d(-2, -3));
        assertEquals(b.opposite(), new Vector2d(1, -1));
        assertEquals(c.opposite(), new Vector2d(-0, -4));
        assertEquals(d.opposite(), new Vector2d(-0, -0));
        assertEquals(e.opposite(), new Vector2d(1, 5));
    }

    @Test
    void testToString() {
        //given:
        Vector2d a = new Vector2d(2, 3);
        Vector2d b = new Vector2d(-1, 1);
        Vector2d c = new Vector2d(0, -4);

        //then:
        assertEquals(a.toString(), "(2,3)");
        assertEquals(b.toString(), "(-1,1)");
        assertEquals(c.toString(), "(0,-4)");
    }
}