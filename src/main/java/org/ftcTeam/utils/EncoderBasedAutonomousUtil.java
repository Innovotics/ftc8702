package org.ftcTeam.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;

/**
 * Created by dkim on 1/11/17.
 */

public class EncoderBasedAutonomousUtil {



    public static void pauseMotorEncoder(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) {
            motorR.stop();
            motorL.stop();
            motorBR.stop();
            motorBL.stop();
    }

    public static void rotateMotor180(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) throws InterruptedException{
        //values
        int rotateMotor = 1140;

        motorR.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorL.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorBR.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorBL.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public static boolean strafLeft(MotorToEncoder motorR, MotorToEncoder motorL, MotorToEncoder motorBR, MotorToEncoder motorBL) throws InterruptedException{
        //values
        int rotateMotor = 1140;

        boolean isFRDone = motorR.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        boolean isFLDone = motorL.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        boolean isBRDone = motorBR.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        boolean isBLDone = motorBL.runToTarget(.5, rotateMotor, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);

        if (isFRDone && isFLDone && isBRDone && isBLDone) {
            return true;
        }
        else {
            return false;
        }
    }
}
