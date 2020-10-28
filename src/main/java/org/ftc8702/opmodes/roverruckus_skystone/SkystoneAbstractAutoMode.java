package org.ftc8702.opmodes.roverruckus_skystone;

import org.ftc8702.configurations.production.SkystoneAutoConfig;

import ftcbootstrap.ActiveOpMode;

abstract class SkystoneAbstractAutoMode extends ActiveOpMode {

    public SkystoneAutoConfig robot = new SkystoneAutoConfig();
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
