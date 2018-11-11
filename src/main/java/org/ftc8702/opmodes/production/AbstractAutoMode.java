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
        COLOR_SENSOR_SELF_ADJUST,
        MOVE_TO_HOME_DEPOT,
        GYRO_SENSOR_TURNER,
        DONE
    }

    private State currentState;

    private Team8702ProdAuto robot = new Team8702ProdAuto();
    private GyroAutoMode gyroMode;

    private double currentAngle;

    private ColorSensorAdjustmentAutoMode colorSensorAdjustMode;
    private MoveToHomeDepotAutoMode moveToHomeDepotMode;
    private boolean targetReached = false;

    //Set ColorValue to zilch
    //ColorValue panelColor = ColorValue.ZILCH;
    //abstract ColorValue getPanelColor();

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());

        colorSensorAdjustMode = new ColorSensorAdjustmentAutoMode(robot, telemetry);
        colorSensorAdjustMode.init();

        moveToHomeDepotMode = new MoveToHomeDepotAutoMode(robot, telemetry);
        moveToHomeDepotMode.init();

        gyroMode = new GyroAutoMode(robot, getTelemetryUtil());
        gyroMode.init();

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        currentState = State.COLOR_SENSOR_SELF_ADJUST;
        robot.stopRobot();
        robot.setRunMode();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        getTelemetryUtil().sendTelemetry();

        switch (currentState) {
            case COLOR_SENSOR_SELF_ADJUST:
                logStage();
                targetReached = colorSensorAdjustMode.startAdjustment();
                if (targetReached) {
                    currentState = State.MOVE_TO_HOME_DEPOT;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;
            case MOVE_TO_HOME_DEPOT:
                logStage();
                targetReached = moveToHomeDepotMode.moveToHomeDepot();
                if (targetReached) {
                    currentState = State.GYRO_SENSOR_TURNER;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;
            case GYRO_SENSOR_TURNER:
                logStage();
                targetReached = gyroMode.runWithAngleCondition(95);
                if(targetReached) {
                    currentState = State.DONE;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
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


