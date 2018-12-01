package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.opmodes.InnovoticsActiveOpMode;

@Autonomous(name = "AutoModeObjectDetectRoute", group = "Ops")
public class AutoModeObjectDetectRoute extends InnovoticsActiveOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        OBJECT_DETECT,
        COLOR_SENSOR_SELF_ADJUST,
        GYRO_SENSOR_TURNER,
        MOVE_TO_HOME_DEPOT,
        ULTRASONIC_DRIVE_TO_CRATER,
        GO_OVER_RAMP,
        DONE
    }

    private State currentState;

    private Team8702ProdAuto robot = new Team8702ProdAuto();

    private GyroAutoMode gyroMode;
    private ColorSensorAdjustmentAutoMode colorSensorAdjustMode;
    private MoveToHomeDepotAutoMode moveToHomeDepotMode;
    private UltrasonicDriveToCraterAutoMode ultrasonicDriveToCrater;
    private ObjectDetectionAutoMode objectDetectRoute;
    private boolean targetReached = false;


    //Set ColorValue to zilch
    //ColorValue panelColor = ColorValue.ZILCH;
    //abstract ColorValue getPanelColor();

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());

        gyroMode = new GyroAutoMode(robot, getTelemetryUtil());
        gyroMode.init();
        objectDetectRoute = new ObjectDetectionAutoMode(robot, getTelemetryUtil(), gyroMode);
        objectDetectRoute.init();
        double angleToGold = objectDetectRoute.getGoldMineralAngle();
        getTelemetryUtil().addData("Angle To Gold", angleToGold +" degs");

        moveToHomeDepotMode = new MoveToHomeDepotAutoMode(robot, getTelemetryUtil());
        moveToHomeDepotMode.setNeedMoveBeginingForward(false);
        moveToHomeDepotMode.init();
/*
        colorSensorAdjustMode = new ColorSensorAdjustmentAutoMode(robot, getTelemetryUtil());
        colorSensorAdjustMode.init();



        ultrasonicDriveToCrater = new UltrasonicDriveToCraterAutoMode(robot, getTelemetryUtil(), gyroMode);
        ultrasonicDriveToCrater.init();
*/
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        currentState = State.OBJECT_DETECT;

/*
        robot.stopRobot();
        robot.setRunMode();
        */
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
            case OBJECT_DETECT:
                logStage();
                targetReached = objectDetectRoute.detectAndknockDownGoldMineral();
                if (targetReached) {
                    currentState = State.MOVE_TO_HOME_DEPOT; //COLOR_SENSOR_SELF_ADJUST;
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
                targetReached = gyroMode.runWithAngleCondition(45);
                if(targetReached) {
                    currentState = State.ULTRASONIC_DRIVE_TO_CRATER;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case ULTRASONIC_DRIVE_TO_CRATER:
                logStage();
                targetReached = ultrasonicDriveToCrater.ultrasonicDriveToCrater();
                if (targetReached) {
                    currentState = State.GO_OVER_RAMP;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case GO_OVER_RAMP:
                logStage();
                targetReached = gyroMode.testElevationChange(15, 11);
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

/*
            case COLOR_SENSOR_SELF_ADJUST:
                logStage();
                targetReached = colorSensorAdjustMode.startAdjustment();
                if (targetReached) {
                    currentState = State.MOVE_TO_HOME_DEPOT;
                    gyroMode.init();
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;
*/
        }
        telemetry.update();
    }

    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }
}
