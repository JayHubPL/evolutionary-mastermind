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

    public static GameVariant classic(boolean duplicateColorsAllowed) {
        return GameVariant.builder()
                .numberOfColors(6)
                .codeLength(4)
                .guessesLimit(12)
                .duplicateColorsAllowed(duplicateColorsAllowed)
                .build();
    }

    public static GameVariant deluxe(boolean duplicateColorsAllowed) {
        return GameVariant.builder()
                .numberOfColors(8)
                .codeLength(5)
                .guessesLimit(12)
                .duplicateColorsAllowed(duplicateColorsAllowed)
                .build();
    }

    public GameVariant withDuplicateColorsAllowed(boolean duplicateColorsAllowed) {
        return GameVariant.builder()
                .numberOfColors(numberOfColors)
                .codeLength(codeLength)
                .guessesLimit(guessesLimit)
                .duplicateColorsAllowed(duplicateColorsAllowed)
                .build();
    }

}
