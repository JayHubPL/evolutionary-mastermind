package pl.edu.pw.ee.simulation;

import org.junit.Test;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameResults;
import pl.edu.pw.ee.game.GameVariant;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SimulationRunnerTest {

    @Test
    public void debug() throws ExecutionException, InterruptedException {
        var gameVariant = GameVariant.classic(true);
        var secretCode = new Code(gameVariant);
        System.out.println(secretCode);
        var initialGuess = new Code(gameVariant);
        var simulationConfig = SimulationConfig.builder()
                .gameVariant(gameVariant)
                .mutationChance(0.05)
                .secretCode(secretCode)
                .initialGuess(initialGuess)
                .initialPopulationDuplicatesAllowed(true)
                .populationSize(100)
                .build();
        var simulationRunner = new EvoAlgorithmSimulationRunner(1, simulationConfig);
        simulationRunner.execute();
        simulationRunner.get().getIndividualSimulationResults().stream()
                .map(GameResults::getAttemptHistory)
                .flatMap(List::stream)
                .forEach(guess -> {
                    var score = guess.getScore();
                    var expectedScore = guess.getCode().compareTo(secretCode);
                    System.out.printf("%s %d %d | %d %d | %s%n", guess.getCode().getCodeSequence(), score.getCorrectColorAndPlace(), score.getColorPresentButWrongPlace(),
                            expectedScore.getCorrectColorAndPlace(), expectedScore.getColorPresentButWrongPlace(), score.equals(expectedScore));
                });
    }

}
