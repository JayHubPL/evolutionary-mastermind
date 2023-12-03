package pl.edu.pw.ee.simulation;

import lombok.Value;
import pl.edu.pw.ee.game.GameResults;

import java.util.List;

@Value
public class SimulationResults {

    List<GameResults> individualGameResults;
    int numberOfWins;
    int numberOfFails;
    double averageGuessCount;

    public SimulationResults(List<GameResults> gameResults) {
        individualGameResults = gameResults;
        numberOfWins = (int) gameResults.stream()
                .filter(gr -> gr.getGameState() == GameResults.GameState.GUESSED_SECRET_CODE)
                .count();
        numberOfFails = gameResults.size() - numberOfWins;
        averageGuessCount = gameResults.stream()
                .mapToInt(GameResults::getNumberOfAttempts)
                .average().orElse(0.0);
    }

}