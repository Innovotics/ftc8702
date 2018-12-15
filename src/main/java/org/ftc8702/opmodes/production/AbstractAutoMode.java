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
        DETECT_GOLD_MINERAL,
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
    private int LimitingEncoderValue = 12000;//12400;

    private double initialLeftAngleToGold = 25;
    private double initialRightAngleToGold = -25;

    private double reverseLeftAngleToGold = -37;
    private double reverseRightAngleToGold = 40;

    //2064.51612903225806 per inch
    private ObjectDetectionAutoMode.Position goldPosition;

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
        //goldPosition = objectDetectRoute.getGoldMineralAngle();
        getTelemetryUtil().addData("gold position", goldPosition+"");

        ultrasonicDriveToCrater = new UltrasonicDriveToCraterAutoMode(robot, getTelemetryUtil(), gyroMode);
        ultrasonicDriveToCrater.init();

        robot.hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        getTelemetryUtil().addData("Motor Encoder Value: ", robot.hook.getCurrentPosition());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        goldPosition = ObjectDetectionAutoMode.Position.CENTER; // for testing only
        currentState = State.DETECT_GOLD_MINERAL;
        robot.stopRobot();
        robot.setRunMode();

        robot.markerDropper.setPosition(0.9);
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

            case DETECT_GOLD_MINERAL:
                logStage();
                goldPosition = objectDetectRoute.detectGoldMineral();
                sleep(1000);

                getTelemetryUtil().addData("gold position: ", goldPosition+"");
                getTelemetryUtil().sendTelemetry();
                telemetry.update();

                currentState = State.HOOK;
                targetReached = false;

                robot.stopRobot();
                sleep(500);
                break;

            case HOOK:
                logStage();

                //targetReached = hookMotorToEncoder.runToTarget(0.5, LimitingEncoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                robot.hook.setPower(0.7);
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
                gyroMode.goLeftAngleCondition(15);
                sleep(1000);

                robot.hook.setPower(-0.7);
                sleep(2500);
                robot.hook.setPower(0.0);

                robot.forwardRobot(0.3);
                sleep(500);
                robot.stopRobot();
                sleep(1000);

                //robot.forwardRobot(-0.1);
                //robot.sleep(750);
                gyroMode.goRightToAngleDegree(0);

                currentState = State.KNOCK_GOLD_MINERAL;//COLOR_SENSOR_SELF_ADJUST; //KNOCK_GOLD_MINERAL;
                targetReached = false;
                robot.stopRobot();
                sleep(500);
                break;

            case KNOCK_GOLD_MINERAL:
                logStage();

                getTelemetryUtil().addData("gold position: ", goldPosition+"");
                getTelemetryUtil().sendTelemetry();
                telemetry.update();

                if(goldPosition == ObjectDetectionAutoMode.Position.RIGHT) {
                    targetReached = gyroMode.goRightToAngleDegree(initialRightAngleToGold + 1);
                    sleep(500);
                    robot.forwardRobot(.3);
                    sleep(3000);
                    robot.stopRobot();
                    targetReached = gyroMode.goLeftAngleCondition(reverseRightAngleToGold);
                } else if (goldPosition == ObjectDetectionAutoMode.Position.LEFT){
                    targetReached = gyroMode.goLeftAngleCondition(initialLeftAngleToGold + 1);
                    sleep(500);
                    robot.forwardRobot(.3);
                    sleep(3000);
                    robot.stopRobot();
                    targetReached = gyroMode.goRightToAngleDegree(reverseLeftAngleToGold);
                } else {
                    // center
                    robot.forwardRobot(.3);
                    sleep(3000);
                    robot.stopRobot();
                    targetReached = true;
                }

                if(targetReached) {
                    currentState = State.MOVE_TO_HOME_DEPOT;
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
                    //gyroMode.init();
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
                // robot.markerDropper.setPosition(0.0);
                // robot.sleep(1000);
                robot.markerDropper.setPosition(0.0);
                robot.sleep(1000);
                robot.backwardRobot(.25);
                robot.sleep(1000);
                currentState = State.DONE;
                robot.stopRobot();
                sleep(500);
                break;

            case GYRO_SENSOR_TURNER:
                logStage();
                targetReached = gyroMode.goLeftAngleCondition(110);
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


