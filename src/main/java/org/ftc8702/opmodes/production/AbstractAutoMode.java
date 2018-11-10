package org.ftc8702.opmodes.production;

import org.ftc8702.opmodes.InnovoticsActiveOpMode;
import org.ftc8702.configurations.production.Team8702ProdAuto;


abstract class AbstractAutoMode extends InnovoticsActiveOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        GYRO_SENSOR_TURNER,
        DONE
    }

    private State currentState;

    private Team8702ProdAuto robot = new Team8702ProdAuto();
    private GyroAutoMode gyroMode;
    private double currentAngle;

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());
        gyroMode = new GyroAutoMode(robot, telemetry);

        gyroMode.init();
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        currentState = State.GYRO_SENSOR_TURNER;
        robot.stopRobot();
        robot.setRunMode();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
    }

    @Override
    protected void activeLoop(){
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        getTelemetryUtil().sendTelemetry();

        switch (currentState) {
            case GYRO_SENSOR_TURNER:
                logStage();
                sleep(1000);

                if(runWithAngleCondition(95)) {
                    currentState = State.DONE;
                }

                gyroMode.composeTelemetry();
                getTelemetryUtil().sendTelemetry();
                break;

            case DONE: // When all operations are complete
                logStage();
                break;
        }

        telemetry.update();
    }

    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }

    boolean runWithAngleCondition( double angle){
        currentAngle = gyroMode.getAngles().firstAngle;

        if(currentAngle < 0) {
            currentAngle = currentAngle * (-1);
        }

        if(currentAngle > angle) {
            robot.stopRobot();
            return true;
        }

        robot.turnLeft(.2);
        return false;

    }

}


