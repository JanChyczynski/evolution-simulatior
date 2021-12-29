package agh.ics.oop;

import java.util.Objects;

import static java.lang.Math.max;
import static java.lang.Math.min;

public record Vector2d(int x, int y) {
    public Vector2d(Vector2d another){
        this(another.x(), another.y());
    }

    public boolean precedes(Vector2d other) {
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return x >= other.x && y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(max(x, other.x), max(y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(min(x, other.x), min(y, other.y));
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }
    public Vector2d subtract(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }

    public Vector2d opposite(){
        return new Vector2d(-x, -y);
    }

    public Vector2d wrapped(Vector2d bottomLeftCorner, Vector2d upperRightCorner){
        return new Vector2d(
                x% (upperRightCorner.x()+1-bottomLeftCorner.x())+bottomLeftCorner.x(),
                y% (upperRightCorner.y()+1-bottomLeftCorner.y())+bottomLeftCorner.y());
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2d vector2d = (Vector2d) o;
        return x == vector2d.x && y == vector2d.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
