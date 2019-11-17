package org.ftc8702.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class MecanumWheelDriveTrain {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

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

    public void rotateRight(float power) {
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(-power);
    }//Used to be rotate Right

    // when power = 1
    private double speedForwardInFtPerSecond = (1.3 * 2) / 0.968;// speed = distance / time

    public void goForwardFullSpeedInFeet(double feet, boolean stopWhenFinished)
    {
        goForward(1);
        double time = feet / speedForwardInFtPerSecond;
        sleep(time);
        if (stopWhenFinished)
        {
            stop();
        }
    }

    private void sleep(double seconds)
    {
        try {
            Thread.sleep((long) seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        goForward(0);
    }
}
