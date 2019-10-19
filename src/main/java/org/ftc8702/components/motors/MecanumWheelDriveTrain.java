package org.ftc8702.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class MecanumWheelDriveTrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private Servo foundationGrabberLeft;
    private Servo foundationGrabberRight;

    public MecanumWheelDriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor) {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;
    }

    public void goForward(float power) {
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(power); // because motor is on the opposite side
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(power);
    }

    public void goBackward(float power) {
        goForward(-power);
    }

    public void strafeRight(float power) {
        // right wheels rotate inward, left wheels rotate outward
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public void strafeLeft(float power) {
        strafeRight(-power);
    }

    public void rotateLeft(float power) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public void rotateRight(float power) { rotateRight(-power);}

    public void stop() {
        goForward(0);
    }

    public void grabbersDown(float power)
    {
        foundationGrabberLeft.setDirection(Servo.Direction.FORWARD);
        foundationGrabberRight.setDirection (Servo.Direction.FORWARD);
        foundationGrabberLeft.setPosition(power);
        foundationGrabberRight.setPosition (power);

    }

    public void grabbersUp(float power)
    {
        foundationGrabberLeft.setDirection(Servo.Direction.REVERSE);
        foundationGrabberRight.setDirection (Servo.Direction.REVERSE);
        foundationGrabberLeft.setPosition(power);
        foundationGrabberRight.setPosition (power);

    }


}
