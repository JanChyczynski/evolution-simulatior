package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionParserTest {

    @Test
    void parseValidInput() {
        OptionParser parser = new OptionParser();
        String[] input = {"f", "forward", "b", "backward", "r", "right", "l", "left"};
        MoveDirection[] expected = {MoveDirection.FORWARD, MoveDirection.FORWARD,
                                    MoveDirection.BACKWARD, MoveDirection.BACKWARD,
                                    MoveDirection.RIGHT, MoveDirection.RIGHT,
                                    MoveDirection.LEFT, MoveDirection.LEFT};
        assertArrayEquals(parser.parse(input), expected);
    }

    @Test
    void parseInvalidInput() {
        OptionParser parser = new OptionParser();
        String[] input = {"f", "forward", "b", "backward", "unknown", "r", "right", "", "l", "left"};

        assertThrows(IllegalArgumentException.class, ()->parser.parse(input));
    }
}