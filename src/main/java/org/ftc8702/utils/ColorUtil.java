package org.ftc8702.utils;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorUtil {
    // help to fix the red color sense correctly, 20 is to offset the color sensor bias toward red.
    private static int RED_COLOR_OFFSET = 20;

    public static ColorValue getColor(ColorSensor colorSensor) {
        int red = colorSensor.red();
        int blue = colorSensor.blue();
        int green = colorSensor.green();

        // somehow the color sensor is biased toward the red color, so we check for the larger
        // threshold before we call it a color of RED.
        if ( red - blue > RED_COLOR_OFFSET && red - green > RED_COLOR_OFFSET) {
            return ColorValue.RED;
        }

        if (blue > red && blue > green) {
            return ColorValue.BLUE;
        }

        return ColorValue.ZILCH;
    }

    public static boolean isRedOrBlueDetected(ColorValue color) {
        return (color == ColorValue.BLUE || color == ColorValue.RED);
    }

}
