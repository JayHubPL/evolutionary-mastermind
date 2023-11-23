package pl.edu.pw.ee.evo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pw.ee.game.Code;

@ToString(callSuper = true)
public class Specimen extends Code {

    @Getter
    @Setter
    private int fitness = 0;

    public Specimen(Code code) {
        super(code);
    }

}
