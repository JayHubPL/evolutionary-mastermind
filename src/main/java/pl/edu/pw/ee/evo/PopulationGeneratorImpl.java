package pl.edu.pw.ee.evo;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.operators.PopulationGenerator;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class PopulationGeneratorImpl implements PopulationGenerator {

    private final GameVariant gameVariant;

    @Override
    public List<Specimen> generatePopulation(int size) { // FIXME
        var stream = Stream.generate(() -> new Code(gameVariant));
        if (!gameVariant.getDuplicateColorsAllowed()) {
            stream = stream.distinct();
        }
        return stream.limit(size).map(code -> new Specimen(gameVariant, code)).toList();
    }

}
