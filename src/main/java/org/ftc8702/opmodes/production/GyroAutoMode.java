package org.ftc8702.opmodes.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.configurations.production.Team8702ProdAuto;
import org.ftc8702.utilities.OrientationUtils;
import org.ftc8702.utilities.TelemetryUtil;

public class GyroAutoMode {

    private double defaultPower = 0.2;

    private Team8702ProdAuto robot;
    //private Telemetry telemetry;
    private TelemetryUtil telemetryUtil;
    Orientation angles;
    double currentYawAngle;
    double currentPitchAngle;
    double currentRollAngle;

    public GyroAutoMode(Team8702ProdAuto robot, TelemetryUtil telemetryUtil) {
        this.robot = robot;
        this.telemetryUtil = telemetryUtil;

    }

    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";

        robot.imu.initialize(parameters);
        readAngles();
        //angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }

    void printReport() {
        telemetryUtil.addData("heading", OrientationUtils.formatAngle(angles.angleUnit, angles.firstAngle));
        telemetryUtil.addData("roll", OrientationUtils.formatAngle(angles.angleUnit, angles.secondAngle));
        telemetryUtil.addData("pitch", OrientationUtils.formatAngle(angles.angleUnit, angles.thirdAngle));
        telemetryUtil.sendTelemetry();
    }

    public boolean goLeftAngleCondition(double angle){
        return goLeftAngleCondition(angle, defaultPower);
    }

    public boolean goLeftAngleCondition(double angle, double power){
        boolean isFinished = false;
        while (!isFinished)
        {
            readAngles(); // go left => angle increases
            currentYawAngle = getAngles().firstAngle;
            telemetryUtil.addData("Current Angle", currentYawAngle);
            telemetryUtil.sendTelemetry();

            if(currentYawAngle > angle) {
                telemetryUtil.addData("stopping robot, reached=", currentYawAngle);
                robot.stopRobot();
                return true;
            }

            robot.forwardRobot(power);
        }
        return isFinished;
    }

    public boolean goRightToAngleDegree(double angle) {
        return goRightToAngleDegree(angle, defaultPower);
    }

    public boolean goRightToAngleDegree(double angle, double power){
        boolean isFinished = false;
        while (!isFinished) {
            readAngles(); // go right => angle decreases
            currentYawAngle = getAngles().firstAngle;
            telemetryUtil.addData("Current Angle", currentYawAngle);
            telemetryUtil.sendTelemetry();

            if (currentYawAngle < angle) {
                robot.stopRobot();
                return true;
            }

            robot.forwardRobot(power);
        }
        return isFinished;
    }

    public boolean isRollPitchChange( double rollLimit, double pitchLimit) {
        readAngles();

//        if (currentRollAngle < 0) {
//            currentRollAngle = currentRollAngle * (-1);
//        }
//
//        if (currentPitchAngle < 0) {
//            currentPitchAngle = currentPitchAngle * (-1);
//        }
//
        return (Math.abs(currentPitchAngle) > pitchLimit || Math.abs(currentRollAngle) > rollLimit);
    }

    boolean testElevationChange( double rollLimit, double pitchLimit) {
        readAngles();

        if (currentRollAngle < 0) {
            currentRollAngle = currentRollAngle * (-1);
        }

        if (currentPitchAngle < 0) {
            currentPitchAngle = currentPitchAngle * (-1);
        }

        if(currentPitchAngle > pitchLimit || currentRollAngle > rollLimit) {

            robot.forwardRobot(0.77);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            robot.stopRobot();

            return true;
        }

        robot.forwardRobot(0.4);
        return false;
    }


    public Orientation getAngles() {
        return angles;
    }

    public void readAngles(){
        angles = robot.getGyroSensor().getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        currentYawAngle = angles.firstAngle;
        currentPitchAngle = angles.thirdAngle;
        currentRollAngle = angles.secondAngle;
        telemetryUtil.addData("yaw", OrientationUtils.formatAngle(angles.angleUnit, angles.firstAngle));
        telemetryUtil.addData("roll", OrientationUtils.formatAngle(angles.angleUnit, angles.secondAngle));
        telemetryUtil.addData("pitch", OrientationUtils.formatAngle(angles.angleUnit, angles.thirdAngle));
        telemetryUtil.sendTelemetry();

    }
}
