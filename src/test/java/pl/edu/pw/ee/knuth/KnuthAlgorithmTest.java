package pl.edu.pw.ee.knuth;

import org.junit.Test;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.CodeMaker;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.MastermindGame;

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

    @Test
    public void debugSixGuess() {
        var gameVariant = GameVariant.classic(true);
        var initialCode = new Code(gameVariant, List.of(Color.of(0), Color.of(0), Color.of(1), Color.of(1)));
        var secretCode = new Code(gameVariant, List.of(Color.of(3), Color.of(5), Color.of(3), Color.of(0)));
        var knuth = new KnuthAlgorithm(gameVariant, initialCode);
        var game = new MastermindGame(new CodeMaker(secretCode), knuth, gameVariant);
        while (game.checkIfCanGuess()) {
            System.out.println(game.makeGuess());
        }
        if (game.getCodeBreakerWon()) {
            System.out.println("Code has been broken!");
        } else {
            System.out.println("Limit reached!");
        }
        assert game.getPreviousAttempts().size() <= 5;
    }

}
