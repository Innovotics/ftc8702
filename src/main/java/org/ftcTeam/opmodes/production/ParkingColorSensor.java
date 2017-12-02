package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftcTeam.utils.ColorValue;
import org.ftcbootstrap.components.ColorSensorComponent;

/**
 * Created by dkim on 11/24/17.
 */
@Autonomous(name = "Team8702Teleop", group = "production")
@Disabled
public class ParkingColorSensor {

    //Declare Color Sensor
    private ColorSensor parkingColorSensor;

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
