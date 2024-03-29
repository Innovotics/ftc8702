package org.ftc8702.opmodes.roverruckus_skystone;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;
import org.ftc8702.utils.StonePosition;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.DETECT_SKYSTONE;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.GRAB_SKY_STONE_CENTER;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.GRAB_SKY_STONE_LEFT;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.GRAB_SKY_STONE_RIGHT;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.MOVE_TO_SECOND_SKYSTONE_CENTER;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.MOVE_TO_SECOND_SKYSTONE_LEFT;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.MOVE_TO_SECOND_SKYSTONE_RIGHT;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.PARK;

//@Autonomous(name = "Left Blue Skystone", group = "Ops")
public class SkystoneLeftBlueDetectionTest extends ActiveOpMode {

    Orientation angle;
    StonePosition currentStonePosition;
    double currentYawAngle;
    double currentPitchAngle;
    double currentRollAngle;
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    private Park.SkystoneAutonousConfig robot = new Park.SkystoneAutonousConfig();

    @Override
    protected void onInit() {

        robot.init(super.hardwareMap, getTelemetryUtil());
        robot.driveTrain.setTelemetry(telemetry);
        //webCamDetector.initialize(hardwareMap, telemetry);
        currentState = DETECT_SKYSTONE;

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
                /*while(getColorWithYellow(robot.rightColorSensor) == ColorValue.YELLOW
                        || getColorWithYellow(robot.leftColorSensor) == ColorValue.YELLOW){
                    robot.driveTrain.goBackward(0.3f,0.5,2150,100);
                }
                robot.driveTrain.goBackwardWithoutPID(0.3f);
                sleep(100);
                 */
                robot.driveTrain.goBackwardWithUltrasonic(.4f, .3, robot.distanceSensor, 16);
                //robot.driveTrain.goBackward(0.3f,0.5,2000,100);
                //robot.driveTrain.goBackward(0.7f,1,700,50);
                telemetry.addData("going", " backward");
                robot.driveTrain.stop();
                telemetry.addData("stop", " now");

                //int location = skystoneLocation(robot.rightColorSensor, robot.leftColorSensor);

                //displayColors(robot.rightColorSensor, robot.leftColorSensor);

                if (getColorWithYellow(robot.rightColorSensor) == ColorValue.YELLOW
                  && getColorWithYellow(robot.leftColorSensor) == ColorValue.YELLOW)
                {
                    currentStonePosition = StonePosition.RIGHT;
                    currentState = GRAB_SKY_STONE_RIGHT;
                }
                else if (getColorWithYellow(robot.rightColorSensor) == ColorValue.YELLOW
                  && getColorWithYellow(robot.leftColorSensor) != ColorValue.YELLOW)
                {
                    currentStonePosition = StonePosition.CENTER;
                    currentState = GRAB_SKY_STONE_CENTER;
                }
                else if (getColorWithYellow(robot.rightColorSensor) != ColorValue.YELLOW
                        && getColorWithYellow(robot.leftColorSensor) == ColorValue.YELLOW)
                {
                    currentStonePosition = StonePosition.LEFT;
                    currentState = GRAB_SKY_STONE_LEFT;
                }
                else
                {
                    telemetry.addData("Current Position", "unknown, default to right");
                    currentStonePosition = StonePosition.RIGHT;
                    currentState = GRAB_SKY_STONE_RIGHT;
                }
                /*
                if (getColorWithYellow(robot.rightColorSensor) != ColorValue.YELLOW)
                {
                    currentStonePosition = StonePosition.LEFT;
                    currentState = GRAB_SKY_STONE_LEFT;
                } else if (getColorWithYellow(robot.leftColorSensor) != ColorValue.YELLOW)
                {
                    currentStonePosition = StonePosition.CENTER;
                    currentState = GRAB_SKY_STONE_CENTER;
                } else {
                    currentStonePosition = StonePosition.RIGHT;
                    currentState = GRAB_SKY_STONE_RIGHT;
                }
                 */

                telemetry.addData("Current Position", currentStonePosition);
                telemetry.update();
                robot.driveTrain.stop();
                sleep(1000);
                break;

            case GRAB_SKY_STONE_LEFT:
                logStage();
                robot.jaja.JaJaRightDown();
                sleep(300);
                robot.driveTrain.strafeLeftWithoutPid(0.3f);
                sleep(300);
                robot.driveTrain.goForwardWithoutPID(0.5f);
                sleep(500);
                robot.driveTrain.rotateLeftWithGyro(.3f, 90);
                robot.driveTrain.goBackward(.5f, 1, 2000, 100);
                robot.jaja.JaJaUp();
                sleep(100);
                currentState = MOVE_TO_SECOND_SKYSTONE_LEFT;
                break;

            case MOVE_TO_SECOND_SKYSTONE_LEFT:
                logStage();
                robot.driveTrain.goForward(.5f, 1, 2000, 100);
                robot.driveTrain.stop();
                //readAngles();
                //currentYawAngle = angle.thirdAngle;
                robot.driveTrain.rotateRightWithGyro(.3f, 0);
                robot.driveTrain.strafeLeftWithoutPid(0.5f);
                sleep(900);
                robot.driveTrain.goBackwardWithUltrasonic(.3f, .3, robot.distanceSensor, 10);
                robot.jaja.JaJaRightDown();
                sleep(200);
                robot.driveTrain.goForwardWithoutPID(0.3f);
                sleep(700);
                robot.driveTrain.rotateLeftWithGyro(.3f, 90);
                robot.driveTrain.goBackward(.5f, 1, 2700, 100);
                robot.jaja.JaJaUp();
                sleep(200);
                robot.driveTrain.stop();
                currentState = PARK;
                break;

            case GRAB_SKY_STONE_CENTER:
                logStage();
                robot.driveTrain.strafeRightWithoutPID(0.4f);
                sleep(200);
                robot.jaja.JaJaLeftDown();
                sleep(300);
                robot.driveTrain.strafeRightWithoutPID(0.3f);
                sleep(300);
                robot.driveTrain.goForwardWithoutPID(0.5f);
                sleep(500);
                robot.driveTrain.rotateLeftWithGyro(.3f, 90);
                robot.driveTrain.goBackward(.5f, 1, 1800, 100);
                robot.jaja.JaJaUp();
                sleep(100);
                currentState = MOVE_TO_SECOND_SKYSTONE_CENTER;
                break;

            case MOVE_TO_SECOND_SKYSTONE_CENTER:
                logStage();
                robot.driveTrain.goForward(.5f, 0.5, 2000, 100);
                robot.driveTrain.stop();
                //readAngles();
                //currentYawAngle = angle.thirdAngle;
                robot.driveTrain.rotateRightWithGyro(.3f, 0);
                robot.driveTrain.strafeLeftWithoutPid(0.5f);
                sleep(900);
                robot.driveTrain.goBackwardWithUltrasonic(.3f, .3, robot.distanceSensor, 10);
                robot.jaja.JaJaLeftDown();
                sleep(200);
                robot.driveTrain.goForwardWithoutPID(0.3f);
                sleep(800);
                robot.driveTrain.rotateLeftWithGyro(.3f, 90);
                robot.driveTrain.goBackward(.5f, 0.5, 2700, 100);
                robot.jaja.JaJaUp();
                sleep(200);
                robot.driveTrain.stop();
                currentState = PARK;
                break;

            case GRAB_SKY_STONE_RIGHT:
                logStage();
                robot.driveTrain.goForwardWithoutPID(0.4f);
                sleep(300);
                robot.driveTrain.strafeLeftWithoutPid(0.3f);
                sleep(500);
                robot.driveTrain.goBackwardWithoutPID(0.2f);
                sleep(300);
                robot.jaja.JaJaLeftDown();
                sleep(300);
                robot.driveTrain.strafeLeftWithoutPid(0.3f);
                sleep(300);
                robot.driveTrain.goForwardWithoutPID(0.5f);
                sleep(500);
                robot.driveTrain.rotateLeftWithGyro(.4f, 90);
                robot.driveTrain.goBackward(.5f, 1, 2300, 100);
                robot.jaja.JaJaUp();
                sleep(100);
                currentState = MOVE_TO_SECOND_SKYSTONE_RIGHT;
                break;

            case MOVE_TO_SECOND_SKYSTONE_RIGHT:
                logStage();
                robot.driveTrain.goForward(.5f, 1, 2300, 100);
                robot.driveTrain.stop();
                //readAngles();
                //currentYawAngle = angle.thirdAngle;
                robot.driveTrain.rotateRightWithGyro(.4f, 0);
                robot.driveTrain.strafeLeftWithoutPid(0.5f);
                sleep(1100);
                robot.driveTrain.goBackwardWithUltrasonic(.3f, .3, robot.distanceSensor, 10);
                robot.jaja.JaJaLeftDown();
                sleep(200);
                robot.driveTrain.strafeRightWithoutPID(0.5f);
                sleep(300);
                robot.driveTrain.goForwardWithoutPID(0.3f);
                sleep(1000);
                robot.driveTrain.rotateLeftWithGyro(.4f, 90);
                robot.driveTrain.goBackward(.5f, 1, 2900, 100);
                robot.jaja.JaJaUp();
                sleep(200);
                robot.driveTrain.stop();
                currentState = PARK;
                break;


            case PARK:
                logStage();
                robot.driveTrain.goForwardWithoutPID(.2f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    robot.driveTrain.stop();
                    robot.jaja.JaJaDown();
                    currentState = DONE;
                }
                else if(currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN){
                    robot.driveTrain.goForwardWithoutPID(.2f);
                }
                break;

            case DONE:
                logStage();
                telemetry.update();
                robot.driveTrain.stop();
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

    public void readAngles() {
        angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        currentYawAngle = angle.firstAngle;
        currentPitchAngle = angle.thirdAngle;
        currentRollAngle = angle.secondAngle;
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

}