package pl.edu.pw.ee.game;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class GameResults {

    List<Guess> attemptHistory;
    GameState gameState;
    Code secretCode;

    public int getNumberOfAttempts() {
        return attemptHistory.size();
    }

    public enum GameState {
        GUESSED_SECRET_CODE,
        GUESS_LIMIT_REACHED,
        NOT_FINISHED,
    }

}
