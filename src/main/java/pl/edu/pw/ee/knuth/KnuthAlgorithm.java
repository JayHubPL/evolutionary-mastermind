package pl.edu.pw.ee.knuth;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.CodeBreaker;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.MastermindGame;
import pl.edu.pw.ee.game.Score;
import pl.edu.pw.ee.utils.CodePoolGenerator;
import pl.edu.pw.ee.utils.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class KnuthAlgorithm implements CodeBreaker {

    private final Code initialGuess;
    private final List<Code> allCombinations;
    private final List<Code> possibleCodes;

    public KnuthAlgorithm(GameVariant gameVariant, Code initialGuess) {
        this.initialGuess = initialGuess;
        allCombinations = CodePoolGenerator.generateAllPossibleCodes(gameVariant);
        possibleCodes = new ArrayList<>(allCombinations);
    }

    @Override
    public Code makeGuess(MastermindGame gameState) {
        if (gameState.getPreviousAttempts().isEmpty()) {
            return initialGuess;
        }
        var lastGuess = gameState.getPreviousAttempts().get(gameState.getPreviousAttempts().size() - 1);
        possibleCodes.removeIf(possibleCode -> !possibleCode.compareTo(lastGuess.getCode()).equals(lastGuess.getScore()));
        if (possibleCodes.size() == 1) {
            return possibleCodes.get(0);
        }
        return allCombinations.stream()
                .map(code -> Pair.of(code, calculateMaximumPartitionSize(code, possibleCodes)))
                .min(Comparator.<Pair<Code, Integer>, Integer>comparing(Pair::getSecond).thenComparing(p -> p.getFirst().toString()))
                .map(Pair::getFirst)
                .orElseThrow();
    }

    private int calculateMaximumPartitionSize(Code code, List<Code> possibleCodes) {
        var queryPartitionClassCounts = new HashMap<Score, Integer>();
        for (var possibleCode : possibleCodes) {
            var relativeScore = possibleCode.compareTo(code);
            queryPartitionClassCounts.put(relativeScore, queryPartitionClassCounts.getOrDefault(relativeScore, 0) + 1);
        }
        return queryPartitionClassCounts.values().stream()
                .max(Comparator.comparingInt(Integer::intValue))
                .orElse(Integer.MAX_VALUE);
    }

}
