package pl.edu.pw.ee.evo.operators.impl;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Selector;
import pl.edu.pw.ee.utils.StatisticsUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RankBasedSelector implements Selector {

    private static final Random random = new Random();
    private final List<Double> cumulativeDistribution;

    public RankBasedSelector(int numberOfRanks) {
        var rankSum = numberOfRanks * (double) (numberOfRanks + 1) / 2;
        var probabilities = IntStream.range(1, numberOfRanks + 1)
                .mapToObj(rank -> 1. - (rank + 1) / rankSum)
                .toList();
        cumulativeDistribution = StatisticsUtils.calculateCumulativeDistribution(probabilities);
    }

    @Override
    public List<Specimen> select(List<Specimen> population) {
        population.sort(Comparator.comparing(Specimen::getFitness).reversed());
        return random.doubles(population.size())
                .mapToObj(p -> StatisticsUtils.randomize(population, cumulativeDistribution, p))
                .collect(Collectors.toList());
    }

}
