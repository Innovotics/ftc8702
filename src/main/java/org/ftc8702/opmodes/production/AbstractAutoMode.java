package org.ftc8702.opmodes.production;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
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

                if(runWithAngleCondition(95)) {
                    currentState = State.DONE;
                }

//                gyroMode.composeTelemetry();
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

    boolean runWithAngleCondition(double angle){
        gyroMode.readAngles();
        currentAngle = gyroMode.getAngles().firstAngle;

        getTelemetryUtil().addData("Current Angle", currentAngle);
        getTelemetryUtil().sendTelemetry();

        if(Math.abs(currentAngle) > angle) {
            robot.stopRobot();
            return true;
        }

        robot.turnLeft(.2);
        return false;

    }

}


