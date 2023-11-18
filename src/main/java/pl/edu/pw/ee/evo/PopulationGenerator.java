package pl.edu.pw.ee.evo;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PopulationGenerator {

    private static final Random random = new Random();
    private final GameVariant gameVariant;
    private final List<Code> possibleCodes = new LinkedList<>();

    public PopulationGenerator(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
        initializeCodePool();
    }

    public List<Specimen> generatePopulation(int size, boolean duplicatesAllowed) {
        Collections.shuffle(possibleCodes);
        if (duplicatesAllowed) {
            return generatePopulationWithDuplicatesAllowed(size);
        }
        return generatePopulationWithoutDuplicates(size);
    }

    private List<Specimen> generatePopulationWithDuplicatesAllowed(int size) {
        var population = new LinkedList<Specimen>();
        while (population.size() < size) {
            population.add(new Specimen(gameVariant, possibleCodes.get(random.nextInt(possibleCodes.size()))));
        }
        return population;
    }

    private List<Specimen> generatePopulationWithoutDuplicates(int size) {
        return possibleCodes.stream()
                .limit(size)
                .map(code -> new Specimen(gameVariant, code))
                .toList();
    }

    private void initializeCodePool() {
        expandCombination(new LinkedList<>());
    }

    @SuppressWarnings("unchecked")
    private void expandCombination(LinkedList<Color> prevSeq) {
        for (int colorIndex = 0; colorIndex < gameVariant.getNumberOfColors(); colorIndex++) {
            if (!gameVariant.getDuplicateColorsAllowed() && prevSeq.contains(Color.of(colorIndex))) {
                continue;
            }
            var currSeq = (LinkedList<Color>) prevSeq.clone();
            currSeq.add(Color.of(colorIndex));
            if (currSeq.size() == gameVariant.getCodeLength()) {
                possibleCodes.add(new Code(gameVariant, currSeq));
                continue;
            }
            expandCombination(currSeq);
        }
    }

}
