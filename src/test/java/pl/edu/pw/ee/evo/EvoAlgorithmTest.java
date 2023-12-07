package pl.edu.pw.ee.evo;

import org.junit.Test;
import pl.edu.pw.ee.evo.operators.impl.*;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.MastermindGame;

import java.util.List;

public class EvoAlgorithmTest {

    @Test
    public void basicFunctionalityTest() {
        var gameVariant = GameVariant.classic(true);
        var initialGuess = List.of(Color.of(0), Color.of(0), Color.of(1), Color.of(1));
        var config = EvoAlgorithmConfig.builder()
                .populationSize(50)
                .initialGuess(new Code(gameVariant, initialGuess))
                .initialPopulationDuplicatesAllowed(true)
                .gameVariant(gameVariant)
                .populationGenerator(new PopulationGenerator(gameVariant))
                .evaluator(new StandardEvaluator())
                .selector(new UnbalancedRouletteSelector())
                .pairMatcher(new ConsecutivePairMatcher())
                .crosser(new StandardCrosser(gameVariant))
                .mutator(new ColorShiftMutator(gameVariant, 0.01))
                .build();
        var evo = new EvoAlgorithm(config);
        var game = new MastermindGame(evo, gameVariant);
        while (game.checkIfCanGuess()) {
            System.out.println(game.makeGuess());
        }
        if (game.getCodeBreakerWon()) {
            System.out.println("Code has been broken!");
        } else {
            System.out.println("Limit reached!");
        }
    }

}
