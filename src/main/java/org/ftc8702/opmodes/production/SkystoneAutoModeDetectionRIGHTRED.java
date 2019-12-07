package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.ftc8702.configurations.production.SkystoneAutoConfig;
import org.ftc8702.configurations.production.SkystoneAutonousConfig;
import org.ftc8702.opmodes.Sensors.ObjectDetectionAutoModeWebcam;
import org.ftc8702.utils.ColorUtil;
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

    public ObjectDetectionAutoModeWebcam webCamDetector = new ObjectDetectionAutoModeWebcam();
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    private int detectCount = 0;
    Orientation angle;
    StonePosition currentStonePosition;

    private SkystoneAutonousConfig robot = new SkystoneAutonousConfig();

    double currentYawAngle;
    double currentPitchAngle;
    double currentRollAngle;
    double initialYawAngle;

    @Override
    protected void onInit() {

        robot.init(super.hardwareMap, getTelemetryUtil());
        robot.driveTrain.setTelemetry(telemetry);
        webCamDetector.initialize(hardwareMap, telemetry);
        currentState = DETECT_SKYSTONE;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        robot.imu.initialize(parameters);
        readAngles();

        telemetry.addData("Angle: ", angle);
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

                ObjectDetectionAutoModeWebcam.RecognitionResult result = webCamDetector.detect();
                currentStonePosition = result.position;
                telemetry.addData("Current Angle: ", currentStonePosition);
                telemetry.update();
                sleep(1000);
                currentState = POSITION_THE_ROBOT;

                break;

            case POSITION_THE_ROBOT:
                logStage();
                    //Detect Webcam and Move robot
                if (currentStonePosition == StonePosition.CENTER || currentStonePosition == null) {

                    //    robot.driveTrain.strafeRight(.3f, .9, 500, 500);
                        currentState = GRAB_SKY_STONE;

                } else if (currentStonePosition == StonePosition.LEFT) {
                    robot.driveTrain.goBackward(.8f, .9, 1000, 1000);
                        robot.driveTrain.stop();
                        //Next Step
                        currentState = GRAB_SKY_STONE;

                } else if (currentStonePosition == StonePosition.RIGHT) {
                        robot.driveTrain.goBackward(.8f, .9, 1000, 1000);
                        //Next Step
                        currentState = GRAB_SKY_STONE;
                }
                break;

            case GRAB_SKY_STONE:
                logStage();
                //robot.driveTrain.goBackward(.6f);
                sleep(SkyStoneProperties.SLEEP_ROBOT_POSITION_BF_GRAB);

                if (currentStonePosition == StonePosition.LEFT) {
                    robot.jaja.foundationGrabberLeft.setPosition(0.2);
                    sleep(1000);
                    robot.driveTrain.strafeLeft(.4f, .9, 500, 500);

                } else if (currentStonePosition == StonePosition.RIGHT) {
                    robot.jaja.foundationGrabberRight.setPosition(.8);
                    sleep(1000);
                    robot.driveTrain.strafeRight(.8f, .9, 500, 500);

                } else if (currentStonePosition == StonePosition.CENTER) {
                    robot.driveTrain.strafeLeft(.4f, .9, 1000, 500);
                    robot.driveTrain.goBackward(.8f, .9, 1000, 1000);
                    sleep(1000);
                    robot.jaja.foundationGrabberRight.setPosition(.8);
                    sleep(1000);
                    robot.driveTrain.strafeLeft(.4f, .9, 500, 500);

                }
                robot.driveTrain.goForward(.8f, .9, 500, 500);
                robot.driveTrain.stop();
                sleep(1000);
                sleep(SkyStoneProperties.SLEEP_ROBOT_POSITION_AF_GRAB);

                currentState = MOVE_STONE_TO_BUILDER_ZONE;
                break;

            case MOVE_STONE_TO_BUILDER_ZONE: // When all operations are complete
                logStage();
                robot.driveTrain.strafeLeftWithSensor(.8f, .9, robot.colorSensor);  //Example of proper strafing calibrations
                // do what you need to do after initialized
                getTelemetryUtil().sendTelemetry();
                getTelemetryUtil().addData("red", Integer.toString(robot.colorSensor.red()));
                getTelemetryUtil().addData("blue", Integer.toString(robot.colorSensor.blue()));
                getTelemetryUtil().addData("green", Integer.toString(robot.colorSensor.green()));
                getTelemetryUtil().addData("red-blue-diff", Integer.toString(Math.abs(robot.colorSensor.red() - robot.colorSensor.blue())));

//                sleep(SkyStoneProperties.SLEEP_ROBOT_POSITION_AF_GRAB);
//                ColorValue stoppingColor = getColor(robot.colorSensor);
//                while(stoppingColor != ColorValue.BLUE && stoppingColor != ColorValue.RED) {
//                    robot.driveTrain.strafeLeft(0.8f, .9, 1000, 1000);
//                }
//                sleep(SkyStoneProperties.SLEEP_AFTER_DETECT_COLOR);
//                robot.driveTrain.stop();
//
//                robot.jaja.JaJaUp();
                currentState = DONE;
                break;

            case MOVE_TO_SECOND_SKYSTONE:
                logStage();

                telemetry.addData("Initial Angle: ", +initialYawAngle);
                telemetry.addData("Current Yaw Angle: ", +currentYawAngle);

                robot.driveTrain.goForwardFullSpeedInFeet(0.3, false);
                // robot.driveTrain.strafeRight(1);
                sleep(4000);
                robot.driveTrain.stop();
                currentState = HUG_STONE2;
                break;

            case HUG_STONE2:
                logStage();
                robot.jaja.foundationGrabberLeft.setPosition(1);
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

            case MOVE_FOUNDATION:
                if (accomplishedTask == false) {
                    robot.jaja.foundationGrabberLeft.setPosition(0);
                    sleep(5000);
                    robot.jaja.foundationGrabberLeft.setPosition(0);
                    accomplishedTask = true;

                } else if (accomplishedTask == true) {
                    currentState = PARK;
                }


            case PARK:
                logStage();
                // robot.driveTrain.strafeRight(.3f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if (currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    currentState = DONE;
                } else if (currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN) {
                    // robot.driveTrain.strafeRight(.3f);
                }
                break;

            case DONE:
                logStage();
                telemetry.addData("Right", " Position");
                telemetry.addData("Angle: ", angle);
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
}
