package pl.edu.pw.ee.evo.operators.impl;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Selector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
        var cumulativeDistribution = calculateCumulativeDistribution(probabilities);
        var selected = new LinkedList<Specimen>();
        for (int i = 0; i < population.size(); i++) {
            var p = random.nextDouble();
            for (int j = cumulativeDistribution.size() - 1; j >= 0; j--) {
                if (cumulativeDistribution.get(j) <= p || j == 0) {
                    selected.add(population.get(j));
                    break;
                }
            }
        }
        return selected;
    }

    private List<Double> calculateCumulativeDistribution(List<Double> probabilities) {
        var cumulativeDistribution = new LinkedList<Double>();
        cumulativeDistribution.add(probabilities.get(0));
        for (int i = 1; i < probabilities.size(); i++) {
            cumulativeDistribution.add(probabilities.get(i) + cumulativeDistribution.get(i - 1));
        }
        return cumulativeDistribution;
    }

}
