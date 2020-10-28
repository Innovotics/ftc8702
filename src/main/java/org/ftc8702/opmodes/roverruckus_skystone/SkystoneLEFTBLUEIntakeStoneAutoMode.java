package org.ftc8702.opmodes.roverruckus_skystone;

import static org.ftc8702.opmodes.roverruckus_skystone.SkystoneAutoModeState.*;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import ftcbootstrap.ActiveOpMode;

public class SkystoneLEFTBLUEIntakeStoneAutoMode extends ActiveOpMode {

    public SkystoneAutoConfig robot = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        currentState = MOVE_TO_FOUNDATION;
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        telemetry.update();

    }
}

