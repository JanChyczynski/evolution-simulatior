package agh.ics.oop;

import java.util.Arrays;

public class World {
    static void run(Direction[] args) {
        for (Direction arg : args) {
            String message = "Zwierzak idzie " + switch (arg) {
                case FORWARD -> "idzie do przodu";
                case BACKWARD -> "idzie do tyłu";
                case RIGHT -> "skręca w prawo";
                case LEFT -> "skręca w lewo";
            };
            System.out.println(message);

        }
    }

    static Direction[] stringsToDirections(String[] args) {
        Direction[] ret = new Direction[args.length];
        int j = 0;
        for (String arg : args) {
            switch (arg) {
                case "f" -> ret[j++] = Direction.FORWARD;
                case "b" -> ret[j++] = Direction.BACKWARD;
                case "r" -> ret[j++] = Direction.RIGHT;
                case "l" -> ret[j++] = Direction.LEFT;
            }
        }

        return Arrays.copyOfRange(ret, 0, j);
    }

    public static void main(String[] args) {
        MoveDirection[] directions = new OptionParser().parse(args);
//        IWorldMap map = new RectangularMap(10, 5);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(0,0), new Vector2d(10,10) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map);
    }
}
