package pl.edu.pw.ee.game;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
public class Color {

    @Getter
    @Setter
    Integer index;

    public static Color of(int index) {
        return new Color(index);
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }

}
