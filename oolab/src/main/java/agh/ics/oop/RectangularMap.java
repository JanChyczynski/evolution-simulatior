package agh.ics.oop;

import static java.util.Objects.isNull;

public class RectangularMap implements IWorldMap{
    private final int height, width;
    private final Animal[][] map;
    private final MapVisualiser mapVisualiser;

    public RectangularMap(int height, int width) {
        this.height = height;
        this.width = width;

        map = new Animal[height+1][width+1];
        mapVisualiser = new MapVisualiser(this);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return inBounds(position) &&
                !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition()))
        {
            map[animal.getPosition().y()][animal.getPosition().x()] = animal;
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return inBounds(position) &&
                (!isNull(map[position.y()][position.x()]));

    }

    @Override
    public Object objectAt(Vector2d position) {
        return (inBounds(position))?
                map[position.y()][position.x()] :
                null;
    }

    @Override
    public boolean move(Vector2d start, Vector2d end) {
        if(isOccupied(start) && canMoveTo(end))
        {
            map[end.y()][end.x()] = map[start.y()][start.x()];
            map[start.y()][start.x()] = null;
            return true;
        }
        return false;
    }

    private boolean inBounds(Vector2d position){
        return (position.follows(new Vector2d(0,0)) &&
                position.precedes(new Vector2d(width, height)));
    }

    @Override
    public String toString() {
        return mapVisualiser.draw(new Vector2d(0,0), new Vector2d(width, height));
    }
}
