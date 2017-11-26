package org.ftcTeam.utils;

import org.ftcTeam.configurations.production.Team8702Prod;
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

    //configuration
    private Team8702Prod robot;

    public EncoderBasedOmniWheelController(Team8702Prod robot) {
        this.robot = robot;
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

