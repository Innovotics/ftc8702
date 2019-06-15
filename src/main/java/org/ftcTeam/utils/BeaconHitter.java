package org.ftcTeam.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

import ftcbootstrap.components.ColorSensorComponent;
import ftcbootstrap.components.operations.motors.MotorToEncoder;
import ftcbootstrap.components.utils.MotorDirection;
import ftcbootstrap.components.utils.TelemetryUtil;

/**
 * Created by tylerkim on 12/21/16.
 */

public class BeaconHitter {

    int step = 1;
    ColorValue rainbowValue;
    TelemetryUtil teleUtil;

    ColorValue teamColor;

    public BeaconHitter(TelemetryUtil tele, ColorValue tC) {
        teleUtil = tele;
        teamColor = tC;


    }

    public boolean beaconHitter(ColorSensorComponent colorSensorComponent, MotorToEncoder motorToEncoderR, MotorToEncoder motorToEncoderL, ColorValue teamColor) throws InterruptedException {
        boolean targetReached = false;
        teleUtil.addData("Current Beacon Hitter step: ", step);

        switch (step) {
            case 1:
                rainbowValue = ColorUtils.getColor(colorSensorComponent);
                teleUtil.addData("Color: ", rainbowValue.toString());
                step++;
                break;
            case 2:
//move method
                targetReached = moveForward(rainbowValue, teamColor, motorToEncoderR, motorToEncoderL);

                if (targetReached) {
                    step++;
                }
                break;
            case 3:
                //backward
                targetReached = moveBackward(rainbowValue, teamColor, motorToEncoderR, motorToEncoderL);

                if (targetReached) {
                    step = 99;
                }
                break;
            case 99:
                return true;
        }
        return false;
    }

    private boolean moveForward(ColorValue color, ColorValue teamColor, MotorToEncoder motorToEncoderR, MotorToEncoder motorToEncoderL) throws InterruptedException {
        boolean targetReached = false;
        if (color == ColorValue.ZILCH) {
            return true;
        } else if (color == teamColor) {
            // move right motor
            targetReached = motorToEncoderL.runToTarget(0.1, 350,
                    MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);

        } else if (color != teamColor) {
            targetReached = motorToEncoderR.runToTarget(0.1, 350,
                    MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        }
        return targetReached;
    }

    private boolean moveBackward(ColorValue color, ColorValue teamColor, MotorToEncoder motorToEncoderR, MotorToEncoder motorToEncoderL) throws InterruptedException {
        boolean targetReached = false;
        if (color == teamColor) {
            targetReached = motorToEncoderL.runToTarget(-0.1, 350,
                    MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        } else if (color != teamColor) {
            targetReached = motorToEncoderR.runToTarget(-0.1, 350,
                    MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        } else if (color == ColorValue.ZILCH) {
            return true;
        }
        return targetReached;
    }

}
