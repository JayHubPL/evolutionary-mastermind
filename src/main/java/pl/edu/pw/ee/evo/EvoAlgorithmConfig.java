package pl.edu.pw.ee.evo;

import lombok.Builder;
import lombok.Value;
import pl.edu.pw.ee.evo.operators.*;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;

@Value
@Builder
public class EvoAlgorithmConfig {

    int populationSize;
    Code initialGuess;
    boolean initialPopulationDuplicatesAllowed;
    GameVariant gameVariant;

    PopulationGenerator populationGenerator;
    Evaluator evaluator;
    Selector selector;
    Crosser crosser;
    Mutator mutator;
    PairMatcher pairMatcher;

}
