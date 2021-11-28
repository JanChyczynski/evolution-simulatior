package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationEngineTest {

    @Test
    void run() {
        IWorldMap map = new RectangularMap(2,2);
        SimulationEngine engine = new SimulationEngine(
                new OptionParser().parse(new String[]{"f", "r", "f", "r", "f"}),
                map, new Vector2d[]{new Vector2d(0, 0), new Vector2d(1, 1)});

        engine.run();

        assert(map.isOccupied(new Vector2d(0, 2)));
        assert(map.isOccupied(new Vector2d(1, 1)));
        assertEquals(((Animal)(map.objectAt(new Vector2d(0,2)).getInstanceOfClass(Animal.class))).getPosition(), new Vector2d(0,2));
        assertEquals(((Animal)(map.objectAt(new Vector2d(0,2)).getInstanceOfClass(Animal.class))).getOrientation(), MapDirection.NORTH);
        assertEquals(((Animal)(map.objectAt(new Vector2d(1,1)).getInstanceOfClass(Animal.class))).getPosition(), new Vector2d(1, 1));
        assertEquals(((Animal)(map.objectAt(new Vector2d(1,1)).getInstanceOfClass(Animal.class))).getOrientation(), MapDirection.SOUTH);
    }
}