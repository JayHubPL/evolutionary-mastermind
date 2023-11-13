package pl.edu.pw.ee.codebreakers;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.CodeBreaker;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.MastermindGame;

import java.util.ArrayList;
import java.util.List;

public class PlayerCodeBreaker implements CodeBreaker {

    private List<Color> currentCodeSequence = new ArrayList<>();

    @Override
    public Code makeGuess(MastermindGame gameState) {
        return new Code(gameState.getVariant(), currentCodeSequence);
    }

    public void setCurrentCodeSequence(List<Integer> colorIndexSequence) {
        currentCodeSequence = colorIndexSequence.stream().map(Color::of).toList();
    }
}
