package org.ftcTeam.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702Test;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;
import org.ftcbootstrap.components.utils.TelemetryUtil;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Note: This Exercise assumes that you have used your Robot Controller App to "scan" your hardware and
 * saved the configuration named: "DemoBot" and creating a class by the same name: {@link Team8702Test}.
 * <p/>
 * Note:  It is assumed that the proper registry is used for this set of demos. To confirm please
 * search for "Enter your custom registry here"  in  {@link org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;}
 * <p/>
 * Summary:
 * <p/>
 * Opmode demonstrates running a motor from and encoder
 */


@Autonomous
public class EncoderMotorOpMode extends ActiveOpMode {

    private Team8702Test robot;

    private MotorToEncoder motorToEncoder;
    private MotorToEncoder motorToEncoderL;
    private int step;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        //specify configuration name save from scan operation
        robot = Team8702Test.newConfig(hardwareMap, getTelemetryUtil());
        motorToEncoder = new MotorToEncoder(  this, robot.motorR);
        motorToEncoderL = new MotorToEncoder( this, robot.motorL);
        motorToEncoder.setName("motor1");
        motorToEncoder.setName("motor2");

        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();

    }

    @Override
    protected void onStart() throws InterruptedException  {
        super.onStart();
        step = 2;
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
           /* case 1:

                //full power , forward for 1000, 3ft in length
                targetReached = motorToEncoder.runToTarget(1,1850,
                        MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                targetReached = motorToEncoderL.runToTarget(1, 1850,
                        MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                if (targetReached) {
                    step++;
                }
                break;
*/
           case 2:
                //  90 degree turn Left
                targetReached = motorToEncoder.runToTarget(0.57, 1475,
                        MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
                if (targetReached) {
                    step++;
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
