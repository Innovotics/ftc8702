package org.ftc8702.opmodes.roverruckus_skystone;

import ftcbootstrap.ActiveOpMode;

abstract class SkystoneAbstractAutoMode extends ActiveOpMode {

    public ColorSensorAdjustmentAutoMode.SkystoneAutoConfig robot = new ColorSensorAdjustmentAutoMode.SkystoneAutoConfig();
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

    public ColorSensorAdjustmentAutoMode.SkystoneAutoConfig getRobot() {
        return robot;
    }

    public SkystoneAutoModeState getCurrentState() {
        return currentState;
    }

    public void setRobot(ColorSensorAdjustmentAutoMode.SkystoneAutoConfig robot) {
        this.robot = robot;
    }

    public void setCurrentState(SkystoneAutoModeState currentState) {
        this.currentState = currentState;
    }

    public void setAccomplishedTask(boolean accomplishedTask) {
        this.accomplishedTask = accomplishedTask;
    }
}
