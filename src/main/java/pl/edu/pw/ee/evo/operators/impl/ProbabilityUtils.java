package pl.edu.pw.ee.evo.operators.impl;

import java.util.LinkedList;
import java.util.List;

public class ProbabilityUtils {

    public static List<Double> calculateCumulativeDistribution(List<Double> probabilities) {
        var cumulativeDistribution = new LinkedList<Double>();
        cumulativeDistribution.add(probabilities.get(0));
        for (int i = 1; i < probabilities.size(); i++) {
            cumulativeDistribution.add(probabilities.get(i) + cumulativeDistribution.get(i - 1));
        }
        return cumulativeDistribution;
    }

    public static <T> T randomize(List<T> items, List<Double> cumulativeDistribution, Double p) {
        assert items.size() == cumulativeDistribution.size();
        for (int i = cumulativeDistribution.size() - 1; i > 0; i--) {
            if (cumulativeDistribution.get(i) <= p) {
                return items.get(i);
            }
        }
        return items.get(0);
    }

}
