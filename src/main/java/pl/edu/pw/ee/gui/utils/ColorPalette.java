package pl.edu.pw.ee.gui.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public enum ColorPalette {

    LILAC(new Color(180, 139, 232), false),
    BLUE(new Color(21, 101, 192), true),
    MARINE(new Color(0, 150, 136), false),
    GREEN(new Color(139, 195, 74), false),
    YELLOW(new Color(255, 214, 94), false),
    ORANGE(new Color(255, 152, 0), false),
    RED(new Color(244, 67, 54), false),
    PURPLE(new Color(140, 15, 69), true),
    LIGHT_GRAY(new Color(96, 98, 99), false),
    DARK_GRAY(new Color(78, 80, 82), true),
    SILVER(new Color(187, 187, 187), false);

    @Getter
    private final Color color;
    private final boolean lightFontColor;

    public static Color fromIndex(int idx) {
        return values()[idx].color;
    }

    public static Color getFontColor(int idx) {
        return values()[idx].lightFontColor ? SILVER.color : DARK_GRAY.color;
    }

}
