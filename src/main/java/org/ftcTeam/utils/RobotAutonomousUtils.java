package org.ftcTeam.utils;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;
import org.ftcbootstrap.components.utils.TelemetryUtil;

import java.util.Locale;

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


    // Move the robot to adjust into box holder
    public static void strafAdjustLeft(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try{
            motorL.setPower(.4 * (1));
            motorR.setPower(.4 * (1));
            motorBL.setPower(.4 * (-1));
            motorBR.setPower(.4 * (-1));
            Thread.sleep(550);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pauseMotor(motorR, motorL, motorBL, motorBR);
    }

    public static void offFromPlatform(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {
        try{
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

    public static void continuousStrafLeft(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {

            motorL.setPower(.25 * (1));
            motorR.setPower(.25 * (1));
            motorBL.setPower(.25 * (-1));
            motorBR.setPower(.25 * (-1));

    }

    public static void continuousStrafRight(DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL) {

        motorL.setPower(-0.40);
        motorR.setPower(-0.40);
        motorBL.setPower(0.40);
        motorBR.setPower(0.40);

    }


    public static void rotateMotor180(double intialAngle, BNO055IMU imu, DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL, TelemetryUtil tUtil) {

        motorR.setPower(.4);
        motorL.setPower(.4);
        motorBR.setPower(.4);
        motorBL.setPower(.4);
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        while ((intialAngle + 180) > angle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);

            tUtil.addData("Heading Angle", angle );
            tUtil.sendTelemetry();
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
