package org.ftcTeam.opmodes.test;

/**
 * Created by tylerkim on 11/5/17.
 */

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftcTeam.configurations.production.Team8702ProdAuto;
import ftcbootstrap.components.ColorSensorComponent;
import org.ftcTeam.utils.ColorValue;
import ftcbootstrap.ActiveOpMode;

public class AdaFruitColorSensor {

    //Declare Robot
    private Team8702ProdAuto robot;

    public ColorSensor elmoColorSensor;

    //Declare Color Sensor
    public ColorSensorComponent colorSensorComponent;

    public AdaFruitColorSensor(ActiveOpMode opmode, Team8702ProdAuto robot) {
        this.robot = robot;
        colorSensorComponent = new ColorSensorComponent(opmode, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);
    }

    public ColorValue getColor() {
        ColorValue resultColor = ColorValue.ZILCH;

        //Determine which is color to call
        if (robot.elmoColorSensor.red() > robot.elmoColorSensor.blue()
                && robot.elmoColorSensor.red() > robot.elmoColorSensor.green()) {
            resultColor = ColorValue.RED;
        }
        else if (robot.elmoColorSensor.blue() > robot.elmoColorSensor.red()
                && robot.elmoColorSensor.green() > robot.elmoColorSensor.red()) {
            resultColor = ColorValue.BLUE;
        }
        return resultColor;
    }
}
