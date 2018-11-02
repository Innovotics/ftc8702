package org.ftc8702.opmodes.test;

import static org.ftc8702.utils.ColorUtil.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftc8702.configurations.test.BenCharisConfig;
import org.ftc8702.utils.ColorValue;
import org.ftcbootstrap.ActiveOpMode;

@Autonomous(name = "Test: BenCharisAdjustment", group = "Test")
public class BenCharisAdjustmentTest extends ActiveOpMode {
    private static final double FORWARD_SPEED = 0.15;
    private static final double TURN_SPEED_RIGHT = 0.33;
    private static final double TURN_SPEED_LEFT = 0.3;
    private static final double TURN_BACKWORD_SPEED_RIGHT = -0.27;
    private static final double TURN_BACKWORD_SPEED_LEFT = -0.23;

    // NOT USED - original intent for slowing down forward momentum; but it did not work well when
    // comparing to using backward speed on the opposite side of motor.  But keeping it for
    // reference.
    // private static final long SLEEP_TIME_MS = 250;

    // help to fix the red color sense correctly, 20 is to offset the color sensor bias toward red.
    private static int RED_COLOR_OFFSET = 20;

    private BenCharisConfig robotConfig;

    private boolean isRightMotorStopped = false;
    private boolean isLeftMotorStopped = false;

    @Override
    protected void onInit() {
        // do stuff at initialization stage
        robotConfig = BenCharisConfig.newConfig(hardwareMap, getTelemetryUtil());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    protected void moveForward() {
        robotConfig.motorR.setPower(isRightMotorStopped ? 0.0 : FORWARD_SPEED);
        robotConfig.motorL.setPower(isLeftMotorStopped ? 0.0 : FORWARD_SPEED);
    }

    protected void stopRight() {
        // boost the opposite side of motor when stopping on this side.  This is needed since just 1
        // motor tends to need a bit more power to move the robot.
        robotConfig.motorL.setPower(isLeftMotorStopped ? 0.0 : TURN_SPEED_LEFT);
        // set this stopping side motor to backward to offset the forward momentum a bit to back
        // to its intended stopping position.
        robotConfig.motorR.setPower(TURN_BACKWORD_SPEED_RIGHT);
    }

    protected void stopLeft() {
        robotConfig.motorL.setPower(TURN_BACKWORD_SPEED_LEFT);
        robotConfig.motorR.setPower(isRightMotorStopped ? 0.0 : TURN_SPEED_RIGHT);
    }

    protected void pauseMovement() {
        robotConfig.motorL.setPower(0.0);
        robotConfig.motorR.setPower(0.0);
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        ColorValue rightColor = getColor(robotConfig.colorSensorBackRight);
        getTelemetryUtil().addData("Right Color: ", rightColor.name());
        ColorValue leftColor = getColor(robotConfig.colorSensorBackLeft);
        getTelemetryUtil().addData("Left Color: ", rightColor.name());

        // get color readings from both left and right sensors
        boolean isColorDetectedByRightSensor = isRedOrBlueDetected(rightColor);
        boolean isColorDetectedByLeftSensor = isRedOrBlueDetected(leftColor);

        // Move both wheels until one of the color sensor detects colors
        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
            pauseMovement(); // stop robot moving to slow down the momentum
            moveForward();
            getTelemetryUtil().addData("Motor Both", "move forward");
        }

        //If color sensor is right side, then stop right motor
        if (isColorDetectedByRightSensor) {
            pauseMovement(); // turn off both motors to stop the momentum
            stopRight();
            isRightMotorStopped = true;
            getTelemetryUtil().addData("Motor Right", "stopped");
        }
        //If color sensor is left side, then stop left motor
        if (isColorDetectedByLeftSensor) {
            pauseMovement(); // turn off both motors to stop the momentum
            stopLeft();
            isLeftMotorStopped = true;
            getTelemetryUtil().addData("Motor Left", "stopped");
        }

        getTelemetryUtil().sendTelemetry();
        //sleep();
        // Move on to next Phase
        // Play Uptown Funk By Bruno Mars
    }
}
