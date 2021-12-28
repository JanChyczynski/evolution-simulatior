package agh.ics.oop;

public interface IStatisticsObserver {
    void statisticsChanged(int day, int animalsNumber, int plantsNumber,
                           double averageEnergy, double averageLifespan, double averageChildrenNumber, Genome genomeMode);
}
