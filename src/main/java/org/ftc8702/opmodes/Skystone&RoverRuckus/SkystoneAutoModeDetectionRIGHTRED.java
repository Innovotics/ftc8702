package org.ftc8702.opmodes.production;

import android.graphics.Color;
import android.graphics.Point;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.ftc8702.configurations.production.SkystoneAutonousConfig;
import org.ftc8702.utils.ColorValue;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.utils.StonePosition;
import org.ftc8702.utils.SkyStoneProperties;


import java.util.Locale;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.*;
import static org.ftc8702.utils.ColorUtil.getColor;

@Autonomous(name = "RIGHT RED Auto Detect", group = "Ops")
public class SkystoneAutoModeDetectionRIGHTRED extends ActiveOpMode {

    Orientation angle;
    StonePosition currentStonePosition;
    double currentYawAngle;
    double currentPitchAngle;
    double currentRollAngle;
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    private SkystoneAutonousConfig robot = new SkystoneAutonousConfig();

    @Override
    protected void onInit() {

        robot.init(super.hardwareMap, getTelemetryUtil());
        robot.driveTrain.setTelemetry(telemetry);
        //webCamDetector.initialize(hardwareMap, telemetry);
        currentState = POSITION_THE_ROBOT;

        initializeIMU();
        telemetry.update();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();

        switch (currentState) {
            case DETECT_SKYSTONE:
                logStage();
                readAngles();
                currentYawAngle = angle.thirdAngle;

                sleep(500);

                int location = skystoneLocation(robot.rightColorSensor, robot.leftColorSensor);

                displayColors(robot.rightColorSensor, robot.leftColorSensor);

                if (location == 1) {

                    currentStonePosition = StonePosition.LEFT;


                } else if (location == 2) {

                    currentStonePosition = StonePosition.CENTER;

                } else {

                    currentStonePosition = StonePosition.RIGHT;

                }

                telemetry.addData("Current Position", currentStonePosition);
                telemetry.update();

                currentState = GRAB_SKY_STONE;

                break;

            case POSITION_THE_ROBOT:
                logStage();
                //Position the Robot
                robot.driveTrain.goBackwardWithUltrasonic(.2f, .3, robot.distanceSensor, 10.0f);

                currentState = DETECT_SKYSTONE;

                break;

            case GRAB_SKY_STONE:
                logStage();
                //robot.driveTrain.goBackward(.6f);
                sleep(500);

                if (currentStonePosition == StonePosition.LEFT) {

                    leftSkystone(robot);

                } else if (currentStonePosition == StonePosition.RIGHT) {

                    rightSkystone(robot);

                } else if (currentStonePosition == StonePosition.CENTER) {

                    centerSkystone(robot);
                }

                robot.driveTrain.goForward(.2f, .3, 400, 50);
                sleep(500);
                robot.driveTrain.rotateRightWithGyro(.2f, 90);
                sleep(500);

                currentState = MOVE_STONE_TO_BUILDER_ZONE;
                break;


            case TEST_CASE:

                currentState = DONE;
                break;

            case MOVE_STONE_TO_BUILDER_ZONE: // When all operations are complete
                logStage();
                robot.driveTrain.goBackwardWithSensor(.3f, .3, robot.colorSensor);
                sleep(500);
                robot.jaja.JaJaUp();
                sleep(100);
                robot.driveTrain.goBackward(.5f, .5, 500, 100);

                //Example of proper strafing calibrations
                // do what you need to do after initialized
                getTelemetryUtil().sendTelemetry();

                displayColors(robot.rightColorSensor, robot.leftColorSensor);

                currentState = PARK;
                break;

            case MOVE_TO_SECOND_SKYSTONE:
                logStage();

                robot.driveTrain.goBackwardWithUltrasonic(.2f, .3, robot.distanceSensor, 10.0f);
                sleep(1000);

                robot.driveTrain.rotateRightWithGyro(.1f, 90);
                sleep(500);

                currentState = DONE;
                break;

            case HUG_STONE2:
                logStage();
                robot.jaja.foundationGrabberLeft.close();
                sleep(1000);
                currentState = MOVE_STONE_TO_BUILDER_ZONE2;
                break;

            case MOVE_STONE_TO_BUILDER_ZONE2:
                logStage();
                // robot.driveTrain.strafeRight(1);
                sleep(1000);
                // robot.driveTrain.goForward(1);
                sleep(4000);
                robot.driveTrain.stop();
                currentState = PARK;
                break;

            case PARK:
                logStage();
                // robot.driveTrain.strafeRight(.3f);
                robot.driveTrain.goBackwardWithSensor(-.2f, .1, robot.colorSensor);
                robot.jaja.JaJaRightDown();
                robot.jaja.JaJaLeftDown();
                currentState = DONE;
                break;

            case DONE:
                logStage();
                telemetry.update();
                robot.driveTrain.stop();
                //setOperationsCompleted();
                break;
        }
        getTelemetryUtil().sendTelemetry();
        telemetry.update();

    }

    //Log the Stage
    public void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

    public Orientation getAngles() {

        return angle;
    }

    public void readAngles() {
        angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        currentYawAngle = angle.firstAngle;
        currentPitchAngle = angle.thirdAngle;
        currentRollAngle = angle.secondAngle;
    }


    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    public int skystoneLocation(ColorSensor rightColorSensor, ColorSensor leftColorSensor) {

        ColorValue rightColorValue = getColorWithYellow(rightColorSensor);
        ColorValue leftColorValue = getColorWithYellow(leftColorSensor);

        if (leftColorValue != ColorValue.YELLOW) {
            return 2;

        } else if (rightColorValue != ColorValue.YELLOW) {

            return 1;

        } else {
            return 3;
        }


    }

    public ColorValue getColorWithYellow(ColorSensor colorSensor) {

        //Determine which is color to call
        if (colorSensor.red() + colorSensor.green() > 3 * colorSensor.blue()) {
            return ColorValue.YELLOW;

        } else {
            return ColorValue.ZILCH;
        }
    }


    public void initializeIMU() {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        robot.imu.initialize(parameters);
        readAngles();

    }

    public void displayColors(ColorSensor rightColorSensor, ColorSensor leftColorSensor) {
        getTelemetryUtil().addData("red", Integer.toString(robot.colorSensor.red()));
        getTelemetryUtil().addData("blue", Integer.toString(robot.colorSensor.blue()));
        getTelemetryUtil().addData("green", Integer.toString(robot.colorSensor.green()));
        getTelemetryUtil().addData("red-blue-diff", Integer.toString(Math.abs(robot.colorSensor.red() - robot.colorSensor.blue())));
        telemetry.update();
    }

    public void leftSkystone(SkystoneAutonousConfig robot) {
        robot.jaja.JaJaRightDown();
        ;
        sleep(1000);
        robot.driveTrain.strafeLeft(.3f, .3, 500, 50);
        telemetry.addData("Left", "");
    }

    public void centerSkystone(SkystoneAutonousConfig robot) {
        robot.driveTrain.strafeLeft(.3f, .3, 700, 50);
        robot.jaja.JaJaRightDown();
        robot.driveTrain.stop();
        sleep(1000);

        robot.driveTrain.strafeLeft(.3f, .3, 400, 50);
        telemetry.addData("Center", "");

    }

    public void rightSkystone(SkystoneAutonousConfig robot) {
        robot.driveTrain.strafeLeft(.3f, .3, 300, 50);
        robot.jaja.JaJaLeftDown();
        robot.driveTrain.stop();
        sleep(1000);

        robot.driveTrain.strafeLeft(.3f, .3, 400, 50);

    }
}