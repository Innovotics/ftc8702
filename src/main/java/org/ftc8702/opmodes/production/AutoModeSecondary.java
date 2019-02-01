package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.ColorValue;


@Autonomous(name = "AutoModeSecondary", group = "Ops")
public class AutoModeSecondary extends AbstractAutoMode {
//    private State currentState;
//
//    private Team8702ProdAuto robot = new Team8702ProdAuto();
//    private GyroAutoMode gyroMode;
//    private ObjectDetectionAutoMode objectDetectRoute;
//
//    private double currentAngle;
//
//    private ColorSensorAdjustmentAutoMode colorSensorAdjustMode;
//    private MoveToHomeDepotAutoMode moveToHomeDepotMode;
//    private UltrasonicDriveToCraterAutoMode ultrasonicDriveToCrater;
//    private boolean targetReached = false;
//
    //2064.51612903225806 per inch
//    private ObjectDetectionAutoMode.Position goldPosition;

    //Set ColorValue to zilch
    //ColorValue panelColor = ColorValue.ZILCH;
    //abstract ColorValue getPanelColor();

    @Override
    protected void onInit() {
        super.onInit();

        goldPosition = ObjectDetectionAutoMode.Position.CENTER; // for testing only
        currentState = State.LIFT_ARM_UP;
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

            case INIT:
                break;

            case HOOK:
                logStage();

                robot.hook.setPower(1.0);
                robot.shortArm.setPower(2.0);
                sleep(7200);

                robot.hook.setPower(0.0);
                robot.shortArm.setPower(0.0);
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
                    robot.forwardRobot(.5);
                    sleep(2000);
                    robot.stopRobot();
                    sleep(500);
                    targetReached = gyroMode.goLeftAngleCondition(reverseRightAngleToGold);
                } else if (goldPosition == ObjectDetectionAutoMode.Position.LEFT){
                    targetReached = gyroMode.goLeftAngleCondition(initialLeftAngleToGold);
                    sleep(500);
                    robot.forwardRobot(.5);
                    sleep(2000);
                    robot.stopRobot();
                    sleep(500);
                    targetReached = gyroMode.goRightToAngleDegree(reverseLeftAngleToGold);
                } else {
                    // center
                    robot.forwardRobot(.4);
                    sleep(1000);
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

            case SecondaryGetToCrater:
                logStage();

                if (goldPosition == ObjectDetectionAutoMode.Position.RIGHT) {
                    targetReached = gyroMode.goLeftAngleCondition(reverseRightAngleToGold);
                    robot.sleep(500);
                    robot.forwardRobot(0.3);
                    robot.sleep(1000);
                    robot.turnRight(0.7);
                    robot.sleep(1000);
                    robot.stopRobot();
                if (goldPosition == ObjectDetectionAutoMode.Position.LEFT){
                    targetReached = gyroMode.goLeftAngleCondition((reverseLeftAngleToGold));
                    robot.sleep(500);
                    robot.forwardRobot(0.3);
                    robot.sleep(1000);
                    robot.turnLeft(0.7);
                    robot.sleep(1000);
                    robot.stopRobot();
                if (goldPosition == ObjectDetectionAutoMode.Position.CENTER){
                    robot.forwardRobot(0.3);
                    robot.sleep(1000);
                    robot.turnLeft(0.7);
                    robot.sleep(1000);
                    robot.stopRobot();
                }
                }
                }

                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(500);
                }
                break;

            /*case COLOR_SENSOR_SELF_ADJUST:
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
                currentState = State.BACK_TO_CRATER;
                robot.stopRobot();
                sleep(500);
               break;

            case BACK_TO_CRATER:
                logStage();
                if (goldPosition == ObjectDetectionAutoMode.Position.LEFT
                        || goldPosition == ObjectDetectionAutoMode.Position.RIGHT) {
                    robot.backwardRobot(.5);
                    robot.sleep(3000);
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
  */
            case COLOR_SENSOR_SELF_ADJUST:
                break;
            case MOVE_TO_HOME_DEPOT:
                break;
            case DROP_MARKER:
                break;
            case BACK_TO_CRATER:
                break;
            case GYRO_SENSOR_TURNER:
                break;
            case ULTRASONIC_DRIVE_TO_CRATER:
                break;
            case GO_OVER_RAMP:
                break;
            case TURN_TO_HOMEDEPOT:
                break;
            case DONE:
                break;
        }

    }
}
