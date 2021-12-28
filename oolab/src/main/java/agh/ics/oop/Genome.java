package agh.ics.oop;

import agh.ics.oop.MapDirection;
import agh.ics.oop.gui.Randomizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Genome {
    public static final int SIZE = 32;
    public static final int MAX_VALUE = MapDirection.values().length-1;
    private final List<Integer> genes;

    public Genome() {
        this.genes = new ArrayList<>(SIZE);
        randomize();
    }

    public Genome(List<Integer> genes){
        this.genes = genes;
        sort();
    }


    public List<Integer> getGenes() {
        return genes;
    }

    public int getRandomGene(){
        return genes.get(Randomizer.randInt(0, SIZE-1));
    }

    public Genome combined(Genome other, int genesNumber){
        if(genesNumber > SIZE) {
            throw new IllegalArgumentException("genesNumber to combine larger than the genome");
        }
        this.sort();
        other.sort();
        return ((Randomizer.randInt(0, 1) == 0) ?
                new Genome(Stream.concat(genes.subList(0, SIZE - genesNumber).stream(),
                        other.getGenes().subList(SIZE - genesNumber, SIZE).stream()).collect(Collectors.toList())) :
                new Genome(Stream.concat(other.getGenes().subList(0, genesNumber).stream(),
                        genes.subList(genesNumber, SIZE).stream()).collect(Collectors.toList())));

    }

    private void randomize() {
        for (int i = 0; i < SIZE; i++) {
            genes.add(Randomizer.randInt(0, MAX_VALUE));
        }
        sort();
    }

    public void sort() {
        Collections.sort(genes);
    }

    @Override
    public String toString() {
        return genes.toString().replace(",", "")
                .replace("[", "")
                .replace("]", "")
                .trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genome genome = (Genome) o;
        return Objects.equals(genes, genome.genes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes);
    }
}
