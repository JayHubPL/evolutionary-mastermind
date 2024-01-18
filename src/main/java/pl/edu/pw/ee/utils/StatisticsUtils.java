package pl.edu.pw.ee.utils;

import java.util.ArrayList;
import java.util.List;

public class StatisticsUtils {

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

    public static double calculateUncertainty(List<Integer> values, double mean) {
        return 1.96 * (calculateStandardDeviation(values, mean) / Math.sqrt(values.size()));
    }

    private static double calculateStandardDeviation(List<Integer> values, double mean) {
        var sumSquaredDiff = values.stream()
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .sum();
        var variance = sumSquaredDiff / values.size();
        return Math.sqrt(variance);
    }

}
