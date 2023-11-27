package pl.edu.pw.ee.utils;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;

import java.util.LinkedList;
import java.util.List;

public class CodePoolGenerator {

    public static List<Code> generateAllPossibleCodes(GameVariant gameVariant) {
        return expandCombination(gameVariant, new LinkedList<>());
    }

    @SuppressWarnings("unchecked")
    private static List<Code> expandCombination(GameVariant gameVariant, LinkedList<Color> seqPrefix) {
        var combinations = new LinkedList<Code>();
        for (int colorIndex = 0; colorIndex < gameVariant.getNumberOfColors(); colorIndex++) {
            if (!gameVariant.getDuplicateColorsAllowed() && seqPrefix.contains(Color.of(colorIndex))) {
                continue;
            }
            var currSeqPrefix = (LinkedList<Color>) seqPrefix.clone();
            currSeqPrefix.add(Color.of(colorIndex));
            if (currSeqPrefix.size() == gameVariant.getCodeLength()) {
                combinations.add(new Code(gameVariant, currSeqPrefix));
                continue;
            }
            combinations.addAll(expandCombination(gameVariant, currSeqPrefix));
        }
        return combinations;
    }

}
