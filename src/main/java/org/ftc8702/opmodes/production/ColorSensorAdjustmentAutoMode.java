package org.ftc8702.opmodes.production;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utilities.TelemetryUtil;
import org.ftc8702.utils.ColorValue;

import java.util.concurrent.TimeUnit;

import static org.ftc8702.utils.ColorUtil.getColor;
import static org.ftc8702.utils.ColorUtil.isRedOrBlueDetected;

public class ColorSensorAdjustmentAutoMode {
    private static final double FORWARD_SPEED = 0.15;
    private static final double BACKWARD_SPEED = -0.15;
    private static final double TURN_SPEED_RIGHT = 0.5;
    private static final double TURN_SPEED_LEFT = 0.5;
    private static final long BACKWARD_ADJUST_MILLIS = 1000;
    /*
    private static final double TURN_SPEED_RIGHT = 0.33;
    private static final double TURN_SPEED_LEFT = 0.26;
    private static final double TURN_BACKWORD_SPEED_RIGHT = -0.18;
    private static final double TURN_BACKWORD_SPEED_LEFT = -0.23;
    */
    private static final long TIME_OUT = 10_000; // 10 seconds

    private Team8702ProdAuto robot;
    private TelemetryUtil telemetry;

    private long startTime = 0;

    /*
    private boolean isRightMotorStopped = false;
    private boolean isLeftMotorStopped = false;
    */

    public ColorSensorAdjustmentAutoMode(Team8702ProdAuto robot, TelemetryUtil telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
    }

    public void init() {
        telemetry.addData("Init", getClass().getSimpleName() + " initialized.");
    }

    /*
    private void moveForward() {
        robot.motorR.setPower(isRightMotorStopped ? 0.0 : FORWARD_SPEED);
        robot.motorL.setPower(isLeftMotorStopped ? 0.0 : FORWARD_SPEED);
    }

    private void stopRight() {
        // boost the opposite side of motor when stopping on this side.  This is needed since just 1
        // motor tends to need a bit more power to move the robot.
        robot.motorL.setPower(isLeftMotorStopped ? 0.0 : TURN_SPEED_LEFT);
        // set this stopping side motor to backward to offset the forward momentum a bit to back
        // to its intended stopping position.
        robot.motorR.setPower(TURN_BACKWORD_SPEED_RIGHT);
    }

    private void stopLeft() {
        robot.motorL.setPower(TURN_BACKWORD_SPEED_LEFT);
        robot.motorR.setPower(isRightMotorStopped ? 0.0 : TURN_SPEED_RIGHT);
    }
    */

    private void turnLeft(double power) {
        robot.forwardRobot(power);
    }

    private void turnRight(double power) {
        robot.forwardRobot(power);
    }

    private void moveBackward() {
        robot.backwardRobot(BACKWARD_SPEED);
    }

    public boolean startAdjustment() throws InterruptedException {
        boolean isCompleted = false;
        startTime = System.currentTimeMillis();
        while (!isCompleted) {
            isCompleted = newAdjustmentLoop();
        }
        return isCompleted;
    }

    private boolean newAdjustmentLoop() throws InterruptedException {
        ColorValue rightColor = getColor(robot.colorSensorName);
        telemetry.addData("Right Color: ", rightColor.name());
        boolean isColorDetectedByRightSensor = isRedOrBlueDetected(rightColor);

        telemetry.addData("Left Color: ", rightColor.name());
        // boolean isColorDetectedByLeftSensor = isRedOrBlueDetected(leftColor);

        boolean isTimedOut = (System.currentTimeMillis() - startTime) > TIME_OUT;

//        if ((isColorDetectedByRightSensor && isColorDetectedByLeftSensor) || isTimedOut) {
//            robot.stopRobot(); // stop robot moving to slow down the momentum
//            telemetry.addData("Exit", isTimedOut ? "TimedOut" : "Adjusted");
//            telemetry.sendTelemetry();
//            // end state - when both motors have stopped, return true to indicate we are done.
//            return true;
//        }
//        else if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
//            robot.forwardRobot(FORWARD_SPEED);
//        }
//        else if (isColorDetectedByLeftSensor && !isColorDetectedByRightSensor) {
//            telemetry.addData("Turning: ", "Left");
//            turnLeft(TURN_SPEED_LEFT);
//            boolean isRightSensorDetectColor = false;
//            while (!isRightSensorDetectColor) {
//                ColorValue rightSensorColor = getColor(robot.colorSensorName);
//                isRightSensorDetectColor = isRedOrBlueDetected(rightSensorColor);
//            }
//            robot.stopRobot();
//            robot.sleep(250);
//
//            telemetry.addData("Going: ", "Back");
//            moveBackward();
//            robot.sleep(BACKWARD_ADJUST_MILLIS);
//            robot.stopRobot();
//        }
//        else if (!isColorDetectedByLeftSensor && isColorDetectedByRightSensor) {
//            telemetry.addData("Turning: ", "Right");
//            turnRight(TURN_SPEED_RIGHT);
//            boolean isLeftSensorDetectColor = false;
//            while (!isLeftSensorDetectColor) {
//                ColorValue leftSensorColor = getColor(robot.colorSensorName);
//                isLeftSensorDetectColor = isRedOrBlueDetected(leftSensorColor);
//            }
            robot.stopRobot();
            robot.sleep(250);

            telemetry.addData("Going: ", "Back");
            moveBackward();
            robot.sleep(BACKWARD_ADJUST_MILLIS);
            robot.stopRobot();
       // }
        //telemetry.sendTelemetry();

        return false;
    }

    /*
    private boolean loopToAdjust() throws InterruptedException {
        if (isRightMotorStopped && isLeftMotorStopped) {
            return true;
        }

        ColorValue rightColor = getColor(robot.colorSensorBackRight);
        telemetry.addData("Right Color: ", rightColor.name());
        boolean isColorDetectedByRightSensor = isRedOrBlueDetected(rightColor);
        //If color sensor is right side, then stop right motor
        if (isColorDetectedByRightSensor) {
            pauseMovement(); // turn off both motors to stop the momentum
            stopRight(); // turn robot to right
            isRightMotorStopped = true;
            telemetry.addData("Motor Right", "stopped");
            return false;
        }

        ColorValue leftColor = getColor(robot.colorSensorBackLeft);
        telemetry.addData("Left Color: ", rightColor.name());
        boolean isColorDetectedByLeftSensor = isRedOrBlueDetected(leftColor);
        //If color sensor is left side, then stop left motor
        if (isColorDetectedByLeftSensor) {
            pauseMovement(); // turn off both motors to stop the momentum
            stopLeft();
            isLeftMotorStopped = true;
            telemetry.addData("Motor Left", "stopped");
            return false;
        }

        // Move both wheels until one of the color sensor detects colors
        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
            pauseMovement(); // stop robot moving to slow down the momentum
            moveForward();
            telemetry.addData("Motor Both", "move forward");
        }

        // end state - when both motors have stopped, return true to indicate we are done.
        return isLeftMotorStopped && isRightMotorStopped;
    }
    */
}

