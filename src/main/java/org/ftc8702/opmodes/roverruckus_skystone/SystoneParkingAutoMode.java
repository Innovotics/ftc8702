package org.ftc8702.opmodes.roverruckus_skystone;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.production.SkystoneAutoConfig;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.LOWER_FOUNDATION_GRABBER;

//@Autonomous(name = "ParkingOpMode", group = "Ops")
public class SystoneParkingAutoMode extends ActiveOpMode {

    public SkystoneAutoConfig robot = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        currentState = LOWER_FOUNDATION_GRABBER;
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();

    switch (currentState) {

        case LOWER_FOUNDATION_GRABBER:
            logStage();
            robot.jaja.JaJaLeftDown();
            robot.jaja.JaJaRightDown();
            sleep(1000);
            currentState = DONE;
            break;

        case DONE: // When all operations are complete
            logStage();
            robot.driveTrain.stop();
            setOperationsCompleted();
            break;
    }
    getTelemetryUtil().sendTelemetry();
        telemetry.update();

}
    public void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }
}
