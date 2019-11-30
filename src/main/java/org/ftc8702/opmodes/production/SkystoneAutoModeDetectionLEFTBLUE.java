package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import org.ftc8702.opmodes.Sensors.ObjectDetectionAutoModeWebcamOLD;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;
import org.ftc8702.utils.StonePosition;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_STONE_TO_BUILDER_ZONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.PARK;

@Autonomous(name = " LEFT BLUE Auto Detect", group = "Ops")
public class SkystoneAutoModeDetectionLEFTBLUE extends ActiveOpMode{

    public ObjectDetectionAutoModeWebcamOLD webCamDetector = new ObjectDetectionAutoModeWebcamOLD();
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    private int detectCount = 0;

    private SkystoneAutoConfig robot = new SkystoneAutoConfig();

    @Override
    protected void onInit() {

        robot.init(super.hardwareMap, getTelemetryUtil());
        webCamDetector.initialize(hardwareMap, telemetry);
        currentState = SkystoneAutoModeState.DETECT_SKYSTONE;


    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();

        switch (currentState) {
            case DETECT_SKYSTONE:
                logStage();

                ObjectDetectionAutoModeWebcamOLD.RecognitionResult result = webCamDetector.detect();
                detectCount++;

                if (result != null) {
                    //Detect Webcam and Move robot
                    if(result.position == StonePosition.CENTER) {
                        robot.driveTrain.strafeLeft(.2f);
                        telemetry.addData("Position", "Center");
                        telemetry.addData("Angle: ", result.angleToPosition);
                        telemetry.update();

                    }
                    else if (result.position == StonePosition.LEFT) {
                        telemetry.addData("Position", "Left");
                        telemetry.addData("Angle: ", result.angleToPosition);
                        telemetry.update();
                        robot.driveTrain.stop();
                        sleep(1000);
                        robot.driveTrain.goBackward(.1f);
                        sleep(1000);
                        robot.jaja.foundationGrabberRight.setPosition(0);
                        //Next Step
                        currentState = MOVE_STONE_TO_BUILDER_ZONE;
                    }

                    else if(result.position == StonePosition.RIGHT) {
                        telemetry.addData("Position", "Right");
                        telemetry.addData("Angle: ", result.angleToPosition);
                        telemetry.update();
                        robot.driveTrain.stop();
                        sleep(1000);

                        robot.driveTrain.goForward(.1f);
                        sleep(1000);
                        robot.jaja.foundationGrabberLeft.setPosition(1.0);
                        //Next Step
                        currentState = MOVE_STONE_TO_BUILDER_ZONE;
                    }
                }
                break;

            case GRAB_SKY_STONE:
                currentState = MOVE_STONE_TO_BUILDER_ZONE;
                break;
            case MOVE_STONE_TO_BUILDER_ZONE: // When all operations are complete
                logStage();
                robot.driveTrain.goForward(1);//Pulls the skystone out a little bit
                sleep(500);
                robot.driveTrain.strafeRight(0.7f);
                sleep(2500);
                robot.driveTrain.stop();
                robot.jaja.JaJaUp();
                currentState = PARK;
                break;

            case PARK:
                logStage();
                robot.driveTrain.strafeLeft(.3f);
                ColorValue currentColor = ColorUtil.getColor(robot.colorSensor);

                if(currentColor == ColorValue.BLUE || currentColor == ColorValue.RED) {
                    telemetry.addData("Touching ", currentColor);
                    currentState = DONE;
                }
                else if(currentColor == ColorValue.ZILCH || currentColor == ColorValue.GREEN){
                    robot.driveTrain.strafeLeft(.3f);
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
}
