package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.opmodes.InnovoticsActiveOpMode;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;


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
        LIFT_ARM_UP,
        DROP_MARKER,
        BACK_TO_CRATER,
        GYRO_SENSOR_TURNER,
        ULTRASONIC_DRIVE_TO_CRATER,
        GO_OVER_RAMP,
        SecondaryGetToCrater,
        TURN_TO_HOMEDEPOT,
        DONE
    }

    protected State currentState;

    //double odsReading = getOdsReading();
    protected Team8702ProdAuto robot = new Team8702ProdAuto();
    protected GyroAutoMode gyroMode;
    protected ObjectDetectionAutoMode objectDetectRoute;

    protected MoveToHomeDepotAutoMode moveToHomeDepotMode;
    protected boolean targetReached = false;
    protected int LimitingEncoderValue = 12000;//12400;

    protected double initialLeftAngleToGold = 29;
    protected double initialRightAngleToGold = -34;
    protected double reverseLeftAngleToGold = -34; // -42;

    protected double reverseRightAngleToGold = 37; // -45

    //2064.51612903225806 per inch
    protected ObjectDetectionAutoMode.Position goldPosition;

    protected void onInit() {

        robot.init(hardwareMap, getTelemetryUtil());

        moveToHomeDepotMode = new MoveToHomeDepotAutoMode(robot, getTelemetryUtil());
        moveToHomeDepotMode.init();

        gyroMode = new GyroAutoMode(robot, getTelemetryUtil());
        gyroMode.init();

        objectDetectRoute = new ObjectDetectionAutoMode(robot, getTelemetryUtil(), gyroMode);
        objectDetectRoute.init();
        getTelemetryUtil().addData("gold position", goldPosition+"");

        robot.hook.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.hook.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        getTelemetryUtil().addData("Motor Encoder Value: ", robot.hook.getCurrentPosition());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

        goldPosition = ObjectDetectionAutoMode.Position.CENTER; // for testing only
        currentState = State.HOOK;
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
        telemetry.update();

        switch (currentState) {

            case LIFT_ARM_UP:
                logStage();
                robot.longArm.setPower(1.0);
                sleep(1750);

                robot.longArm.setPower(0.0);
                sleep(500);

                currentState = State.DETECT_GOLD_MINERAL;
                targetReached = false;
                break;

            case DETECT_GOLD_MINERAL:
                logStage();
                sleep(1000);
                goldPosition = objectDetectRoute.detectGoldMineral();
                sleep(500);

                getTelemetryUtil().addData("gold position: ", goldPosition+"");
                getTelemetryUtil().sendTelemetry();
                telemetry.update();

                currentState = State.HOOK;

                robot.stopRobot();
                sleep(500);
                break;

            case HOOK:
                logStage();

                robot.hook.setPower(1.0);
                robot.shortArm.setPower(2.0);
               sleep(7000);

                robot.hook.setPower(0.0);
                robot.shortArm.setPower(0.0);
                targetReached = true;

                if(targetReached) {
                    currentState = State.DONE;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            case TURN_TO_UNHOOK:
                logStage();
                gyroMode.goRightToAngleDegree(-9);
                sleep(750);

                robot.hook.setPower(-0.9);
                sleep(2000);
                robot.hook.setPower(0.0);

                robot.forwardRobot(0.3);
                sleep(250);

                robot.forwardRobot(0.2);
                sleep(500);

                robot.stopRobot();
                sleep(750);

                //robot.forwardRobot(-0.1);
                //robot.sleep(750);
                gyroMode.goLeftAngleCondition(0);

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
                    targetReached = gyroMode.goRightToAngleDegree(initialRightAngleToGold);
                    sleep(500);
                    robot.forwardRobot(.2);
                    sleep(3000);
                    robot.stopRobot();
                    sleep(500);
                    targetReached = gyroMode.goLeftAngleCondition(reverseRightAngleToGold);
                } else if (goldPosition == ObjectDetectionAutoMode.Position.LEFT){
                    targetReached = gyroMode.goLeftAngleCondition(initialLeftAngleToGold);
                    sleep(500);
                    robot.forwardRobot(.2);
                    sleep(3000);
                    robot.stopRobot();
                    sleep(500);
                    targetReached = gyroMode.goRightToAngleDegree(reverseLeftAngleToGold);
                } else {
                    // center
                    robot.forwardRobot(.2);
                    sleep(1500);
                    robot.stopRobot();
                    targetReached = true;
                }

                if(targetReached) {
                    currentState = State.DONE;
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
                robot.clawA.setPosition(1.0);
                robot.clawB.setPosition(0.0);

                robot.shortArm.setPower(2.0);
                sleep(5000);

                currentState = State.DONE;
                robot.shortArm.setPower(0.0);
                sleep(500);
                break;

            case BACK_TO_CRATER:
                logStage();
                if (goldPosition == ObjectDetectionAutoMode.Position.LEFT
                        || goldPosition == ObjectDetectionAutoMode.Position.RIGHT) {
                    robot.backwardRobot(.6);
                    robot.sleep(2000);
                }
                robot.stopRobot();
                currentState = State.DONE;
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
                setOperationsCompleted();
                break;
        }

        telemetry.update();
    }

    public void logStage() {
        getTelemetryUtil().addData("Stage", currentState.toString());
        getTelemetryUtil().sendTelemetry();
    }


//    public double getOdsReading () {
//        return  robot.ods.getLightDetected();
//    }

}


