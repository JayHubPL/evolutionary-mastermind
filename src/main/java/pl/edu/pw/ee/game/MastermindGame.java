package pl.edu.pw.ee.game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MastermindGame {

    private final CodeMaker codeMaker;
    private final List<Guess> previousAttempts = new ArrayList<>();
    private final GameVariant variant;

    private Boolean guessesLimitReached = false;
    private Boolean codeBreakerWon = false;

    public MastermindGame(GameVariant variant) {
        codeMaker = new CodeMaker(variant);
        this.variant = variant;
    }

    public boolean checkIfCanGuess() {
        return !guessesLimitReached && !codeBreakerWon;
    }

    public Guess makeGuess(Code guessCode) {
        if (!checkIfCanGuess()) {
            throw new IllegalStateException("Can't make a guess because game has ended.");
        }
        var guess = codeMaker.evaluateGuess(guessCode);
        guessesLimitReached = previousAttempts.size() < variant.getGuessesLimit();
        codeBreakerWon = guess.getScore().getCorrectColorAndPlace().equals(variant.getCodeLength());
        return guess;
    }

}
