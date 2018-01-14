package org.ftc8702.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by dkim on 12/18/17.
 */

public class MecanumWheelMotors extends FourWheelMotors {

    public MecanumWheelMotors(DcMotor FR, DcMotor FL, DcMotor BR, DcMotor BL) {
        this.motorFR = FR;
        this.motorFL = FL;
        this.motorBR = BR;
        this.motorBL = BL;
    }

    @Override
    public void forward(float power) {
        motorFR.setPower(power * -1);
        motorFL.setPower(power);
        motorBR.setPower(power * -1);
        motorBL.setPower(power);
    }

    @Override
    public void reverse(float power) {
        motorFR.setPower(power);
        motorFL.setPower(power * -1);
        motorBR.setPower(power);
        motorBL.setPower(power * -1);
    }

    @Override
    public void strafingLeft(float power) {
        motorFR.setPower(power);
        motorFL.setPower(power);
        motorBR.setPower(power * -1);
        motorBL.setPower(power * -1);

    }

    @Override
    public void strafingRight(float power) {
        motorFR.setPower(power * -1);
        motorFL.setPower(power * -1);
        motorBR.setPower(power);
        motorBL.setPower(power);
    }

    @Override
    public void rotateLeft(float power) {
        motorFR.setPower(power);
        motorFL.setPower(power);
        motorBR.setPower(power);
        motorBL.setPower(power);
    }

    @Override
    public void rotateRight(float power) {
        float reversePower = power * -1;
        motorFR.setPower(reversePower);
        motorFL.setPower(reversePower);
        motorBR.setPower(reversePower);
        motorBL.setPower(reversePower);
    }
}
