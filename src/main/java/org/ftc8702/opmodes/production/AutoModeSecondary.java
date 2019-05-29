package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.ColorValue;


@Autonomous(name = "AutoModeSecondary", group = "Ops")
public class AutoModeSecondary extends AbstractAutoMode {

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
                robot.motorName.setPower(1.0);
                sleep(1750);

                currentState = State.DETECT_GOLD_MINERAL;
                targetReached = false;
                break;

            case DETECT_GOLD_MINERAL:
                logStage();
                sleep(1000);
                goldPosition = objectDetectRoute.detectGoldMineral();
                sleep(500);

                getTelemetryUtil().addData("gold position: ", goldPosition + "");
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

                robot.motorName.setPower(1.0);
                robot.motorName.setPower(2.0);
                sleep(7200);

                targetReached = true;

                if (targetReached) {
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

                robot.motorName.setPower(-0.9);
                sleep(2000);

                robot.forwardRobot(0.3);
                sleep(250);

                gyroMode.goLeftAngleCondition(0);

                currentState = State.KNOCK_GOLD_MINERAL;//COLOR_SENSOR_SELF_ADJUST; //KNOCK_GOLD_MINERAL;
                targetReached = false;
                robot.stopRobot();
                sleep(500);
                break;

            case KNOCK_GOLD_MINERAL:
                logStage();

                getTelemetryUtil().addData("gold position: ", goldPosition + "");
                getTelemetryUtil().sendTelemetry();
                telemetry.update();

                if (goldPosition == ObjectDetectionAutoMode.Position.RIGHT) {
                    targetReached = gyroMode.goRightToAngleDegree(initialRightAngleToGold);
                    sleep(250);
                    robot.forwardRobot(.4);
                    sleep(1000);
                    robot.stopRobot();
                } else if (goldPosition == ObjectDetectionAutoMode.Position.LEFT) {
                    targetReached = gyroMode.goLeftAngleCondition(initialLeftAngleToGold);
                    sleep(250);
                    robot.forwardRobot(.4);
                    sleep(1000);
                    robot.stopRobot();
                } else {
                    // center
                    robot.forwardRobot(.4);
                    sleep(1000);
                    robot.stopRobot();
                    targetReached = true;
                }

                if (targetReached) {
                    currentState = State.DONE;
                    targetReached = false;

                    robot.stopRobot();
                    sleep(100);
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
                    if (goldPosition == ObjectDetectionAutoMode.Position.LEFT) {
                        targetReached = gyroMode.goLeftAngleCondition((reverseLeftAngleToGold));
                        robot.sleep(500);
                        robot.forwardRobot(0.3);
                        robot.sleep(1000);
                        robot.turnLeft(0.7);
                        robot.sleep(1000);
                        robot.stopRobot();
                        if (goldPosition == ObjectDetectionAutoMode.Position.CENTER) {
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
        }
    }
}
