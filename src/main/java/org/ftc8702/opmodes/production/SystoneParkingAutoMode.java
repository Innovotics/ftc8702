package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.LOWER_FOUNDATION_GRABBER;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_FROM_FOUNDATION;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.MOVE_TO_FOUNDATION;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.PARK;

@Autonomous(name = "ParkingOpMode", group = "Ops")
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
