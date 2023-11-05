package pl.edu.pw.ee.game;

import lombok.Value;

import java.util.Random;

@Value
public class RandomUtils {

    static Random random = new Random();

    public static Color randomColor(int colors) {
        return new Color(random.nextInt(colors));
    }

}
