package pl.edu.pw.ee.knuth;

import pl.edu.pw.ee.game.*;
import pl.edu.pw.ee.utils.CodePoolGenerator;
import pl.edu.pw.ee.utils.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class KnuthAlgorithm implements CodeBreaker {

    private final Code initialGuess;
    private final List<Code> possibleCodes;

    public KnuthAlgorithm(GameVariant gameVariant, Code initialGuess) {
        this.initialGuess = initialGuess;
        possibleCodes = CodePoolGenerator.generateAllPossibleCodes(gameVariant);
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
        return possibleCodes.stream()
                .map(code -> Pair.of(code, calculateMaximumPartitionSize(code, possibleCodes)))
                .min((p1, p2) -> {
                    var c1 = p1.getSecond().compareTo(p2.getSecond());
                    if (c1 == 0) {
                        return p1.getFirst().toString().compareTo(p2.getFirst().toString());
                    }
                    return c1;
                })
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
