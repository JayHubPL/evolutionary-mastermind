package pl.edu.pw.ee.game;

import org.junit.Test;

import java.util.List;

public class MastermindGameTest {

    @Test
    public void debug() {
        var gameVariant = GameVariant.classic(true);
        var password = new Code(gameVariant, List.of(Color.of(4), Color.of(5), Color.of(0), Color.of(1)));
        var guessCode = new Code(gameVariant, List.of(Color.of(2), Color.of(5), Color.of(0), Color.of(0)));
        System.out.println(password.compareTo(guessCode));
        System.out.println(guessCode.compareTo(password));
    }

}
