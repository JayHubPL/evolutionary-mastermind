package pl.edu.pw.ee.evo.operators.impl;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Selector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class TournamentSelector implements Selector {

    private static final Random random = new Random();
    private final Integer tournamentSize;

    @Override
    public List<Specimen> select(List<Specimen> population) {
        var selected = new ArrayList<Specimen>();
        for (int i = 0; i < population.size(); i++) {
            selected.add(random.ints(tournamentSize, 0, population.size())
                    .mapToObj(population::get)
                    .max(Comparator.comparing(Specimen::getFitness))
                    .orElseThrow());
        }
        return selected;
    }

}
