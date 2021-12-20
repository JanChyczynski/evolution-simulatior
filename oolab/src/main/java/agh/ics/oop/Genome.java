package agh.ics.oop;

import agh.ics.oop.MapDirection;
import agh.ics.oop.gui.Randomizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Genome {
    public static final int SIZE = 32;
    public static final int MAX_VALUE = MapDirection.values().length-1;
    private final List<Integer> genes;

    public Genome() {
        this.genes = new ArrayList<>(SIZE);
        randomize();
        System.out.println(genes.size());
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public int getRandomGene(){
        return genes.get(Randomizer.randInt(0, SIZE-1));
    }

    private void randomize() {
        for (int i = 0; i < SIZE; i++) {
            genes.add(Randomizer.randInt(0, MAX_VALUE));
        }
        Collections.sort(genes);
    }


}
