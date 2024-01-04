package pl.edu.pw.ee.simulation;

import lombok.Value;
import pl.edu.pw.ee.game.GameResults;

import java.util.ArrayList;
import java.util.List;

@Value
public class SimulationResults {

    static String[] HEADERS = {"id", "won", "guesses"};
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

    public List<String[]> toCsvData() {
        var data = new ArrayList<String[]>(individualGameResults.size() + 1);
        data.add(HEADERS);
        for (int i = 0; i < individualGameResults.size(); i++) {
            var result = individualGameResults.get(i);
            data.add(new String[]{
                    String.valueOf(i + 1),
                    result.getGameState() == GameResults.GameState.GUESSED_SECRET_CODE ? "1" : "0",
                    String.valueOf(result.getAttemptHistory().size())
            });
        }
        return data;
    }
}