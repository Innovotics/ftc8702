package org.ftcTeam.utils;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftcbootstrap.components.utils.TelemetryUtil;

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

    public static void rotateMotor(DcMotor motorR, DcMotor motorL, DcMotor motorBL, DcMotor motorBR) {
        try {
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


    public static void offFromPlatformBlue(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try {
            motorL.setPower(.4 * (1));
            motorR.setPower(.4 * (1));
            motorBL.setPower(.4 * (-1));
            motorBR.setPower(.4 * (-1));
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);
    }

    public static void offFromPlatformRed(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try {
            motorL.setPower(.4 * (-1));
            motorR.setPower(.4 * (-1));
            motorBL.setPower(.4 * (1));
            motorBR.setPower(.4 * (1));
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);
    }

    public static void continuousStrafLeft(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {

        motorL.setPower(.4 * (1));
        motorR.setPower(.4 * (1));
        motorBL.setPower(.4 * (-1));
        motorBR.setPower(.4 * (-1));

    }

    public static void continuousStrafRight(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {

        motorL.setPower(-0.40);
        motorR.setPower(-0.40);
        motorBL.setPower(0.40);
        motorBR.setPower(0.40);

    }

    public static void adjustStrafRight(int location, DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {

        motorL.setPower(-0.4);
        motorR.setPower(-0.4);
        motorBL.setPower(0.4);
        motorBR.setPower(0.4);

        pauseMotor(motorR, motorL, motorBL, motorBR);

    }

    // Move the robot to adjust into box holder
    public static void strafAdjustLeft(int location, DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {

        motorL.setPower(.4 * (1));
        motorR.setPower(.4 * (1));
        motorBL.setPower(.4 * (-1));
        motorBR.setPower(.4 * (-1));

        pauseMotor(motorR, motorL, motorBL, motorBR);
    }

    public static void rotateMotor180(double intialAngle, BNO055IMU imu, DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL, TelemetryUtil tUtil) {

        motorR.setPower(.3);
        motorL.setPower(.3);
        motorBR.setPower(.3);
        motorBL.setPower(.3);
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        while ((intialAngle + 177) > angle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);

            tUtil.addData("Heading Angle", angle);
            tUtil.sendTelemetry();
        }

        pauseMotor(motorR, motorL, motorBL, motorBR);

    }

    public static void rotateMotor90(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try {
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

    public static void pushGlyph(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try {
            motorR.setPower(.4 * (-1));
            motorL.setPower(.4 * (1));
            motorBR.setPower(.4 * (-1));
            motorBL.setPower(.4 * (1));
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);
    }

    public static void moveBackToPark(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try {
            motorR.setPower(.5 * (1));
            motorL.setPower(.5 * (-1));
            motorBR.setPower(.5 * (1));
            motorBL.setPower(.5 * (-1));
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);
    }
}
