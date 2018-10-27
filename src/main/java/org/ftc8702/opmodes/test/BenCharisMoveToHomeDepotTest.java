package org.ftc8702.opmodes.test;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftc8702.opmodes.configurations.test.BenCharisConfig;
import org.ftc8702.utils.ColorValue;
import org.ftcbootstrap.ActiveOpMode;

public class BenCharisMoveToHomeDepotTest extends ActiveOpMode {

    private BenCharisConfig robotConfig;

    @Override
    protected void onInit() {
        // do stuff at initialization stage
        robotConfig = BenCharisConfig.newConfig(hardwareMap, getTelemetryUtil());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    protected void moveForward() {
        robotConfig.motorL.setPower(0.1);
        robotConfig.motorR.setPower(0.1);
    }

    protected void stopRight() {
        robotConfig.motorR.setPower(0.0);
    }

    protected void stopLeft() {
        robotConfig.motorL.setPower(0.0);
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        // 1. check if blue or red is detected by either or both color sensors
        ColorValue rightColor = getColor(robotConfig.colorSensorBackRight);
        getTelemetryUtil().addData("Right Color: ", rightColor.name());
        ColorValue leftColor = getColor(robotConfig.colorSensorBackLeft);
        getTelemetryUtil().addData("Left Color: ", rightColor.name());

        boolean isColorDetectedByRightSensor = (rightColor == ColorValue.BLUE || rightColor == ColorValue.RED);
        boolean isColorDetectedByLeftSensor = (leftColor == ColorValue.BLUE || leftColor == ColorValue.RED);

        //Move both wheels until one of the color sensor detects
        if (!isColorDetectedByRightSensor && !isColorDetectedByLeftSensor) {
            moveForward();
            getTelemetryUtil().addData("Motor Both", "move forward");
        }

        //If color sensor is right side, then stop right motor
        if (isColorDetectedByRightSensor) {
            stopRight();
            getTelemetryUtil().addData("Motor Right", "stopped");
        }
        //If color sensor is left side, then stop left motor
        if (isColorDetectedByLeftSensor) {
            stopLeft();
            getTelemetryUtil().addData("Motor Left", "stopped");
        }

        getTelemetryUtil().sendTelemetry();
        // Move on to next Phase
        // Play Uptown Funk By Bruno Mars
    }

    /*
    public ColorValue getColor() {
        //Helping fix the red color sense correctly, 20 is to offset the color sensor bias toward red.
        int FixRed = 20;

        //Determine which is color to call
        if ( Math.abs(robotConfig.colorSensorRight.red() - robotConfig.colorSensorRight.blue()) > FixRed
                && Math.abs(robotConfig.colorSensorRight.red() -  robotConfig.colorSensorRight.green()) > FixRed) {
            return ColorValue.RED;
        }

        if (robotConfig.colorSensorRight.blue() > robotConfig.colorSensorRight.red() && robotConfig.colorSensorRight.blue() > robotConfig.colorSensorRight.green()) {
            return ColorValue.BLUE;
        }

        if (robotConfig.colorSensorRight.green() > robotConfig.colorSensorRight.red() && robotConfig.colorSensorRight.green() > robotConfig.colorSensorRight.blue()) {
            return ColorValue.GREEN;
        }

        return ColorValue.ZILCH;
    }
    */

    //Helping fix the red color sense correctly, 20 is to offset the color sensor bias toward red.
    private static int RED_COLOR_OFFSET = 20;

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
