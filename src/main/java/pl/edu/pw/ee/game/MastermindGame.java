package pl.edu.pw.ee.game;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
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
        while (checkIfCanGuess() && !Thread.interrupted()) {
            makeGuess();
        }
        GameResults.GameState gameState = GameResults.GameState.NOT_FINISHED;
        if (codeBreakerWon) {
            gameState = GameResults.GameState.GUESSED_SECRET_CODE;
        } else if (guessesLimitReached) {
            gameState = GameResults.GameState.GUESS_LIMIT_REACHED;
        }
        return GameResults.builder()
                .attemptHistory(previousAttempts)
                .gameState(gameState)
                .secretCode(codeMaker.getSecretCode())
                .build();
    }

}
