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
        //this.telemetry = telemetry;

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
        //composeTelemetry();
    }

//
//
//    void composeTelemetry() {
//
//        // At the beginning of each telemetry update, grab a bunch of data
//        // from the IMU that we will then display in separate lines.
//        telemetry.addAction(new Runnable() {
//            @Override
//            public void run() {
//                // Acquiring the angles is relatively expensive; we don't want
//                // to do that in each of the three items that need that info, as that's
//                // three times the necessary expense.
//                angles = robot.getGyroSensor().getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//            }
//        });
//
//        telemetry.addLine()
//                .addData("status", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return robot.getGyroSensor().getSystemStatus().toShortString();
//                    }
//                })
//                .addData("calib", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return robot.getGyroSensor().getCalibrationStatus().toString();
//                    }
//                });
//
//        telemetry.addLine()
//                .addData("heading", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return OrientationUtils.formatAngle(angles.angleUnit, angles.firstAngle);
//                    }
//                })
//                .addData("roll", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return OrientationUtils.formatAngle(angles.angleUnit, angles.secondAngle);
//                    }
//                })
//                .addData("pitch", new Func<String>() {
//                    @Override
//                    public String value() {
//                        return OrientationUtils.formatAngle(angles.angleUnit, angles.thirdAngle);
//                    }
//                });
//
//    }

    void printReport() {
        telemetryUtil.addData("heading", OrientationUtils.formatAngle(angles.angleUnit, angles.firstAngle));
        telemetryUtil.addData("roll", OrientationUtils.formatAngle(angles.angleUnit, angles.secondAngle));
        telemetryUtil.addData("pitch", OrientationUtils.formatAngle(angles.angleUnit, angles.thirdAngle));
        telemetryUtil.sendTelemetry();
    }

    public boolean runWithAngleCondition(double angle){
        readAngles();
        //currentYawAngle = getAngles().firstAngle;
        //telemetryUtil.addData("Current Angle", currentYawAngle);
        //telemetryUtil.sendTelemetry();

        if(Math.abs(currentYawAngle) > angle) {
            robot.stopRobot();
            return true;
        }

        robot.turnLeft(.2);
        return false;

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

            robot.forwardRobot(0.7);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            robot.stopRobot();

            return true;
        }

        robot.forwardRobot(0.2);
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
