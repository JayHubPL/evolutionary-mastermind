package pl.edu.pw.ee.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Code implements Iterable<Color> {

    @EqualsAndHashCode.Exclude
    private final GameVariant gameVariant;
    @Getter
    private final List<Color> codeSequence;

    public Code(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
        this.codeSequence = generateRandomCodeSequence();
    }

    public Code(Code other) {
        this(other.gameVariant, other.codeSequence);
    }

    private ArrayList<Color> generateRandomCodeSequence() {
        var codeSequence = new ArrayList<Color>(length());
        while (codeSequence.size() < length()) {
            Color color = RandomUtils.randomColor(numberOfColors());
            if (!canRepeatColors() && codeSequence.contains(color)) {
                continue;
            }
            codeSequence.add(color);
        }
        return codeSequence;
    }

    private int length() {
        return gameVariant.getCodeLength();
    }

    private boolean canRepeatColors() {
        return gameVariant.getDuplicateColorsAllowed();
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
