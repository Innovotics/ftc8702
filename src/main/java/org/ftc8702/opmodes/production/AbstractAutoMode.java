package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.components.ImuGyroSensor;
import org.ftc8702.components.color.ColorSensorComponent;
import org.ftc8702.components.motors.FourWheelMotors;
import org.ftc8702.components.motors.MecanumWheelMotors;
import org.ftc8702.opmodes.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.ColorValue;
import org.ftc8702.utils.RobotAutonomousUtils;

import org.ftcbootstrap.ActiveOpMode;


import java.util.Locale;


abstract class AbstractAutoMode extends LinearOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        DROP_DOWN,
        SELF_ADJUST,
        KNOCK_BLOCK,
        GO_BACK_TOLANDER,
        GYRO_SENSOR_ADJUSTER,
        ULTRASONIC_ADJUSTER,
        GYRO_SENSOR_TURNER,
        ULTRASONIC_SENSOR_TO_DEPOT,
        COLOR_SENSOR_STOP,
        DROP_MARKER,
        MOVE_TO_PARK,
        GYRO_SENSOR_TO_STOP,
        DONE
    }

    //Declare the MotorToEncoder
    private Team8702ProdAuto robot = new Team8702ProdAuto();


    //Set ColorValue to zilch
    ColorValue panelColor = ColorValue.ZILCH;


    abstract ColorValue getPanelColor();

    abstract boolean park() throws InterruptedException;

    Orientation angles;
    Acceleration gravity;

    protected void onInit() {

        robot.init(hardwareMap);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        onInit();
    }

}


