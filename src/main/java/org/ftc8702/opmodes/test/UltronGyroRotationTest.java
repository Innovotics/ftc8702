package org.ftc8702.opmodes.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.components.ImuGyroSensor;
import org.ftc8702.opmodes.configurations.test.Team8702TestAuto;
import org.ftc8702.utils.test.RobotTwoWheelsAutonomousUtil;
import org.ftcbootstrap.ActiveOpMode;


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

               robot.imu.initialize(ImuGyroSensor.getParameters());

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();

        angles =  robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        //set reference
        robot.imu = hardwareMap.get(BNO055IMU.class, "imu");
        initialAngle = AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle);
        getTelemetryUtil().addData("Angle", ImuGyroSensor.formatAngle(angles.angleUnit, angles.firstAngle));
        getTelemetryUtil().sendTelemetry();


    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws InterruptedException
     */
    @Override
    protected void activeLoop() throws InterruptedException {

        //Take Reference Angle
//        RobotTwoWheelsAutonomousUtil.rotateMotor90(initialAngle, robot.imu, robot.motorR, robot.motorL);

            telemetry.update();
            getTelemetryUtil().sendTelemetry();

        //send any telemetry that may have been added in the above operations



    }

}
