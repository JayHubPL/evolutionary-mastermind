package pl.edu.pw.ee.game;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

@Value
@AllArgsConstructor
public class Score implements Iterable<Color> {

    Integer correctColorAndPlace;
    Integer colorPresentButWrongPlace;

    public int distanceTo(Score other) {
        int minWhite = Math.min(colorPresentButWrongPlace, other.colorPresentButWrongPlace);
        int minBlack = Math.min(correctColorAndPlace, other.correctColorAndPlace);
        int minSum = minWhite + minBlack;
        return Math.max(colorPresentButWrongPlace + correctColorAndPlace, other.colorPresentButWrongPlace + other.correctColorAndPlace) - minSum;
    }

    @Override
    public Iterator<Color> iterator() {
        var blacks = Collections.nCopies(correctColorAndPlace, Color.BLACK);
        var whites = Collections.nCopies(colorPresentButWrongPlace, Color.WHITE);
        var merged = new ArrayList<Color>();
        merged.addAll(blacks);
        merged.addAll(whites);
        return merged.iterator();
    }
}
