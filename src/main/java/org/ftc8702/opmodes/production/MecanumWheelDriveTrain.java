package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotor;

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
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(-power); // because motor is on the opposite side
        backLeftMotor.setPower(power);
        backRightMotor.setPower(-power);
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

    public void rotateRight (float power) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public void stop() {
        goForward(0);
    }

}
