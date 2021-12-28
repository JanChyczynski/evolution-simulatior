package agh.ics.oop;

import java.util.OptionalInt;

public interface ITrackable extends IMapElement {
    int getNewChildrenNumber(int startDay);

    int getNewAncestorsNumber(int startDay);

    OptionalInt getDeathDay();

    Genome getGenome();
}
