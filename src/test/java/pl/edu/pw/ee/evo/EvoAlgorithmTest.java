package pl.edu.pw.ee.evo;

import org.junit.Test;
import pl.edu.pw.ee.evo.operators.impl.ConsecutivePairMatcher;
import pl.edu.pw.ee.evo.operators.impl.NoScaler;
import pl.edu.pw.ee.evo.operators.impl.OnePointSplitCrosser;
import pl.edu.pw.ee.evo.operators.impl.StandardEvaluator;
import pl.edu.pw.ee.evo.operators.impl.UnbalancedRouletteSelector;
import pl.edu.pw.ee.evo.operators.impl.ValueShiftMutator;
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
                .scaler(new NoScaler())
                .selector(new UnbalancedRouletteSelector())
                .pairMatcher(new ConsecutivePairMatcher())
                .crosser(new OnePointSplitCrosser(gameVariant, 0.9))
                .shouldMutateSurvivors(false)
                .mutator(new ValueShiftMutator(gameVariant, 0.01))
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
