package org.ftc8702.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by dkim on 12/17/17.
 */

abstract public class FourWheelMotors {
    protected DcMotor motorFR;
    protected DcMotor motorFL;
    protected DcMotor motorBR;
    protected DcMotor motorBL;


    abstract void forward(float power);
    abstract void reverse(float power);
    abstract void strafingLeft(float power);
    abstract void strafingRight(float power);
    abstract void rotateLeft(float power);
    abstract void rotateRight(float power);
}
