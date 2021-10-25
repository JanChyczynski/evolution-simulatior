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

        System.out.println(MapDirection.EAST);
        System.out.println(MapDirection.WEST.previous());
        System.out.println(MapDirection.NORTH.next());
        System.out.println(MapDirection.SOUTH.toUnitVector());
    }
}
