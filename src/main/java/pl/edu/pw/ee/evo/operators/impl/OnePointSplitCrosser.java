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
    public List<Specimen> cross(Pair<Specimen, Specimen> pair) {
        if (random.nextDouble() > crossingProbability) {
            return List.of(pair.first, pair.second);
        }
        int splitIndex = random.nextInt(gameVariant.getCodeLength() - 1) + 1;
        var subSeq1 = pair.first.getCodeSequence().subList(0, splitIndex);
        var subSeq2 = pair.second.getCodeSequence().subList(splitIndex, gameVariant.getCodeLength());
        var child1 = new Specimen(new Code(gameVariant, Stream.concat(subSeq1.stream(), subSeq2.stream()).toList()));
        var child2 = new Specimen(new Code(gameVariant, Stream.concat(subSeq2.stream(), subSeq1.stream()).toList()));
        return List.of(child1, child2);
    }

}
