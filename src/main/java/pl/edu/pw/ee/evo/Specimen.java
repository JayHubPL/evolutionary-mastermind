package pl.edu.pw.ee.evo;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pw.ee.game.Code;

public class Specimen extends Code {

    @Getter
    @Setter
    private int fitness = 0;

    public Specimen(Code code) {
        super(code);
    }

    @Override
    public String toString() {
        return String.format("%s, fitness=%d", codeSequence, fitness);
    }
}
