package pl.edu.pw.ee.evo;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.utils.CodePoolGenerator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PopulationGenerator {

    private static final Random random = new Random();
    private final List<Code> possibleCodes;

    public PopulationGenerator(GameVariant gameVariant) {
        possibleCodes = CodePoolGenerator.generateAllPossibleCodes(gameVariant);
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
            population.add(new Specimen(possibleCodes.get(random.nextInt(possibleCodes.size()))));
        }
        return population;
    }

    private List<Specimen> generatePopulationWithoutDuplicates(int size) {
        return possibleCodes.stream()
                .limit(size)
                .map(Specimen::new)
                .toList();
    }

}
