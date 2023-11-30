package pl.edu.pw.ee.simulation;

import lombok.Builder;
import lombok.Value;
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
    double mutationChance;

    public Optional<Code> getInitialGuess() {
        return Optional.ofNullable(initialGuess);
    }

    public Optional<Code> getSecretCode() {
        return Optional.ofNullable(secretCode);
    }
}