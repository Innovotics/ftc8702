package org.ftcTeam.opmodes.test;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.ftcTeam.configurations.test.Team8702AdafruitSensor;
import org.ftcTeam.utils.ColorValue;
import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.ColorSensorComponent;


/**
 * Created by tylerkim on 8/25/17.
 */


@Autonomous(name = "Test: Adafruit", group = "Test")
@Disabled
public class TazeringAdafruitTest extends ActiveOpMode {

    //Declare the MotorToEncoder
    private Team8702AdafruitSensor robot;

    //Declare sensors
    public ColorSensorComponent colorSensorComponent;


    //colors
    String blue = "blue";
    String red = "red";
    String green = "green";


    @Override
    protected void onInit() {

        robot = Team8702AdafruitSensor.newConfig(hardwareMap, getTelemetryUtil());

        //Color Sensor
        colorSensorComponent = new ColorSensorComponent(this, robot.elmoSensor, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);
        colorSensorComponent.enableLed(false);

        //Telemetry
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

    }

    @Override
    protected void activeLoop() throws InterruptedException {

        //getTelemetryUtil().addData("step: " + step, " Current");
        getTelemetryUtil().addData("Color: ", getColor().name());
        getTelemetryUtil().sendTelemetry();
        getTelemetryUtil().addData("red", Integer.toString(robot.elmoSensor.red()));
        getTelemetryUtil().addData("blue", Integer.toString(robot.elmoSensor.blue()));
        getTelemetryUtil().addData("green", Integer.toString(robot.elmoSensor.green()));
    }

    // Color Values
    // TODO - Refactor this
    public ColorValue getColor() {
        int Red = colorSensorComponent.getR();
        int Blue = colorSensorComponent.getB();
        int Green = colorSensorComponent.getG();

        //Boolean Values
        boolean redBoolean = colorSensorComponent.isRed(Red, Blue, Green);
        boolean blueBoolean = colorSensorComponent.isBlue(Red, Blue, Green);
        boolean greenBoolean = colorSensorComponent.isGreen(Red, Blue, Green);

        //Determine which is color to call

        if (robot.elmoSensor.red() > robot.elmoSensor.blue() && robot.elmoSensor.red() > robot.elmoSensor.green()) {
            redBoolean = true;
        }

        if (robot.elmoSensor.blue() > robot.elmoSensor.red() && robot.elmoSensor.blue() > robot.elmoSensor.red()) {
            blueBoolean = true;
        }

        if (robot.elmoSensor.green() > robot.elmoSensor.red() && robot.elmoSensor.green() > robot.elmoSensor.blue()) {
            greenBoolean = true;
        }

        if (redBoolean) {
            return ColorValue.RED;
        } else if (blueBoolean) {
            return ColorValue.BLUE;
        } else if (greenBoolean) {
            return ColorValue.GREEN;
        }
        return ColorValue.ZILCH;

    }

}
