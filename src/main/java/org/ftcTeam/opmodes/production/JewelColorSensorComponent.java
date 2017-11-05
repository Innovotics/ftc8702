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

    //Colors
    String blue = "blue";
    String red = "red";
    String green = "green";


    public JewelColorSensorComponent(ActiveOpMode opmode, Team8702Prod robot) {
        this.robot = robot;
        colorSensorComponent = new ColorSensorComponent(opmode, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);

    }
}
