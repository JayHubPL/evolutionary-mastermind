package pl.edu.pw.ee.evo.operators.impl;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Crosser;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.utils.Pair;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OnePointSplitCrosser implements Crosser {

    private static final Random random = new Random();
    private final GameVariant gameVariant;
    private final double crossingProbability;

    @Override
    public CrossoverResults cross(List<Pair<Specimen, Specimen>> pairs) {
        var results = new CrossoverResults();
        for (var pair : pairs) {
            if (random.nextDouble() > crossingProbability) {
                results.getNonCrossedSpecimens().add(pair.first);
                results.getNonCrossedSpecimens().add(pair.second);
            }
            int splitIndex = random.nextInt(gameVariant.getCodeLength() - 1) + 1;
            var subSeq1 = pair.first.getCodeSequence().subList(0, splitIndex);
            var subSeq2 = pair.second.getCodeSequence().subList(splitIndex, gameVariant.getCodeLength());
            var child1 = new Specimen(new Code(gameVariant, Stream.concat(subSeq1.stream(), subSeq2.stream()).toList()));
            var child2 = new Specimen(new Code(gameVariant, Stream.concat(subSeq2.stream(), subSeq1.stream()).toList()));
            results.getOffsprings().add(child1);
            results.getOffsprings().add(child2);
        }
        return results;
    }

}
