package org.ftc8702.opmodes.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.utils.test.RobotTwoWheelsAutonomousUtil;
import org.ftcbootstrap.ActiveOpMode;
import org.ftc8702.opmodes.configurations.test.Team8702TestAuto;


/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link }.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;}
 * <p/>
 * Summary:
 * <p/>
 * Opmode demonstrates running a motor from and encoder
 */


@Autonomous(name = "UltronGyroRotationTest", group = "Sensors Test")
public class UltronGyroRotationTest extends ActiveOpMode {

    private Team8702TestAuto robot;

    private int step;

    Orientation angles;
    Acceleration gravity;
    double initialAngle;
    double currentAngle;
    double targetAngle;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = Team8702TestAuto.newConfig(hardwareMap, getTelemetryUtil());

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        robot.imu.initialize(parameters);

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        step = 1;

        //set reference
        robot.imu = hardwareMap.get(BNO055IMU.class, "imu");
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *  @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        getTelemetryUtil().addData("step: " + step , "current");

        boolean targetReached = false;

        switch (step) {
            case 1:
                //Take Reference Angle
                if(targetReached == false) {
                    initialAngle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
                    targetReached = true;
                }
                if (targetReached) {
                    targetReached = false;
                    step++;
                }
                break;
            case 2:
                //Rotate According to Reference Angle
                if(targetReached == false) {
                    RobotTwoWheelsAutonomousUtil.rotateMotor90(initialAngle, robot.imu,robot.motorR, robot.motorL);
                    targetReached = true;
                }

                if(targetReached) {
                    targetReached = false;
                    step = 99;
                }
                break;
            case 99:
                getTelemetryUtil().addData("step" + step + " Opmode Status", "Robot Stopped.  Kill switch activated");
                setOperationsCompleted();
                break;

            default:
                setOperationsCompleted();
                break;

        }


        //send any telemetry that may have been added in the above operations
        getTelemetryUtil().sendTelemetry();


    }

    }
