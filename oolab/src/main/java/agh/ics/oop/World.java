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
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "f":
                    ret[j++] = Direction.FORWARD;
                    break;
                case "b":
                    ret[j++] = Direction.BACKWARD;
                    break;
                case "r":
                    ret[j++] = Direction.RIGHT;
                    break;
                case "l":
                    ret[j++] = Direction.LEFT;
                    break;
            }
        }

        return Arrays.copyOfRange(ret, 0, j);
    }

    public static void main(String[] args) {
        MoveDirection[] directions = new OptionParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map);
    }
}
