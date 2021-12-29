package agh.ics.oop;

public record StatisticsEntry(int day, int animalsNumber, int plantsNumber,
                              double averageEnergy, double averageLifespan, double averageChildrenNumber, Genome genomeMode) {
    static public String csvHeader(){
        return csvHeader(";");
    }

    static public String csvHeader(String separator){
        return "day" + separator +
               "animalsNumber" + separator +
               "plantsNumber" + separator +
               "averageEnergy" + separator +
               "averageLifespan" + separator +
               "averageChildrenNumber";
    }

    public String asCsvRecord(){
        return asCsvRecord(";");
    }

    public String asCsvRecord(String separator){
        return day + separator +
               animalsNumber + separator +
               plantsNumber + separator +
               averageEnergy + separator +
               averageLifespan + separator +
               averageChildrenNumber;
    }

    public StatisticsEntry add(StatisticsEntry rhs){
        return new StatisticsEntry(day + rhs.day,
                                   animalsNumber + rhs.animalsNumber,
                                   plantsNumber + rhs.plantsNumber,
                                   averageEnergy + rhs.averageEnergy,
                                   averageLifespan + rhs.averageLifespan,
                                   averageChildrenNumber + rhs.averageChildrenNumber, genomeMode
                );
    }

    public StatisticsEntry divide(int divisor){
        return new StatisticsEntry(day/divisor,
                animalsNumber/divisor,
                plantsNumber/divisor,
                averageEnergy/divisor,
                averageLifespan/divisor,
                averageChildrenNumber/divisor,
                genomeMode
        );
    }

    @Override
    public String toString() {
        return asCsvRecord();
    }
}
