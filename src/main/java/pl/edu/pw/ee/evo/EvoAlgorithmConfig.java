package pl.edu.pw.ee.evo;

import lombok.Value;
import pl.edu.pw.ee.evo.operators.*;
import pl.edu.pw.ee.game.GameVariant;

@Value
public class EvoAlgorithmConfig {

    int populationSize;
    GameVariant gameVariant;

    PopulationGenerator populationGenerator;
    Evaluator evaluator;
    Selector selector;
    Crosser crosser;
    Mutator mutator;
    PairMatcher pairMatcher;

}
