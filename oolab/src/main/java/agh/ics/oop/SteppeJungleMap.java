package agh.ics.oop;

import java.util.function.Function;

import static java.util.Objects.isNull;

public class SteppeJungleMap extends AbstractWorldMap{
    private final int height, width;
    private int jungleHeight, jungleWidth;
    private final int grassEnergy;
    private Vector2d jungleBottomLeftCorner, jungleUpperRightCorner;
    private int plantsNumber;

    public SteppeJungleMap(SimulationParameters params){
        this(params.height(), params.width(), params.jungleRatio(), params.grassEnergy());
    }

    public SteppeJungleMap(int height, int width, int jungleRatio, int grassEnergy) {
        this.height = height;
        this.width = width;
        this.grassEnergy = grassEnergy;
        this.plantsNumber = 0;
        createJungle(jungleRatio);
    }

    private void createJungle(int jungleRatio){
        jungleWidth = width/jungleRatio;
        jungleHeight = width/jungleRatio;
        jungleBottomLeftCorner = new Vector2d((width-jungleWidth)/2, (height-jungleHeight)/2);
        jungleUpperRightCorner = new Vector2d(jungleBottomLeftCorner.x()+jungleWidth,
                                              jungleBottomLeftCorner.y()+jungleHeight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return inBounds(position) &&
                super.canMoveTo(position);
    }

    public void growGrass(){
        Vector2d position;
        Function<Vector2d, Boolean> isFreeOnSteppe = position1 -> !isOccupied(position1) && !inJungle(position1);
        position = getRandomPositionSatisfying(isFreeOnSteppe);
        if(!isNull(position))
        {
            addToMap(new Grass(position, grassEnergy));
            plantsNumber++;
        }
        position = getRandomPositionSatisfying((p) -> !isOccupied(p), jungleBottomLeftCorner, jungleUpperRightCorner);
        if(!isNull(position))
        {
            addToMap(new Grass(position, grassEnergy));
            plantsNumber++;
        }
    }

    public Vector2d getRandomPositionSatisfying(Function<Vector2d, Boolean> predicate, Vector2d bottomLeft, Vector2d upperRight) {
        Vector2d position;
        int tries = 10;
        do {
            position = new Vector2d(Randomizer.randInt(bottomLeft.x(), upperRight.y()),
                    Randomizer.randInt(bottomLeft.y(), upperRight.y()));
        }
        while ((tries-- > 0) && !predicate.apply(position));
        for (int x = bottomLeft.x(); x < upperRight.x(); x++) {
            for (int y = bottomLeft.y(); y < upperRight.y(); y++) {
                if(predicate.apply(position)){
                    return position;
                }
            }
        }

        return null;
    }

    public Vector2d getRandomPositionSatisfying(Function<Vector2d, Boolean> predicate){
        return getRandomPositionSatisfying(predicate, bottomLeftCorner(), upperRightCorner());
    }

    private boolean inBounds(Vector2d position){
        return (position.follows(bottomLeftCorner()) &&
                position.precedes(upperRightCorner()));
    }

    private boolean inJungle(Vector2d position){
        return (position.follows(jungleBottomLeftCorner) &&
                position.precedes(jungleUpperRightCorner));
    }

    @Override
    public Vector2d bottomLeftCorner(){
        return new Vector2d(0,0);
    }

    @Override
    public Vector2d upperRightCorner(){
        return new Vector2d(width, height);
    }

    @Override
    public void remove(IMapElement element){
        if(element instanceof  Grass){
            plantsNumber--;
        }
        super.remove(element);
    }



    public int getPlantsNumber() {
        return plantsNumber;
    }
}
