package pl.edu.pw.ee.evo;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.utils.CodePoolGenerator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PopulationGenerator {

    private static final Random random = new Random();
    private final List<Code> possibleCodes;

    public PopulationGenerator(GameVariant gameVariant) {
        possibleCodes = Collections.synchronizedList(CodePoolGenerator.getAllPossibleCodes(gameVariant));
    }

    public List<Specimen> generatePopulation(int size, boolean duplicatesAllowed) {
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
        var indexes = IntStream.range(0, possibleCodes.size())
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(indexes);
        return indexes.stream()
                .limit(size)
                .map(possibleCodes::get)
                .map(Specimen::new)
                .collect(Collectors.toList());
    }

}
