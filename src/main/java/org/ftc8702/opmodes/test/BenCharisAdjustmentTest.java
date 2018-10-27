package org.ftc8702.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftc8702.opmodes.configurations.test.BenCharisConfig;
import org.ftc8702.utils.ColorValue;
import org.ftcbootstrap.ActiveOpMode;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Test: BenCharisAdjustment", group = "Test")
public class BenCharisAdjustmentTest extends ActiveOpMode {
    private static final double FORWARD_SPEED = 0.15;
    private static final double TURN_SPEED_RIGHT = 0.3;
    private static final double TURN_SPEED_LEFT = 0.3;
    private static final double TURN_BACKWORD_SPEED_RIGHT = -0.1;
    private static final double TURN_BACKWORD_SPEED_LEFT = -0.1;
    private static final long SLEEP_TIME_MS = 250;

    //Helping fix the red color sense correctly, 20 is to offset the color sensor bias toward red.
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
        robotConfig.motorL.setPower(isLeftMotorStopped ? 0.0 : TURN_SPEED_LEFT);
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

    protected void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME_MS);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        ColorValue rightColor = getColor(robotConfig.colorSensorBackRight);
        getTelemetryUtil().addData("Right Color: ", rightColor.name());
        ColorValue leftColor = getColor(robotConfig.colorSensorBackLeft);
        getTelemetryUtil().addData("Left Color: ", rightColor.name());

        // get color readings from both left and right sensors
        boolean isColorDetectedByRightSensor = (rightColor == ColorValue.BLUE || rightColor == ColorValue.RED);
        boolean isColorDetectedByLeftSensor = (leftColor == ColorValue.BLUE || leftColor == ColorValue.RED);

        // Move both wheels until one of the color sensor detects
        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
            pauseMovement(); // stop robot moving to slow down the momentum
            moveForward();
            getTelemetryUtil().addData("Motor Both", "move forward");
        }

        //If color sensor is right side, then stop right motor
        if (isColorDetectedByRightSensor) {
            pauseMovement();
            stopRight();
            isRightMotorStopped = true;
            getTelemetryUtil().addData("Motor Right", "stopped");
        }
        //If color sensor is left side, then stop left motor
        if (isColorDetectedByLeftSensor) {
            pauseMovement();
            stopLeft();
            isLeftMotorStopped = true;
            getTelemetryUtil().addData("Motor Left", "stopped");
        }

        getTelemetryUtil().sendTelemetry();
        //sleep();
        // Move on to next Phase
        // Play Uptown Funk By Bruno Mars
    }

    public ColorValue getColor(ColorSensor colorSensor) {
        int red = colorSensor.red();
        int blue = colorSensor.blue();
        int green = colorSensor.green();


        //Determine which is color to call
        if ( red - blue > RED_COLOR_OFFSET && red - green > RED_COLOR_OFFSET) {
            return ColorValue.RED;
        }

        if (blue > red && blue > green) {
            return ColorValue.BLUE;
        }

        return ColorValue.ZILCH;
    }
}
