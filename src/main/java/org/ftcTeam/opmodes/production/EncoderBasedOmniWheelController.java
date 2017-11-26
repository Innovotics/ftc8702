package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftcTeam.configurations.production.Team8702Prod;
import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.operations.motors.MotorToEncoder;

/**
 * Created by dkim on 10/28/17.
 */

public class EncoderBasedOmniWheelController {

    //motors
    private MotorToEncoder motorToEncoderFR;
    private MotorToEncoder motorToEncoderFL;
    private MotorToEncoder motorToEncoderBR;
    private MotorToEncoder motorToEncoderBL;

    public void init(ActiveOpMode opMode, DcMotor motorFR, DcMotor motorFL, DcMotor motorBR, DcMotor motorBL )
    {

        //Declare the Motors
        motorToEncoderFL = new MotorToEncoder(opMode, motorFL);
        motorToEncoderFR = new MotorToEncoder(opMode, motorFR);
        motorToEncoderBR = new MotorToEncoder(opMode, motorBR);
        motorToEncoderBL = new MotorToEncoder(opMode, motorBL);
    }
    public void forward( int encoderValue) {


    }
    public void backward( int encoderValue) {

    }
    public void moveRight( int encoderValue) {

    }
    public void moveLeft( int encoderValue) {

    }
}

