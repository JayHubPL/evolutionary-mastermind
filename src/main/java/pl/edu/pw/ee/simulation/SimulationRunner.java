package pl.edu.pw.ee.simulation;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.EvoAlgorithm;
import pl.edu.pw.ee.evo.EvoAlgorithmConfig;
import pl.edu.pw.ee.evo.PopulationGenerator;
import pl.edu.pw.ee.evo.operators.*;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.CodeMaker;
import pl.edu.pw.ee.game.MastermindGame;
import pl.edu.pw.ee.gui.utils.ProgressListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.*;

@RequiredArgsConstructor
public class SimulationRunner extends SwingWorker<Void, Void> {

    private final ProgressListener progressListener;
    private final SimulationConfig config;
    private final Integer numberOfSimulations;

    @Override
    protected Void doInBackground() {
        ExecutorService executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        var gameVariant = config.getGameVariant();
        var populationGenerator = new PopulationGenerator(gameVariant);
        var evaluator = new StandardEvaluator();
        var selector = new UnbalancedRouletteSelector();
        var pairMatcher = new ConsecutivePairMatcher();
        var crosser = new StandardCrosser(gameVariant);
        var mutator = new ColorShiftMutator(gameVariant, config.getMutationChance());

        var results = new ArrayList<Future<Integer>>();

        for (int i = 0; i < numberOfSimulations; i++) {
            var secretCode = config.getSecretCode().orElse(new Code(gameVariant));
            var evoAlgorithmConfig = EvoAlgorithmConfig.builder()
                    .populationSize(config.getPopulationSize())
                    .initialGuess(config.getInitialGuess().orElse(new Code(gameVariant)))
                    .initialPopulationDuplicatesAllowed(config.isInitialPopulationDuplicatesAllowed())
                    .gameVariant(gameVariant)
                    .populationGenerator(populationGenerator)
                    .evaluator(evaluator)
                    .selector(selector)
                    .pairMatcher(pairMatcher)
                    .crosser(crosser)
                    .mutator(mutator)
                    .build();
            results.add(executorService.submit(() -> runSimulation(evoAlgorithmConfig, secretCode)));
        }

        var guessCounts = new ArrayList<Integer>();
        try {
            for (var future : results) {
                guessCounts.add(future.get());
                progressListener.update((double) guessCounts.size() / numberOfSimulations);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return null;
    }

    private int runSimulation(EvoAlgorithmConfig config, Code secretCode) {
        var evo = new EvoAlgorithm(config);
        var game = new MastermindGame(new CodeMaker(secretCode), evo, config.getGameVariant());
        while (game.checkIfCanGuess()) {
            game.makeGuess();
        }
        return game.getPreviousAttempts().size();
    }

}
