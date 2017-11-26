package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;
import org.ftcbootstrap.components.utils.MotorDirection;

/**
 * Created by dkim on 10/28/17.
 */

public class EncoderBasedOmniWheelController {

    //motors
    private MotorToEncoder motorToEncoderFR;
    private MotorToEncoder motorToEncoderFL;
    private MotorToEncoder motorToEncoderBR;
    private MotorToEncoder motorToEncoderBL;

    public void init(ActiveOpMode opMode, DcMotor motorFR, DcMotor motorFL, DcMotor motorBR, DcMotor motorBL) {

        //Declare the Motors
        motorToEncoderFL = new MotorToEncoder(opMode, motorFL);
        motorToEncoderFR = new MotorToEncoder(opMode, motorFR);
        motorToEncoderBR = new MotorToEncoder(opMode, motorBR);
        motorToEncoderBL = new MotorToEncoder(opMode, motorBL);
    }

    public void forward(int encoderValue, float powerValue) throws InterruptedException {
        motorToEncoderFL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderFR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void backward(int encoderValue, float powerValue) throws InterruptedException {
        motorToEncoderFL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderFR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveRight(int encoderValue, float powerValue) throws InterruptedException {
        motorToEncoderFL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderFR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void moveLeft(int encoderValue, float powerValue) throws InterruptedException {
        motorToEncoderFL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBL.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderFR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_BACKWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        motorToEncoderBR.runToTarget(powerValue, encoderValue, MotorDirection.MOTOR_FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);

    }
}

