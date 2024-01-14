package pl.edu.pw.ee.evo;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.ee.game.Code;

@Setter
@Getter
public class Specimen extends Code {

    private double fitness = 0;

    public Specimen(Code code) {
        super(code);
    }

    public Specimen(Specimen other) {
        super(other);
        fitness = other.getFitness();
    }

    @Override
    public String toString() {
        return String.format("%s, fitness=%.3f", codeSequence, fitness);
    }
}
