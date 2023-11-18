package pl.edu.pw.ee.evo.operators;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.game.GameVariant;

import java.util.Random;

@RequiredArgsConstructor
public class ColorShiftMutator implements Mutator {

    private static final Random random = new Random();
    private final GameVariant gameVariant;
    private final Double mutationProbability;

    @Override
    public void mutate(Specimen specimen) {
        specimen.getCodeSequence().forEach(color -> {
            if (random.nextDouble() <= mutationProbability) {
                var valueShift = random.nextDouble() <= 0.5 ? -1 : 1;
                var newColorIndex = (color.getIndex() + valueShift) % gameVariant.getNumberOfColors();
                if (newColorIndex == -1) {
                    newColorIndex = gameVariant.getNumberOfColors() - 1;
                }
                color.setIndex(newColorIndex);
            }
        });
    }

}
