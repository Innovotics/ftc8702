package org.ftcTeam.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;

/**
 * Created by dkim on 1/11/17.
 */

public class EncoderBasedAutonomousUtil {


    public static void pauseMotorEncoder(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) {
        boolean targetReached = false;

        motorR.stop();
        motorL.stop();
        motorBR.stop();
        motorBL.stop();
    }

    public static boolean rotateMotor180(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) throws InterruptedException {
        //values
        int rotateMotor = 1140;
        boolean targetReached = false;

        targetReached =  motorR.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
        motorL.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
        motorBR.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
        motorBL.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

        if (targetReached) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean strafeLeft(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) throws InterruptedException {
        //values
        int strafeRobot = 1140;
        boolean targetReached = false;

        targetReached = motorR.runToTarget(.5, strafeRobot * (1), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
                motorL.runToTarget(.5, strafeRobot * (1), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
                motorBR.runToTarget(.5, strafeRobot * (-1), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER) &&
                motorBL.runToTarget(.5, strafeRobot * (-1), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

        if (targetReached) {
            return true;

        } else {

            return false;
        }
    }
}


