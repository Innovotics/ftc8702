package org.ftc8702.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;

/**
 * Created by dkim on 1/11/17.
 */

public class EncoderBasedAutonomousUtil {


    public static boolean pauseMotorEncoder(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) {

        motorR.stop();
        motorL.stop();
        motorBR.stop();
        motorBL.stop();
        return true;
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
                    motorR.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    motorL.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    motorBR.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    motorBL.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            return true;

        } else {
            return false;
        }
    }


    public static boolean strafLeft(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) throws InterruptedException{
        //values
        int rotateMotor = 1140;

        boolean isFRDone = motorR.runToTarget(1.0, rotateMotor * (1), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        boolean isFLDone = motorL.runToTarget(1.0, rotateMotor * (1), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        boolean isBRDone = motorBR.runToTarget(1.0, rotateMotor * (-1), MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        boolean isBLDone = motorBL.runToTarget(1.0, rotateMotor * (-1), MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);

        if (isFRDone && isFLDone && isBRDone && isBLDone) {
            motorR.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorL.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBR.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBL.runToTarget(.5, rotateMotor * (0), MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            return true;
        }
        else {
            return false;
        }
    }
}


