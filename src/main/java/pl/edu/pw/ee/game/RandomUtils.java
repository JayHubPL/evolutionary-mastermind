package pl.edu.pw.ee.game;

import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Value
public class RandomUtils {

    static Random random = new Random();

    public static List<Color> randomColors(int size, int colors, boolean duplicatesAllowed) {
        if (duplicatesAllowed) {
            return random.ints(size, 0, colors).mapToObj(Color::of).collect(Collectors.toList());
        }
        var availableColors = IntStream.range(0, colors).mapToObj(Color::of).collect(Collectors.toList());
        Collections.shuffle(availableColors);
        return availableColors.subList(0, size);
    }

}
