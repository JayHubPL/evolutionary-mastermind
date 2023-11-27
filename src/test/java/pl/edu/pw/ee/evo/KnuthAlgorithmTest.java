package pl.edu.pw.ee.evo;

import org.junit.Test;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.MastermindGame;
import pl.edu.pw.ee.knuth.KnuthAlgorithm;

import java.util.List;

public class KnuthAlgorithmTest {

    @Test
    public void basicFunctionalityTest() {
        var gameVariant = GameVariant.classic(true);
        var initialCode = new Code(gameVariant, List.of(Color.of(0), Color.of(0), Color.of(1), Color.of(1)));
        var knuth = new KnuthAlgorithm(gameVariant, initialCode);
        var game = new MastermindGame(knuth, gameVariant);
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
