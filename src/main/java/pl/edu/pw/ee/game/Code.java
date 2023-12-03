package pl.edu.pw.ee.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Code implements Iterable<Color> {

    @EqualsAndHashCode.Exclude
    private final GameVariant gameVariant;
    @Getter
    protected final List<Color> codeSequence;

    public Code(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
        this.codeSequence = RandomUtils.randomColors(length(), numberOfColors(), gameVariant.getDuplicateColorsAllowed());
    }

    public Code(Code other) {
        gameVariant = other.gameVariant;
        codeSequence = other.codeSequence.stream().map(color -> Color.of(color.getIndex())).toList();
    }

    private int length() {
        return gameVariant.getCodeLength();
    }

    private int numberOfColors() {
        return gameVariant.getNumberOfColors();
    }

    public Score compareTo(Code other) {
        int blackPins = 0;
        int whitePins = 0;
        boolean[] matched = new boolean[length()];
        Arrays.fill(matched, false);
        int[] colorCounts = new int[numberOfColors()];
        Arrays.fill(colorCounts, 0);
        for (var color : codeSequence) {
            colorCounts[color.getIndex()]++;
        }
        for (int i = 0; i < length(); i++) {
            if (codeSequence.get(i).equals(other.codeSequence.get(i))) {
                matched[i] = true;
                colorCounts[codeSequence.get(i).getIndex()]--;
                blackPins++;
            }
        }
        for (int i = 0; i < length(); i++) {
            if (!matched[i] && colorCounts[other.codeSequence.get(i).getIndex()] > 0) {
                colorCounts[other.codeSequence.get(i).getIndex()]--;
                whitePins++;
            }
        }
        return new Score(blackPins, whitePins);
    }

    @Override
    public String toString() {
        return codeSequence.toString();
    }

    @Override
    public Iterator<Color> iterator() {
        return codeSequence.iterator();
    }

}
