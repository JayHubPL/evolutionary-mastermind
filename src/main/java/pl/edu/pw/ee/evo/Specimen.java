package pl.edu.pw.ee.evo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;

@ToString(callSuper = true)
public class Specimen extends Code {

    @Getter
    @Setter
    private int fitness = 0;

    public Specimen(GameVariant gameVariant, Code code) {
        super(gameVariant, code.getCodeSequence());
    }

}
