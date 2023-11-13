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
