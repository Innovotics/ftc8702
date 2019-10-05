package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.production.SkystoneAutoConfig;
import org.ftc8702.opmodes.test.BenColorSensorTest;
import org.ftc8702.utils.ColorUtil;
import org.ftc8702.utils.ColorValue;

import ftcbootstrap.ActiveOpMode;

import static org.ftc8702.opmodes.production.SkystoneAutoModeState.DONE;
import static org.ftc8702.opmodes.production.SkystoneAutoModeState.PARK;

abstract class SkystoneAbstractAutoMode extends ActiveOpMode {

    private SkystoneAutoConfig robot = new SkystoneAutoConfig();
    private SkystoneAutoModeState currentState;
    private boolean accomplishedTask = false;

    @Override
    protected void onInit() {
        robot.init(hardwareMap, getTelemetryUtil());
        currentState = SkystoneAutoModeState.INIT;
    }


    public void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }

    public SkystoneAutoConfig getRobot() {
        return robot;
    }

    public SkystoneAutoModeState getCurrentState() {
        return currentState;
    }

    public void setRobot(SkystoneAutoConfig robot) {
        this.robot = robot;
    }

    public void setCurrentState(SkystoneAutoModeState currentState) {
        this.currentState = currentState;
    }

    public void setAccomplishedTask(boolean accomplishedTask) {
        this.accomplishedTask = accomplishedTask;
    }
}
