package agh.ics.oop;

import java.util.Arrays;

public class OptionParser {
    public MoveDirection[] parse(String[] input){
        MoveDirection[] ret = new MoveDirection[input.length];
        int j = 0;
        for (String s : input) {
            switch (s) {
                case "f", "forward" -> ret[j++] = MoveDirection.FORWARD;
                case "b", "backward" -> ret[j++] = MoveDirection.BACKWARD;
                case "r", "right" -> ret[j++] = MoveDirection.RIGHT;
                case "l", "left" -> ret[j++] = MoveDirection.LEFT;
            }
        }

        return Arrays.copyOfRange(ret, 0, j);
    }
}
