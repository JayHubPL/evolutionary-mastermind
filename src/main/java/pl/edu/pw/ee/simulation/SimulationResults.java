package pl.edu.pw.ee.simulation;

import lombok.Value;
import pl.edu.pw.ee.game.GameResults;
import pl.edu.pw.ee.utils.StatisticsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class SimulationResults {

    static String[] HEADERS = {"id", "won", "guesses"};
    List<GameResults> individualGameResults;
    int numberOfWins;
    int numberOfFails;
    double averageGuessCount;
    double uncertainty;
    int maxGuessCount;
    long time;

    public SimulationResults(List<GameResults> gameResults, long time) {
        this.time = time;
        individualGameResults = gameResults;
        numberOfWins = (int) gameResults.stream()
                .filter(gr -> gr.getGameState() == GameResults.GameState.GUESSED_SECRET_CODE)
                .count();
        numberOfFails = gameResults.size() - numberOfWins;
        var guessCounts = gameResults.stream()
                .map(GameResults::getNumberOfAttempts)
                .collect(Collectors.toList());
        averageGuessCount = guessCounts.stream()
                .mapToInt(Integer::intValue)
                .average().orElse(0.0);
        uncertainty = StatisticsUtils.calculateUncertainty(guessCounts, averageGuessCount);
        maxGuessCount = gameResults.stream()
                .mapToInt(GameResults::getNumberOfAttempts)
                .max().orElse(-1);
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