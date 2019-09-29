package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.*;

import org.ftc8702.configurations.production.SkystoneAutoConfig;

import ftcbootstrap.ActiveOpMode;

@Autonomous(name = "SkystoneSimpleAutoMode", group = "Ops")
public class SkystoneSimpleAutoMode extends ActiveOpMode {

    private SkystoneAutoConfig config = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;

    @Override
    protected void onInit() {
        config.init(hardwareMap, getTelemetryUtil());
        currentState = PARK;
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        getTelemetryUtil().sendTelemetry();
        telemetry.update();

        switch (currentState) {

            case PARK:
                currentState = DONE;
                logStage();
                config.driveTrain.goForward(.8f);
                sleep(1500);
                config.driveTrain.stop();
                break;

            case DONE: // When all operations are complete
                logStage();
                setOperationsCompleted();
                break;
        }

        telemetry.update();
    }

    public void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }
}
