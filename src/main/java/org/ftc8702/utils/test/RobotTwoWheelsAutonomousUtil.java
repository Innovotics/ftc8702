package org.ftc8702.utils.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by dkim on 1/11/17.
 */

public class RobotTwoWheelsAutonomousUtil {

    int rotateMotor = 1140;

    public static void pauseMotor(DcMotor motorR, DcMotor motorL) {
        try {
            motorR.setPower(0);
            motorL.setPower(0);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void rotateMotor180(double initialAngle, BNO055IMU imu, DcMotor motorR, DcMotor motorL, DcMotor motorBR, DcMotor motorBL, TelemetryUtil tUtil) {
//
//        motorR.setPower(.3);
//        motorL.setPower(.3);
//        motorBR.setPower(.3);
//        motorBL.setPower(.3);
//        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        double angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
//        while ((intialAngle + 177) > angle) {
//            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//            angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
//
//            tUtil.addData("Heading Angle", angle);
//            tUtil.sendTelemetry();
//        }
//
//        pauseMotor(motorR, motorL);
//
//    }

    public static void rotateMotor90(double initialAngle, BNO055IMU imu, DcMotor motorR, DcMotor motorL) {
        motorR.setPower(.5 * (1));
        motorL.setPower(.5 * (1));
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);

        while ((initialAngle + 88) > angle) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            angle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        }

        pauseMotor(motorR, motorL);
    }

}
