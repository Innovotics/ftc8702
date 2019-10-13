package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.ftc8702.opmodes.production.SkystoneAutoModeState;
import org.ftc8702.opmodes.Sensors.ObjectDetectionAutoModeWebcam;
import org.ftc8702.configurations.production.SkystoneAutoConfig;



@Autonomous(name = "Detect and Grab Skystone", group = "Ops")
public class SkystoneAutoModeDetection extends SkystoneAbstractAutoMode {

    private VuforiaLocalizer vuforia;
    public ObjectDetectionAutoModeWebcam webCamDetector = new ObjectDetectionAutoModeWebcam();
    private static final String VUFORIA_KEY = "ASVx607/////AAABmeYAtysWv0AXpZe726GhwxofjFOd04VMHXb225G3ekEFMyTp6Wb9dJcGjGpeDNyRQBzGLKn2BMDTmBb5fMFIUBrN/LdHRaR1XtWhBnAusAVpP5nhLPAAdNIT6duwXmcijvtNKrHg4Eh/dA8UPFBRdx/uFkWpRYwEntXDWYor3Fo03J02mLPUvic76qSUlNBWhDM3pe/V1I82oGRt/X4yEsXKRk3YiDFnAMbxziGnYAV2I5rX9oVPriZ9y+JB5YvfSZIIYgmp3GYxQVJjIqUNNYM5+PBaBxBy012laaKhqf40BYxX41QEfbCq+KNx76JSCOSvVRKEay39+czt1JAyaBMIWadXSHrrmPI12JRAG+57";

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




}
