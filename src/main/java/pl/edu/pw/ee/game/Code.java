package pl.edu.pw.ee.game;

import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Value
public class Code implements Iterable<Color> {

    List<Color> codeSequence;
    Integer numberOfColors;
    Integer length;

    public Code(int length, int colors, boolean canRepeatColors) {
        this.length = length;
        numberOfColors = colors;
        codeSequence = new ArrayList<>(length);
        while (codeSequence.size() < length) {
            Color color = RandomUtils.randomColor(numberOfColors);
            if (!canRepeatColors && codeSequence.contains(color)) {
                continue;
            }
            codeSequence.add(color);
        }
    }

    public Code(GameVariant variant) {
        this(variant.getCodeLength(), variant.getNumberOfColors(), variant.getDuplicateColorsAllowed());
    }

    public Score compareTo(Code other) {
        int blackPins = 0;
        int whitePins = 0;
        boolean[] matched = new boolean[length];
        Arrays.fill(matched, false);
        int[] colorCounts = new int[numberOfColors];
        Arrays.fill(colorCounts, 0);
        for (var color : codeSequence) {
            colorCounts[color.getIndex()]++;
        }
        for (int i = 0; i < length; i++) {
            if (getColorAt(i).equals(other.getColorAt(i))) {
                matched[i] = true;
                colorCounts[getColorAt(i).getIndex()]--;
                blackPins++;
            }
        }
        for (int i = 0; i < length; i++) {
            if (!matched[i] && colorCounts[other.getColorAt(i).getIndex()] > 0) {
                colorCounts[other.getColorAt(i).getIndex()]--;
                whitePins++;
            }
        }
        return new Score(blackPins, whitePins);
    }

    private Color getColorAt(int position) {
        return codeSequence.get(position);
    }

    @Override
    public Iterator<Color> iterator() {
        return codeSequence.iterator();
    }

}
