package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.ftc8702.opmodes.production.SkystoneAutoModeState;
import org.ftc8702.opmodes.Sensors.ObjectDetectionAutoModeWebcam;
import org.ftc8702.configurations.production.SkystoneAutoConfig;

import java.util.List;


@Autonomous(name = "Detect and Grab Skystone", group = "Ops")
public class SkystoneAutoModeDetection extends SkystoneAbstractAutoMode {

    public ObjectDetectionAutoModeWebcam webCamDetector = new ObjectDetectionAutoModeWebcam();
    private boolean accomplishedTask = false;
    private SkystoneAutoModeState currentState;
    private SkystoneAutoConfig robot = new SkystoneAutoConfig();

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        webCamDetector.initialize();
        currentState = SkystoneAutoModeState.DETECT_SKYSTONE;

    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();


        switch (currentState) {
            case DETECT_SKYSTONE:
                logStage();
                webCamDetector.getPosition(webCamDetector.detect());
                break;

            case HUG_STONE:
                logStage();
                break;


            case MOVE_STONE_TO_BUIDER_ZONE: // When all operations are complete
                logStage();
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
        getTelemetryUtil().addData("Stage", getCurrentState().toString());
        getTelemetryUtil().sendTelemetry();
    }

    //Find Vector

}
