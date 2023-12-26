package pl.edu.pw.ee.evo.operators.impl;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Selector;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UnbalancedRouletteSelector implements Selector {

    private static final Random random = new Random();

    @Override
    public List<Specimen> select(List<Specimen> population) {
        int totalFitness = population.stream()
                .map(Specimen::getFitness)
                .mapToInt(Integer::intValue)
                .sum();
        var probabilities = population.stream()
                .map(specimen -> specimen.getFitness() / (double) totalFitness)
                .toList();
        var cumulativeDistribution = ProbabilityUtils.calculateCumulativeDistribution(probabilities);
        return random.doubles(population.size())
                .mapToObj(p -> ProbabilityUtils.randomize(population, cumulativeDistribution, p))
                .collect(Collectors.toList());
    }

}