package pl.edu.pw.ee.evo;

import lombok.Builder;
import lombok.Value;
import pl.edu.pw.ee.evo.operators.Crosser;
import pl.edu.pw.ee.evo.operators.Evaluator;
import pl.edu.pw.ee.evo.operators.Mutator;
import pl.edu.pw.ee.evo.operators.PairMatcher;
import pl.edu.pw.ee.evo.operators.Scaler;
import pl.edu.pw.ee.evo.operators.Selector;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;

@Value
@Builder
public class EvoAlgorithmConfig {

    int populationSize;
    Code initialGuess;
    boolean initialPopulationDuplicatesAllowed;
    boolean shouldMutateSurvivors;
    GameVariant gameVariant;

    PopulationGenerator populationGenerator;
    Evaluator evaluator;
    Scaler scaler;
    Selector selector;
    Crosser crosser;
    Mutator mutator;
    PairMatcher pairMatcher;

}
