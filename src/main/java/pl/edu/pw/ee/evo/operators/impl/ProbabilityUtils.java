package pl.edu.pw.ee.evo.operators.impl;

import java.util.ArrayList;
import java.util.List;

public class ProbabilityUtils {

    public static List<Double> calculateCumulativeDistribution(List<Double> probabilities) {
        var cumulativeDistribution = new ArrayList<Double>(probabilities.size());
        cumulativeDistribution.add(probabilities.get(0));
        for (int i = 1; i < probabilities.size(); i++) {
            cumulativeDistribution.add(probabilities.get(i) + cumulativeDistribution.get(i - 1));
        }
        return cumulativeDistribution;
    }

    public static <T> T randomize(List<T> items, List<Double> cumulativeDistribution, Double p) {
        return items.get(findIntervalIndex(cumulativeDistribution, p));
    }

    private static int findIntervalIndex(List<Double> cumulativeDistribution, Double p) {
        int left = 0;
        int right = cumulativeDistribution.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (cumulativeDistribution.get(mid) < p) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

}
