package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.ftc8702.configurations.production.SkystoneAutoConfig;
import org.ftc8702.opmodes.Sensors.ObjectDetectionAutoModeWebcam;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.utils.StonePosition;


import java.util.Locale;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.GRAB_SKY_STONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.HUG_STONE2;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_STONE_TO_BUILDER_ZONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_STONE_TO_BUILDER_ZONE2;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_TO_SECOND_SKYSTONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.PARK;

@Autonomous(name = "RIGHT RED Auto Detect", group = "Ops")
public class SkystoneAutoModeDetectionRIGHTRED extends ActiveOpMode {

    public ObjectDetectionAutoModeWebcam webCamDetector = new ObjectDetectionAutoModeWebcam();
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    private int detectCount = 0;
    Orientation angle;
    double currentYawAngle;
    double currentPitchAngle;
    double currentRollAngle;
    StonePosition currentStonePosition;

    private SkystoneAutoConfig robot = new SkystoneAutoConfig();

    @Override
    protected void onInit() {

        robot.init(super.hardwareMap, getTelemetryUtil());
        webCamDetector.initialize(hardwareMap, telemetry);
        currentState = SkystoneAutoModeState.DETECT_SKYSTONE;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        robot.imu = hardwareMap.get(BNO055IMU.class, "imu");
        robot.imu.initialize(parameters);

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

                ObjectDetectionAutoModeWebcam.RecognitionResult result = webCamDetector.detect();
                detectCount++;

                if (result != null) {


                    //Detect Webcam and Move robot
                    if(result.position == StonePosition.CENTER) {
                        robot.driveTrain.strafeRight(.3f);
                        currentStonePosition = StonePosition.CENTER;
                        telemetry.addData("Position", "Center");
                        telemetry.addData("Angle: ", result.angleToPosition);
                        telemetry.update();

                    }
                    else if (result.position == StonePosition.LEFT) {
                        telemetry.addData("Position", "Left");
                        telemetry.addData("Angle: ", result.angleToPosition);
                        telemetry.update();
                        robot.driveTrain.stop();
                        currentStonePosition = StonePosition.LEFT;
                        //Next Step
                        currentState = GRAB_SKY_STONE;
                    }
                    else if(result.position == StonePosition.RIGHT) {
                        telemetry.addData("Position", "Right");
                        telemetry.addData("Angle: ", result.angleToPosition);
                        telemetry.update();
                        robot.driveTrain.stop();
                        currentStonePosition = StonePosition.RIGHT;
                        //Next Step
                        currentState = GRAB_SKY_STONE;
                    }
                }
                break;
            case GRAB_SKY_STONE:
                sleep(1000);
                robot.driveTrain.goBackward(.6f);
                sleep(1800);
                if(currentStonePosition == StonePosition.LEFT) {
                    robot.jaja.foundationGrabberLeft.setPosition(0);
                }
                else if(currentStonePosition == StonePosition.LEFT) {
                    robot.jaja.foundationGrabberRight.setPosition(1.0);
                }
                robot.driveTrain.stop();
                sleep(1000);

                currentState = MOVE_STONE_TO_BUILDER_ZONE;
                break;
//            case HUG_STONE:
//                logStage();
//
//                robot.hugger.HuggerTopDown(1); //we need to decide whether to use the top hugger or bottom one
//                sleep(1000);
//                currentState = MOVE_STONE_TO_BUIDER_ZONE;
//                break;


            case MOVE_STONE_TO_BUILDER_ZONE: // When all operations are complete
                logStage();
                robot.driveTrain.goForward(1);//Pulls the skystone out a little bit
                sleep(500);
                robot.driveTrain.strafeLeft(0.7f);
                sleep(2500);
                robot.driveTrain.stop();
                robot.jaja.JaJaUp();
                currentState = DONE;
                break;

            case MOVE_TO_SECOND_SKYSTONE:
                logStage();
                robot.driveTrain.goForwardFullSpeedInFeet(0.3, false);
                robot.driveTrain.strafeRight(1);
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
                robot.driveTrain.strafeRight(1);
                sleep(1000);
                robot.driveTrain.goForward(1);
                sleep(4000);
                robot.driveTrain.stop();
                currentState = PARK;
                break;

            case MOVE_FOUNDATION:
                if(accomplishedTask == false) {
                    robot.jaja.foundationGrabberLeft.setPosition(0);
                    sleep(5000);
                    robot.jaja.foundationGrabberLeft.setPosition(0);
                    accomplishedTask = true;

                } else if(accomplishedTask == true) {
                    currentState = PARK;
                }


            case PARK:
                logStage();
                robot.driveTrain.strafeRight(.3f);
                  ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    currentState = DONE;
                }
                else if(currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN){
                    robot.driveTrain.strafeRight(.3f);
                }
                break;

            case DONE:
                logStage();
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

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}

