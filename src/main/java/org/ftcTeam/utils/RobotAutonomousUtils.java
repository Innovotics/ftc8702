package org.ftcTeam.utils;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;

/**
 * Created by dkim on 1/11/17.
 */

public class RobotAutonomousUtils {

    int rotateMotor = 1140;

    public static void pauseMotor(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try {
            motorR.setPower(0);
            motorL.setPower(0);
            motorBL.setPower(0);
            motorBR.setPower(0);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pauseMotor(MotorToEncoder motorR, MotorToEncoder motorL) {
        try {
            motorR.stop();
            motorL.stop();
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void rotateMotor(DcMotor motorR, DcMotor motorL, DcMotor motorBL, DcMotor motorBR) {
        try{
            motorR.setPower(.5 * (1));
            motorL.setPower(.5 * (1));
            motorBR.setPower(.5 * (1));
            motorBL.setPower(.5 * (1));
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);

    }

    public static void strafLeft(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try{
            motorL.setPower(.5 * (1));
            motorR.setPower(.5 * (1));
            motorBL.setPower(.5 * (-1));
            motorBR.setPower(.5 * (-1));
            Thread.sleep(2000);
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);

    }

    public static void rotateMotor180(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try{
            motorR.setPower(.5 * (1));
            motorL.setPower(.5 * (1));
            motorBR.setPower(.5 * (1));
            motorBL.setPower(.5 * (1));
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);

    }
    public static void rotateMotor90(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try{
            motorR.setPower(.5 * (1));
            motorL.setPower(.5 * (1));
            motorBR.setPower(.5 * (1));
            motorBL.setPower(.5 * (1));
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);

    }
}
