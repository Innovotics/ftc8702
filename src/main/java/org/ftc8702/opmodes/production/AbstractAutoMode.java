package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.ftc8702.opmodes.InnovoticsActiveOpMode;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.InnovoticsRobotProperties;
import org.ftc8702.utilities.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;


abstract class AbstractAutoMode extends InnovoticsActiveOpMode {

    //States for actual autonomous
    protected enum State {
        INIT,
        HOOK,
        TURN_TO_UNHOOK,
        KNOCK_GOLD_MINERAL,
        COLOR_SENSOR_SELF_ADJUST,
        MOVE_TO_HOME_DEPOT,
        DROP_MARKER,
        GYRO_SENSOR_TURNER,
        ULTRASONIC_DRIVE_TO_CRATER,
        GO_OVER_RAMP,
        DONE
    }

    private State currentState;

    private Team8702ProdAuto robot = new Team8702ProdAuto();
    private GyroAutoMode gyroMode;
    private ObjectDetectionAutoMode objectDetectRoute;

    private double currentAngle;

    private ColorSensorAdjustmentAutoMode colorSensorAdjustMode;
    private MoveToHomeDepotAutoMode moveToHomeDepotMode;
    private UltrasonicDriveToCraterAutoMode ultrasonicDriveToCrater;
    private boolean targetReached = false;
  //  private MotorToEncoder hookMotorToEncoder;
    private int LimitingEncoderValue = 12400;
    //2064.51612903225806 per inch
    private double angleToGold = 0;

    //Set ColorValue to zilch
    //ColorValue panelColor = ColorValue.ZILCH;
    //abstract ColorValue getPanelColor();

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());

        colorSensorAdjustMode = new ColorSensorAdjustmentAutoMode(robot, getTelemetryUtil());
        colorSensorAdjustMode.init();

        moveToHomeDepotMode = new MoveToHomeDepotAutoMode(robot, getTelemetryUtil());
        moveToHomeDepotMode.init();

        gyroMode = new GyroAutoMode(robot, getTelemetryUtil());
        gyroMode.init();

        objectDetectRoute = new ObjectDetectionAutoMode(robot, getTelemetryUtil(), gyroMode);
        objectDetectRoute.init();
        angleToGold = objectDetectRoute.getGoldMineralAngle();
        getTelemetryUtil().addData("Angle To Gold", angleToGold +" degs");

        ultrasonicDriveToCrater = new UltrasonicDriveToCraterAutoMode(robot, getTelemetryUtil(), gyroMode);
        ultrasonicDriveToCrater.init();

        robot.hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        getTelemetryUtil().addData("Motor Encoder Value: ", robot.hook.getCurrentPosition());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        currentState = State.HOOK;
        robot.stopRobot();
        robot.setRunMode();

        robot.markerDropper.setPosition(.5);
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();


    }

    @Override
    protected void activeLoop() throws InterruptedException {
        getTelemetryUtil().addData("activeLoop current state", currentState.toString());
        getTelemetryUtil().sendTelemetry();

        getTelemetryUtil().sendTelemetry();
        telemetry.update();

        switch (currentState) {

            case HOOK:
                logStage();

                //targetReached = hookMotorToEncoder.runToTarget(0.5, LimitingEncoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                robot.hook.setPower(-0.7);
                while(Math.abs(robot.hook.getCurrentPosition()) < LimitingEncoderValue) {
                    getTelemetryUtil().addData("Encoder Value: ", robot.hook.getCurrentPosition());
                    getTelemetryUtil().sendTelemetry();
                }

                robot.hook.setPower(0.0);
                targetReached = true;

                if(targetReached) {
                    currentState = State.TURN_TO_UNHOOK;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case TURN_TO_UNHOOK:
                logStage();
                gyroMode.runWithAngleCondition(45);
                robot.forwardRobot(0.1);
                robot.sleep(750);
                robot.stopRobot();
                sleep(500);
                gyroMode.goRightToAngleDegree(45);

                currentState = State.DONE; //KNOCK_GOLD_MINERAL;
                robot.stopRobot();
                sleep(500);
                break;

            case KNOCK_GOLD_MINERAL:
                logStage();
                targetReached = objectDetectRoute.knockDownGoldMineral(angleToGold);
                if (targetReached) {
                    currentState = State.MOVE_TO_HOME_DEPOT;
                    gyroMode.init();
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

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

            case MOVE_TO_HOME_DEPOT:
                logStage();
                targetReached = moveToHomeDepotMode.moveToHomeDepot();
                if (targetReached) {
                    currentState = State.DROP_MARKER;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case DROP_MARKER:
                logStage();
                robot.markerDropper.setPosition(0.0);
                robot.sleep(1000);
                robot.markerDropper.setPosition(1.0);
                robot.sleep(1000);
                robot.markerDropper.setPosition(.5);

                currentState = State.GYRO_SENSOR_TURNER;
                robot.stopRobot();
                sleep(500);
                break;

            case GYRO_SENSOR_TURNER:
                logStage();
                targetReached = gyroMode.runWithAngleCondition(110);
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
        }

        telemetry.update();
    }

    private void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }
}


