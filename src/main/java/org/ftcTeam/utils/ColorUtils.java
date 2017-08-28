package org.ftcTeam.utils;


import org.ftcbootstrap.components.ColorSensorComponent;

/**
 * Created by tylerkim on 12/21/16.
 */

public class ColorUtils {


    public static ColorValue getColor(ColorSensorComponent colorSensorComponent) {
        int red = colorSensorComponent.getR();
        int blue = colorSensorComponent.getB();
        int green = colorSensorComponent.getG();

        if (blue > red && blue > green) {
            return ColorValue.BLUE;

        } else if (red > blue && red > green) {
            return ColorValue.RED;

        }
        return ColorValue.ZILCH;
    }

}
