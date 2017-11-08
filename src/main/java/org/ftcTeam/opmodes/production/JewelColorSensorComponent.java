package org.ftcTeam.opmodes.production;

/**
 * Created by tylerkim on 11/5/17.
 */
import android.graphics.Color;

import org.ftcTeam.configurations.Team8702Prod;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcTeam.utils.ColorValue;
import org.ftcbootstrap.ActiveOpMode;


public class JewelColorSensorComponent {

    //Declare Robot
    private Team8702Prod robot;

    //Declare Color Sensor
    public ColorSensorComponent colorSensorComponent;

    public JewelColorSensorComponent(ActiveOpMode opmode, Team8702Prod robot) {
        this.robot = robot;
        colorSensorComponent = new ColorSensorComponent(opmode, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);

    }

    // Color Values
    // Refactor this
    public ColorValue getColor() {
        int Red = colorSensorComponent.getR();
        int Blue = colorSensorComponent.getB();
        int Green = colorSensorComponent.getG();

        //Boolean Values
        boolean redBoolean = colorSensorComponent.isRed(Red, Blue, Green);
        boolean blueBoolean = colorSensorComponent.isBlue(Red, Blue, Green);

        //Determine which is color to call
        if (robot.elmoColorSensor.red() > robot.elmoColorSensor.blue()
                && robot.elmoColorSensor.red() > robot.elmoColorSensor.green()) {
            redBoolean = true;
        }

        if (robot.elmoColorSensor.blue() > robot.elmoColorSensor.red()
                && robot.elmoColorSensor.green() > robot.elmoColorSensor.red()) {
            blueBoolean = true;
        }

        if (redBoolean) {
            return ColorValue.RED;
        } else if (blueBoolean) {
            return ColorValue.BLUE;
        }
        return ColorValue.ZILCH;

    }
}
