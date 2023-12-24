package pl.edu.pw.ee.evo.operators.impl;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Selector;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomSelector implements Selector {

    private static final Random random = new Random();

    @Override
    public List<Specimen> select(List<Specimen> population) {
        return random.ints(population.size(), 0, population.size())
                .mapToObj(population::get)
                .collect(Collectors.toList());
    }

}
