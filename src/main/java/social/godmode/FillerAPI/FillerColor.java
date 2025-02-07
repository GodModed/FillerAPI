package social.godmode.FillerAPI;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.Random;

@AllArgsConstructor
public enum FillerColor {
    RED(new Color(233, 35, 88)),
    GREEN(new Color(176, 230, 107)),
    YELLOW(new Color(250, 234, 37)),
    BLUE(new Color(69, 177, 242)),
    PURPLE(new Color(121, 86, 170)),
    BLACK(new Color(66, 64, 67));

    public final Color color;

    public static FillerColor getRandomColor(Random random) {
        return values()[(int) (random.nextDouble() * values().length)];
    }
}