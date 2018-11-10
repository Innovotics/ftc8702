package org.ftc8702.opmodes.production;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utils.ColorValue;

import static org.ftc8702.utils.ColorUtil.getColor;
import static org.ftc8702.utils.ColorUtil.isRedOrBlueDetected;

public class ColorSensorAdjustmentAutoMode {
    private static final double FORWARD_SPEED = 0.15;
    private static final double TURN_SPEED_RIGHT = 0.33;
    private static final double TURN_SPEED_LEFT = 0.3;
    private static final double TURN_BACKWORD_SPEED_RIGHT = -0.27;
    private static final double TURN_BACKWORD_SPEED_LEFT = -0.23;

    private Team8702ProdAuto robot;
    private Telemetry telemetry;

    private boolean isRightMotorStopped = false;
    private boolean isLeftMotorStopped = false;

    public ColorSensorAdjustmentAutoMode(Team8702ProdAuto robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
    }

    public void init() {
        telemetry.addData("Init", getClass().getSimpleName() + " initialized.");
    }

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

    private void pauseMovement() {
        robot.motorL.setPower(0.0);
        robot.motorR.setPower(0.0);
    }

    public boolean startAdjustment() throws InterruptedException {
        boolean isCompleted = false;
        while (!isCompleted) {
            isCompleted = loopToAdjust();
        }
        return isCompleted;
    }

    private boolean loopToAdjust() throws InterruptedException {
        ColorValue rightColor = getColor(robot.colorSensorBackRight);
        telemetry.addData("Right Color: ", rightColor.name());
        ColorValue leftColor = getColor(robot.colorSensorBackLeft);
        telemetry.addData("Left Color: ", rightColor.name());

        // get color readings from both left and right sensors
        boolean isColorDetectedByRightSensor = isRedOrBlueDetected(rightColor);
        boolean isColorDetectedByLeftSensor = isRedOrBlueDetected(leftColor);

        // Move both wheels until one of the color sensor detects colors
        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
            pauseMovement(); // stop robot moving to slow down the momentum
            moveForward();
            telemetry.addData("Motor Both", "move forward");
        }

        //If color sensor is right side, then stop right motor
        if (isColorDetectedByRightSensor) {
            pauseMovement(); // turn off both motors to stop the momentum
            stopRight();
            isRightMotorStopped = true;
            telemetry.addData("Motor Right", "stopped");
        }
        //If color sensor is left side, then stop left motor
        if (isColorDetectedByLeftSensor) {
            pauseMovement(); // turn off both motors to stop the momentum
            stopLeft();
            isLeftMotorStopped = true;
            telemetry.addData("Motor Left", "stopped");
        }
        telemetry.update();

        // end state - when both motors have stopped, return true to indicate we are done.
        return isLeftMotorStopped && isRightMotorStopped;
    }

}
