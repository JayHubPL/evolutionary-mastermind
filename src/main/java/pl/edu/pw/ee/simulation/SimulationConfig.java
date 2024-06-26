package pl.edu.pw.ee.simulation;

import lombok.Builder;
import lombok.Value;
import pl.edu.pw.ee.evo.operators.PairMatcher;
import pl.edu.pw.ee.evo.operators.Scaler;
import pl.edu.pw.ee.evo.operators.Selector;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;

import java.util.Optional;

@Value
@Builder
public class SimulationConfig {

    GameVariant gameVariant;
    int populationSize;
    Code initialGuess;
    Code secretCode;
    boolean initialPopulationDuplicatesAllowed;
    boolean shouldMutateSurvivors;
    double mutationChance;
    double crossingProbability;
    Scaler scaler;
    Selector selector;
    PairMatcher pairMatcher;

    public Optional<Code> getInitialGuess() {
        return Optional.ofNullable(initialGuess);
    }

    public Optional<Code> getSecretCode() {
        return Optional.ofNullable(secretCode);
    }
}
