package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.components.ImuGyroSensor;
import org.ftc8702.components.color.ColorSensorComponent;
import org.ftc8702.components.motors.FourWheelMotors;
import org.ftc8702.components.motors.MecanumWheelMotors;
import org.ftc8702.utils.ColorValue;
import org.ftc8702.utils.RobotAutonomousUtils;
import org.ftcTeam.configurations.production.Team8702ProdAuto;

import org.ftcbootstrap.ActiveOpMode;


import java.util.Locale;


abstract class AbstractAutoMode extends ActiveOpMode {

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

    //Setting Target Reached value.
    //If it is set to true then State moves to next step
    //Starting of each step, it will set to false so the the can run until
    // robot set to true
    boolean targetReached = false;

    //States
    private State currentState;

    //Declare the MotorToEncoder
    private Team8702ProdAuto robot;

    // CHANGE
    private FourWheelMotors fourWheelMotors;
    //Wheel Controller

    //Set ColorValue to zilch
    ColorValue panelColor = ColorValue.ZILCH;

    //Declared colorSensorComponent
    public ColorSensorComponent colorSensorComponent;

    //Set abstract ColorValue
    abstract ColorValue getPanelColor();

    abstract boolean park() throws InterruptedException;

    Orientation angles;
    Acceleration gravity;

    double initialAngle;
    double currentAngle;
    double targetAngle;

    @Override
    protected void onInit() {

        robot = Team8702ProdAuto.newConfig(hardwareMap, getTelemetryUtil());
        fourWheelMotors = new MecanumWheelMotors(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
        //Set state to Init
        currentState = State.INIT;

        //Color Sensor
        colorSensorComponent = new ColorSensorComponent(this, robot.elmoColorSensor, ColorSensorComponent.ColorSensorDevice.MODERN_ROBOTICS_I2C);


        robot.imu.initialize(ImuGyroSensor.getParameters());
        angles =  robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        initialAngle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        getTelemetryUtil().addData("Heading Angle", ImuGyroSensor.formatAngle(angles.angleUnit, angles.firstAngle) );
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        // Read color of panel (Red or Blue)
        panelColor = getPanelColor();


        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {

        switch (currentState) {
            case INIT: //Set everything
                logStage();
                //set targetReached to true
                startTheRobot();

                //test if targetReached is true
                if (targetReached) {
                    targetReached = false;
                }
                break;
            case DONE: // When all operations are complete
                logStage();


                //CHANGE
                RobotAutonomousUtils.moveBackToPark(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);
                RobotAutonomousUtils.pauseMotor(robot.motorFR, robot.motorFL, robot.motorBR, robot.motorBL);


                //set telemetry
                //getTelemetryUtil().addData("VuMark", cryptoBoxLocation);
                getTelemetryUtil().sendTelemetry();
                setOperationsCompleted();
                break;
        }

    }

    private void startTheRobot() {
        // TODO logic later
        targetReached = true;
    }

    public Team8702ProdAuto getRobot() {
        return robot;
    }

    public State getCurrentState() {
        return currentState;
    }

    //Determines Color Value of Elmo Color Sensor
    public ColorValue getElmoColor() {

        ColorValue resultColor = ColorValue.ZILCH;

        //CHANGE
        //Determine which is color to call
        if (robot.elmoColorSensor.red() > robot.elmoColorSensor.blue()
                && robot.elmoColorSensor.red() > robot.elmoColorSensor.green()) {
            resultColor = ColorValue.RED;
        } else if (robot.elmoColorSensor.blue() > robot.elmoColorSensor.red()
                && robot.elmoColorSensor.green() > robot.elmoColorSensor.red()) {
            resultColor = ColorValue.BLUE;
        }

        return resultColor;
    }


    //telemetry for logs
    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
    }

    private void adjustRobot() {

    }

    // Slide until detect the right bar
    // Adjust left to fit into to slide


    //CHANGE
    private boolean slideToDetect() throws InterruptedException {
        telemetry.addData("raw ultrasonic", robot.rangeSensorL.rawUltrasonic());
        int distance = robot.rangeSensorL.rawUltrasonic();

        return false;
    }


    private double feedbackAngle() {
        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        return AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
    }



}


