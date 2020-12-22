package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.configurations.production.OdometerRobotConfiguration;

import java.util.Locale;

import ftcbootstrap.ActiveOpMode;

@Autonomous(name = "PIDTesting", group = "Ops")
public class pidAndOdometerTesting extends ActiveOpMode {

    private OdometerRobotConfiguration odometerRobotConfiguration;
    private MecanumWheelDriveTrain driveTrain;

    @Override
    protected void onInit() {
        odometerRobotConfiguration = OdometerRobotConfiguration.newConfig(hardwareMap, getTelemetryUtil());
        driveTrain = new MecanumWheelDriveTrain(odometerRobotConfiguration.motorFL,  odometerRobotConfiguration.motorFR,  odometerRobotConfiguration.motorBL,  odometerRobotConfiguration.motorBR, telemetry, odometerRobotConfiguration.imu);
        initializeIMU();

        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        driveTrain.goForwardByInches(12,0.1);
    }

    public void initializeIMU() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        driveTrain.imu = hardwareMap.get(BNO055IMU.class, "imu");
        driveTrain.imu.initialize(parameters);

        driveTrain.imu.write8(BNO055IMU.Register.OPR_MODE,BNO055IMU.SensorMode.CONFIG.bVal & 0x0F);
        try {
            Thread.sleep(100);
        }catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
        driveTrain.imu.write8(BNO055IMU.Register.AXIS_MAP_CONFIG,BNO055IMU.SensorMode.IMU.bVal & 0x0F);
        driveTrain.imu.write8(BNO055IMU.Register.AXIS_MAP_SIGN, BNO055IMU.SensorMode.IMU.bVal & 0x0F);
        driveTrain.imu.write8(BNO055IMU.Register.OPR_MODE,BNO055IMU.SensorMode.IMU.bVal & 0x0F);
        try {
            Thread.sleep(100);
        }catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    public Orientation readAngles()
    {
        return driveTrain.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    String formatAngle(AngleUnit angleUnit, double angle)
    {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees)
    {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
