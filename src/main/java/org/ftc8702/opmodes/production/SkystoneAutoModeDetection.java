package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import org.ftc8702.opmodes.Sensors.ObjectDetectionAutoModeWebcam;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.HUG_STONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.HUG_STONE2;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_STONE_TO_BUIDER_ZONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_STONE_TO_BUILDER_ZONE2;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_TO_SECOND_SKYSTONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.PARK;


@Autonomous(name = "Detect and Grab Skystone", group = "Ops")
public class SkystoneAutoModeDetection extends ActiveOpMode {

    public ObjectDetectionAutoModeWebcam webCamDetector = new ObjectDetectionAutoModeWebcam();
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    private SkystoneAutoConfig robot = new SkystoneAutoConfig();

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        //webCamDetector.initialize();
        currentState = SkystoneAutoModeState.DETECT_SKYSTONE;

    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();

        //Put
        //In
        //Switch
        //State
        //Statements
        //Into
        //Each
        //State
        //And
        //Calculate
        //Distance
        switch (currentState) {
            case DETECT_SKYSTONE:
                logStage();
                //webCamDetector.getPosition(webCamDetector.detect()); Put this back in after object detection code is done
                robot.driveTrain.strafeRight(1);
                sleep(1050);
                robot.driveTrain.stop();
                currentState = HUG_STONE;
                break;

            case HUG_STONE:
                logStage();
                robot.hugger.HuggerTopDown(1); //we need to decide wether to use the top hugger or bottom one
                sleep(1000);
                currentState = MOVE_STONE_TO_BUIDER_ZONE;
                break;


            case MOVE_STONE_TO_BUIDER_ZONE: // When all operations are complete
                logStage();
                robot.driveTrain.strafeLeft(1);
                sleep(500);
                robot.driveTrain.goBackward(0.7f);
                sleep(2500);
                robot.driveTrain.stop();
                robot.hugger.HuggerTopUp(1);//we need to decide wether to use the top hugger or the bottom one
                sleep(1000);
                robot.driveTrain.rotateRight(0.7f);
                sleep(300);
                robot.driveTrain.stop();
                currentState = MOVE_TO_SECOND_SKYSTONE;
                break;

            case MOVE_TO_SECOND_SKYSTONE:
                logStage();
                robot.driveTrain.goForwardFullSpeedInFeet(8, false);
                robot.driveTrain.strafeRight(1);
                sleep(750);
                robot.driveTrain.stop();
                currentState = HUG_STONE2;
                break;

            case HUG_STONE2:
                logStage();
                robot.hugger.HuggerTopDown(1);
                sleep(1000);
                currentState = MOVE_STONE_TO_BUILDER_ZONE2;
                break;

            case MOVE_STONE_TO_BUILDER_ZONE2:
                logStage();
                robot.driveTrain.strafeLeft(1);
                sleep(1000);
                robot.driveTrain.goBackward(1);
                sleep(4000);
                robot.driveTrain.stop();
                currentState = PARK;
                break;

            case PARK:
                logStage();
                robot.driveTrain.goForward(.3f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    currentState = DONE;
                }
                else if(currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN){
                    robot.driveTrain.goForward(.3f);
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

    //Find Vector

}