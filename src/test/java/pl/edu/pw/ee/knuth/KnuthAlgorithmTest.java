package pl.edu.pw.ee.knuth;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.junit.Test;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.CodeMaker;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.MastermindGame;
import pl.edu.pw.ee.utils.CodePoolGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class KnuthAlgorithmTest {

    @Test
    public void statisticsForClassicVariant() {
        var gameVariant = GameVariant.classic(true);
        var initialGuess = new Code(gameVariant, List.of(Color.of(0), Color.of(0), Color.of(1), Color.of(1)));
        collectStatisticsMultiThreaded(gameVariant, initialGuess, 8);
    }

    @Test
    public void statisticsForClassicNRVariant() {
        var gameVariant = GameVariant.classic(false);
        var initialGuess = new Code(gameVariant, List.of(Color.of(0), Color.of(1), Color.of(2), Color.of(3)));
        collectStatisticsMultiThreaded(gameVariant, initialGuess, 8);
    }

    @Test
    public void statisticsForDeluxeVariant() {
        var gameVariant = GameVariant.deluxe(true);
        var initialGuess = new Code(gameVariant, List.of(Color.of(0), Color.of(0), Color.of(1), Color.of(2), Color.of(3)));
        collectStatisticsMultiThreaded(gameVariant, initialGuess, 16);
    }

    @Test
    public void statisticsForDeluxeNRVariant() {
        var gameVariant = GameVariant.deluxe(false);
        var initialGuess = new Code(gameVariant, List.of(Color.of(0), Color.of(1), Color.of(2), Color.of(3), Color.of(4)));
        collectStatisticsMultiThreaded(gameVariant, initialGuess, 16);
    }

    private Statistics collectStatistics(GameVariant gameVariant, Code initialGuess, List<Code> secretCodes) {
        var guessCounts = new ArrayList<Integer>();
        var simulationTime = 0L;
        for (var secretCode : secretCodes) {
            var game = new MastermindGame(new CodeMaker(secretCode), new KnuthAlgorithm(gameVariant, initialGuess), gameVariant);
            var startTime = System.currentTimeMillis();
            var gameResults = game.play();
            simulationTime += System.currentTimeMillis() - startTime;
            var guessCount = gameResults.getAttemptHistory().size();
            guessCounts.add(guessCount);
        }
        System.out.printf("[%d] Routine finished!%n", Thread.currentThread().getId()).flush();
        return Statistics.of(guessCounts, simulationTime);
    }

    @SneakyThrows
    private void collectStatisticsMultiThreaded(GameVariant gameVariant, Code initialGuess, int threadCount) {
        var secretCodes = CodePoolGenerator.getAllPossibleCodes(gameVariant);
        var secretCodesPerThread = secretCodes.size() / threadCount;
        var executor = Executors.newFixedThreadPool(threadCount);
        var completionService = new ExecutorCompletionService<Statistics>(executor);
        for (int i = 0; i < threadCount; i++) {
            var threadSecretCodes = secretCodes.subList(i * secretCodesPerThread, (i == threadCount - 1 ? secretCodes.size() : ((i + 1) * secretCodesPerThread)));
            completionService.submit(() -> collectStatistics(gameVariant, initialGuess, threadSecretCodes));
            System.out.printf("Deploying routine #%d%n", i + 1);
        }
        var statistics = new Statistics();
        for (int i = 0; i < threadCount; i++) {
            statistics.merge(completionService.take().get());
        }
        System.out.println("All routines finished!");
        statistics.summary();
        executor.shutdown();
    }

    private static double calculateMarginOfError(List<Integer> values, double mean) {
        return 1.96 * (calculateStandardDeviation(values, mean) / Math.sqrt(values.size()));
    }

    private static double calculateStandardDeviation(List<Integer> values, double mean) {
        var sumSquaredDiff = values.stream()
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .sum();
        var variance = sumSquaredDiff / values.size();
        return Math.sqrt(variance);
    }

    @AllArgsConstructor(staticName = "of")
    static class Statistics {

        private List<Integer> guessCounts;
        private Long simulationTime;

        public Statistics() {
            guessCounts = new LinkedList<>();
            simulationTime = 0L;
        }

        public void merge(Statistics other) {
            guessCounts.addAll(other.guessCounts);
            simulationTime += other.simulationTime;
        }

        public void summary() {
            var mean = guessCounts.stream().mapToInt(Integer::intValue).average().orElse(.0);
            var marginOfError = calculateMarginOfError(guessCounts, mean);
            var guessCountsGrouped = new HashMap<Integer, Integer>();
            guessCounts.forEach(guessCount -> guessCountsGrouped.put(guessCount, guessCountsGrouped.getOrDefault(guessCount, 0) + 1));

            System.out.printf("Total simulation time: %.2f sec%n", simulationTime / 1000.);
            System.out.printf("Confidence interval: %.3f±%.5f%n", mean, marginOfError);
            System.out.println("Guess counts:");
            IntStream.rangeClosed(1, 12)
                    .forEach(i -> System.out.printf("%d: %s%n", i, Objects.toString(guessCountsGrouped.get(i), "0")));
            var totalGuesses = guessCountsGrouped.entrySet().stream().mapToInt(entry -> entry.getKey() * entry.getValue()).sum();
            System.out.printf("Total guesses: %d%n", totalGuesses);
        }

    }

}
