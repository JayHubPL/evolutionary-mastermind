package pl.edu.pw.ee.game;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GameVariant {

    Integer numberOfColors;
    Integer codeLength;
    Integer guessesLimit;
    Boolean duplicateColorsAllowed;

    public static GameVariant classic() {
        return GameVariant.builder()
                .numberOfColors(6)
                .codeLength(4)
                .guessesLimit(12)
                .duplicateColorsAllowed(true)
                .build();
    }

    public static GameVariant deluxe() {
        return GameVariant.builder()
                .numberOfColors(8)
                .codeLength(5)
                .guessesLimit(12)
                .duplicateColorsAllowed(true)
                .build();
    }

}
