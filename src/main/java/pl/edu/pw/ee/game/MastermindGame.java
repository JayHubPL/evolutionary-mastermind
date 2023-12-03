package pl.edu.pw.ee.game;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class MastermindGame {

    @Getter(AccessLevel.NONE)
    private final CodeMaker codeMaker;
    private final CodeBreaker codeBreaker;
    private final List<Guess> previousAttempts = new ArrayList<>();
    private final GameVariant variant;

    private Boolean guessesLimitReached = Boolean.FALSE;
    private Boolean codeBreakerWon = Boolean.FALSE;

    public MastermindGame(CodeBreaker codeBreaker, GameVariant variant) {
        this(new CodeMaker(variant), codeBreaker, variant);
    }

    public MastermindGame(CodeMaker codeMaker, CodeBreaker codeBreaker, GameVariant variant) {
        this.codeMaker = codeMaker;
        this.codeBreaker = codeBreaker;
        this.variant = variant;
    }

    public boolean checkIfCanGuess() {
        return !guessesLimitReached && !codeBreakerWon;
    }

    public Guess makeGuess() {
        if (!checkIfCanGuess()) {
            throw new IllegalStateException("Can't make a guess because game has ended.");
        }
        var guess = codeMaker.evaluateGuess(codeBreaker.makeGuess(this));
        previousAttempts.add(guess);
        guessesLimitReached = previousAttempts.size() == variant.getGuessesLimit();
        codeBreakerWon = guess.getScore().getCorrectColorAndPlace().equals(variant.getCodeLength());
        return guess;
    }

    public GameResults play() {
        while (checkIfCanGuess()) {
            makeGuess();
        }
        GameResults.GameState gameState = GameResults.GameState.NOT_FINISHED;
        if (guessesLimitReached) {
            gameState = GameResults.GameState.GUESS_LIMIT_REACHED;
        } else if (codeBreakerWon) {
            gameState = GameResults.GameState.GUESSED_SECRET_CODE;
        }
        return GameResults.builder()
                .attemptHistory(previousAttempts)
                .gameState(gameState)
                .secretCode(codeMaker.getSecretCode())
                .build();
    }

}
