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
    private Telemetry telemetry;
    Orientation angles;

    public GyroAutoMode(Team8702ProdAuto robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;

    }

    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";

        robot.imu.initialize(parameters);
        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        composeTelemetry();
    }

    public boolean gyroSensorTurnerState(double angle) {
        double currentAngle;
        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        angles = robot.getGyroSensor().getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        robot.turnLeft(-0.2);
        boolean isCompleted = false;
        while (!isCompleted) {
            currentAngle = angles.firstAngle;
            telemetry.addData("currentAngle", currentAngle);
            composeTelemetry();

            if (currentAngle < 0) {
                currentAngle = currentAngle * (-1);
            }

            if (currentAngle > angle) {
                robot.stopRobot();
                isCompleted = true;
            }
            telemetry.update();
        }
        return true;

    }


    void composeTelemetry() {

        // At the beginning of each telemetry update, grab a bunch of data
        // from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() {
            @Override
            public void run() {
                // Acquiring the angles is relatively expensive; we don't want
                // to do that in each of the three items that need that info, as that's
                // three times the necessary expense.
                angles = robot.getGyroSensor().getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override
                    public String value() {
                        return robot.getGyroSensor().getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override
                    public String value() {
                        return robot.getGyroSensor().getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override
                    public String value() {
                        return OrientationUtils.formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override
                    public String value() {
                        return OrientationUtils.formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override
                    public String value() {
                        return OrientationUtils.formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

    }

    public Orientation getAngles() {
        return angles;
    }
}
