package org.ftc8702.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcbootstrap.demos.demobot.DemoBot;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;
import org.ftc8702.opmodes.configurations.test.Team8702TestAuto;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link DemoBot}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;}
 * <p/>
 * Summary:
 * <p/>
 * Opmode demonstrates running a motor from and encoder
 */


@Autonomous(name = "UltronEncoderTest", group = "Sensors Test")
public class UltronEncoderTest extends ActiveOpMode {

    private Team8702TestAuto robot;

    private MotorToEncoder motorToEncoder;
    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = Team8702TestAuto.newConfig(hardwareMap, getTelemetryUtil());

        motorToEncoder = new MotorToEncoder(  this, robot.motorFR);
        motorToEncoder.setName("motorFR");

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        step = 1;
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

                //full power , forward for 8880
                targetReached = motorToEncoder.runToTarget(1, 9600,
                        MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                if (targetReached) {
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
