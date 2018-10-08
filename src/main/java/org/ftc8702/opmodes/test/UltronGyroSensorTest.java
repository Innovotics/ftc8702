package org.ftc8702.opmodes.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.opmodes.configurations.test.Team8702TestProd;
import org.ftcTeam.utils.RobotAutonomousUtils;
import org.ftcbootstrap.ActiveOpMode;


import java.util.Locale;


abstract class UltronGyroSensorTest extends ActiveOpMode {
    //Setting Target Reached value.
    //If it is set to true then State moves to next step
    //Starting of each step, it will set to false so the the can run until
    // robot set to true
    boolean targetReached = false;

    //Declare the MotorToEncoder
    private Team8702TestProd robot;

    Orientation angles;
    Acceleration gravity;

    double initialAngle;
    double currentAngle;
    double targetAngle;

    @Override
    protected void onInit() {

        robot = Team8702TestProd.newConfig(hardwareMap, getTelemetryUtil());

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();


        robot.gyroSensor.initialize(parameters);
        angles =  robot.gyroSensor.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        initialAngle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        getTelemetryUtil().addData("Heading Angle", formatAngle(angles.angleUnit, angles.firstAngle) );
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {


        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {

    }

    private void startTheRobot() {
        // TODO logic later
        targetReached = true;
    }

    //Format method
    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}
