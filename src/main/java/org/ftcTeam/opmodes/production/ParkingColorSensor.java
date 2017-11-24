package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftcTeam.utils.ColorValue;
import org.ftcbootstrap.components.ColorSensorComponent;

/**
 * Created by dkim on 11/24/17.
 */

public class ParkingColorSensor {

    //Declare Color Sensor
    private ColorSensor parkingColorSensor;
    private ColorSensorComponent colorSensorComponent;

    //Set color Vluae
    public ParkingColorSensor(ColorSensor colorSensor) {

        this.parkingColorSensor = colorSensor;
    }


    public boolean parkingOn(ColorValue baseColor) {

        ColorValue detectingColor = ColorValue.ZILCH;
        //Determines Color
        int Red = parkingColorSensor.red();
        int Blue = parkingColorSensor.blue();
        int Green = parkingColorSensor.green();

        //Boolean Values
        boolean redBoolean = colorSensorComponent.isRed(Red, Blue, Green);
        boolean blueBoolean = colorSensorComponent.isBlue(Red, Blue, Green);

        //Determine which is color to call
        if (parkingColorSensor.red() > parkingColorSensor.blue() && parkingColorSensor.red() > parkingColorSensor.green()) {
            detectingColor = ColorValue.RED;
        }

        if (parkingColorSensor.blue() > parkingColorSensor.red() && parkingColorSensor.green() > parkingColorSensor.red()) {
            detectingColor = ColorValue.BLUE;
        }

        //Detect if base color is congruent to detected color
        if (detectingColor.equals(baseColor)) {
            return true;
        } else {
            return false;
        }
    }
}
