package pl.edu.pw.ee.simulation;

import lombok.Value;
import pl.edu.pw.ee.game.GameResults;

import java.util.List;

@Value
public class SimulationStatistics {

    List<GameResults> individualSimulationResults;
    int numberOfWins;
    int numberOfFails;
    double averageGuessCount;

    public SimulationStatistics(List<GameResults> gameResults) {
        individualSimulationResults = gameResults;
        numberOfWins = (int) gameResults.stream()
                .filter(gr -> gr.getGameState() == GameResults.GameState.GUESSED_SECRET_CODE)
                .count();
        numberOfFails = gameResults.size() - numberOfWins;
        averageGuessCount = gameResults.stream()
                .mapToInt(GameResults::getNumberOfAttempts)
                .average().orElse(0.0);
    }

}