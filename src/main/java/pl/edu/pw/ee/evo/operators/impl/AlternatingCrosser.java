package pl.edu.pw.ee.evo.operators.impl;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Crosser;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.utils.Pair;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AlternatingCrosser implements Crosser {

    private final GameVariant gameVariant;

    @Override
    public List<Specimen> cross(Pair<Specimen, Specimen> parents) {
        var seq1 = new ArrayList<Color>(gameVariant.getCodeLength());
        var seq2 = new ArrayList<Color>(gameVariant.getCodeLength());
        for (int i = 0; i < gameVariant.getCodeLength(); i++) {
            seq1.add((i % 2 == 0 ? parents.first : parents.second).getCodeSequence().get(i));
            seq2.add((i % 2 == 0 ? parents.second : parents.first).getCodeSequence().get(i));
        }
        var child1 = new Specimen(new Code(gameVariant, seq1));
        var child2 = new Specimen(new Code(gameVariant, seq2));
        return List.of(child1, child2);
    }

}
