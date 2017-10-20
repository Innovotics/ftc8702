package org.ftcTeam.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702AdafruitSensor;
import org.ftcTeam.configurations.Team8702Prod;
import org.ftcTeam.utils.ColorValue;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.ColorSensorComponent;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;


/**
 * Created by tylerkim on 8/25/17.
 */


@Autonomous(name = "Test: Adafruit", group = "Test")
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
       // colorSensorComponent.enableLed(false);
      //  colorSensorComponent = new ColorSensorComponent(this, robot.ColorSensor1, ColorSensorComponent.ColorSensorDevice.ADAFRUIT);

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
        getTelemetryUtil().addData("Color: " , getColor().name());
        getTelemetryUtil().sendTelemetry();

    }

    // Color Values
    public ColorValue getColor() {
        int Red = colorSensorComponent.getR();
        int Blue = colorSensorComponent.getB();
        int Green = colorSensorComponent.getG();

        boolean redBoolean = colorSensorComponent.isRed( Red, Blue, Green );

        // test if color is red
        if(redBoolean) {
            return ColorValue.RED;
        }
        boolean blueBoolean = colorSensorComponent.isBlue( Red, Blue, Green );

        //test if color is blue
        if(blueBoolean) {
            return ColorValue.BLUE;
        }
        //zilch color
        return ColorValue.ZILCH;

    }

}
