package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.Team8702ProdAuto;
import org.ftcTeam.utils.ColorValue;
import org.ftcTeam.utils.EncoderBasedOmniWheelController;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;


@Autonomous(name = "AutoModeRed", group = "Ops")
public class AutoModeRed extends AbstractAutoMode{
    //Declare the Robot
    private Team8702ProdAuto robot;

    //Declare Motor Encoders
    private MotorToEncoder motorToEncoderFR;
    private MotorToEncoder motorToEncoderFL;
    private MotorToEncoder motorToEncoderBR;
    private MotorToEncoder motorToEncoderBL;

    //Declare Step
    private step currentStep;

    //Declare Steps
    private enum step {
        init,
        shiftRight,
        resetEncoders,
        shiftLeft,
        complete;
    }

    //Declare completion tester
    boolean targetReached = false;

    @Override
    protected void onInit() {
    super.onInit();
    //set step to init
    currentStep = step.init;

    //Declare motor to encoders
        motorToEncoderFR = new MotorToEncoder(this, robot.motorFR);
        motorToEncoderFL = new MotorToEncoder(this, robot.motorFL);
        motorToEncoderBL = new MotorToEncoder(this, robot.motorBL);
        motorToEncoderBR = new MotorToEncoder(this, robot.motorBR);

        //Set Telemetry
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
        getTelemetryUtil().sendTelemetry();
    }



    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        super.activeLoop();
        switch(currentStep) {
            case init: //Set Everything

                startRobot();

            //Test if targetReached is true
            if(targetReached) {
                currentStep = step.shiftRight;
                break;
            }

            case shiftRight: //Shift the robot right

                //Send Telemetry
                getTelemetryUtil().addData("Step: ", currentStep.toString());
            getTelemetryUtil().sendTelemetry();

                //sleep for 1 second
                sleep(1000);

                //Move all wheels to make robot move right
                targetReached = motorToEncoderFR.runToTarget(0.5, 1140, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderFL.runToTarget(0.5,1140, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderBR.runToTarget(0.5,1140, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderBL.runToTarget(0.5, 1140, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_TO_POSITION);

            //Test if targetReached is true
            if(targetReached) {
                currentStep = step.resetEncoders;
                break;
            }

            case resetEncoders:
                //Send Telemetry
                getTelemetryUtil().addData("Step: ", currentStep.toString());
                getTelemetryUtil().sendTelemetry();

                //sleep for 2 seconds
                sleep(2000);

                //Reset Encoder on all Wheels
                targetReached = motorToEncoderFR.runToTarget(0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER) &&
                        motorToEncoderFL.runToTarget(0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER) &&
                        motorToEncoderBR.runToTarget(0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER) &&
                        motorToEncoderBL.runToTarget( 0, 0, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                //Test if targetReached is true
                if(targetReached) {
                    currentStep = step.shiftLeft;
                    break;
                }

            case shiftLeft:
                //Send Telemetry
                getTelemetryUtil().addData("Step: ", currentStep.toString());
                getTelemetryUtil().sendTelemetry();

              //sleep for 2 seconds
                sleep(2000);

               //Move all wheels to make robot move left
                targetReached = motorToEncoderFR.runToTarget(0.5, 1140, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderFL.runToTarget(0.5, 1140, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderBL.runToTarget(0.5, 1140, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_TO_POSITION) &&
                        motorToEncoderBR.runToTarget(0.5, 1140, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_TO_POSITION);

                //Test if targetReached is true
                if(targetReached) {
                    currentStep = step.complete;
                    break;
                }

            case complete:
                //Send Telemetry
                getTelemetryUtil().addData("Step: ", currentStep.toString());
                getTelemetryUtil().sendTelemetry();

                //sleep 2 seconds
                sleep(2000);

                startRobot();

                //Test if targetReached is true
                if(targetReached) {
                    setOperationsCompleted();
                    break;
                }
        }
    }

    protected ColorValue getPanelColor() {
        return ColorValue.RED;
    }

    private void startRobot() {
        targetReached = true;
    }

}
