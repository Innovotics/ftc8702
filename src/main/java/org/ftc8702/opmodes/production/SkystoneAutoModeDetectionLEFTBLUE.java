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

@Autonomous(name = "LEFT BLUE Auto Detect", group = "Ops")
public class SkystoneAutoModeDetectionLEFTBLUE extends ActiveOpMode {

    public ObjectDetectionAutoModeWebcam webCamDetector = new ObjectDetectionAutoModeWebcam();
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    Orientation angle;
    StonePosition currentStonePosition;

    private SkystoneAutonousConfig robot = new SkystoneAutonousConfig();

    double currentYawAngle;
    double currentPitchAngle;
    double currentRollAngle;

    @Override
    protected void onInit() {

        robot.init(super.hardwareMap, getTelemetryUtil());
        robot.driveTrain.setTelemetry(telemetry);
        //webCamDetector.initialize(hardwareMap, telemetry);
        currentState = POSITION_THE_ROBOT;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        robot.imu.initialize(parameters);
        //   readAngles();

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
                //Position the Robot
                robot.driveTrain.goBackwardWithUltrasonic(.2f, .3, robot.distanceSensor, 10.0f);
                currentStonePosition = StonePosition.RIGHT;

                currentState = GRAB_SKY_STONE;

                break;

            case GRAB_SKY_STONE:
                logStage();
                //robot.driveTrain.goBackward(.6f);
                sleep(SkyStoneProperties.SLEEP_ROBOT_POSITION_BF_GRAB);

                if (currentStonePosition == StonePosition.LEFT) {
                    robot.jaja.JaJaRightDown();
                    sleep(1000);
                    robot.driveTrain.strafeRight(.4f, .9, 500, 500);

                } else if (currentStonePosition == StonePosition.RIGHT) {
                    robot.jaja.JaJaLeftDown();
                    sleep(1000);
                    robot.driveTrain.strafeLeft(.3f, .9, 800, 800);

                } else if (currentStonePosition == StonePosition.CENTER) {
                    robot.driveTrain.strafeLeft(.4f, .9, 1000, 500);
                    robot.driveTrain.goBackward(.8f, .9, 1000, 1000);
                    sleep(1000);
                    robot.jaja.JaJaLeftDown();
                    sleep(1000);
                    robot.driveTrain.strafeLeft(.4f, .9, 500, 500);

                }
                robot.driveTrain.goForward(.8f, .9, 500, 100);
                robot.driveTrain.stop();
                sleep(1000);

                currentState = MOVE_STONE_TO_BUILDER_ZONE;
                break;


            case MOVE_STONE_TO_BUILDER_ZONE: // When all operations are complete
                logStage();
                robot.driveTrain.strafeRightWithSensor(.8f, .9, robot.colorSensor);
                sleep(1000);
                robot.driveTrain.strafeRight(.75f, .9, 1000, 100);
                robot.jaja.JaJaUp();


                //Example of proper strafing calibrations
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
                currentState = PARK;
                break;

            case MOVE_TO_SECOND_SKYSTONE:
                logStage();

                robot.driveTrain.goBackward(.4f, .9, 500, 100);
                sleep(500);
                robot.driveTrain.strafeRight(1.0f, .9, 1000, 100);
                sleep(500);

                robot.driveTrain.stop();
                sleep(1000);
                currentState = DONE;
                break;

            case HUG_STONE2:
                logStage();
                robot.jaja.JaJaUp();
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
                    robot.jaja.foundationGrabberLeft.close();
                    sleep(5000);
                    robot.jaja.foundationGrabberLeft.close();
                    accomplishedTask = true;

                } else if (accomplishedTask == true) {
                    currentState = PARK;
                }


            case PARK:
                logStage();
                // robot.driveTrain.strafeRight(.3f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);
                robot.driveTrain.strafeLeftWithSensor(.8f, .9, robot.colorSensor);
                currentState = DONE;

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
