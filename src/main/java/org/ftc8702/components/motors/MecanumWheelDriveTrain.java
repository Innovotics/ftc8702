package org.ftc8702.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumWheelDriveTrain {
    public Telemetry telemetry;
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;

    public MecanumWheelDriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, Telemetry telemetry) {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;
        this.telemetry = telemetry;
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

    public void goRightDiagonal(float power) {
        frontLeftMotor.setPower(power);
        backRightMotor.setPower(-power);
    }

    public void goLeftDiagonal(float power) {
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
    }

    public void strafeLeft(float power) {
        // right wheels rotate inward, left wheels rotate outward
        frontLeftMotor.setPower(-power + (power *.1));
        frontRightMotor.setPower(-power + (power*.1));
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    public void strafeRight(float power) {
        strafeLeft(-power);
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

    public void pivitRight() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0.6);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0.6);
    }

    public void pivitLeft() {
        frontLeftMotor.setPower(-0.6);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(-0.6);
        backRightMotor.setPower(0);
    }
    public void turnSmoothRight() {
        frontLeftMotor.setPower(-1);
        frontRightMotor.setPower(0.2);
        backLeftMotor.setPower(-1);
        backRightMotor.setPower(0.2);
    }
    public void turnSmoothRightBack () {
        frontLeftMotor.setPower(1);
        frontRightMotor.setPower(-0.2);
        backLeftMotor.setPower(1);
        backRightMotor.setPower(-0.2);
    }
    public void turnSmoothRightAutonomous () {
        frontLeftMotor.setPower(-0.6);
        frontRightMotor.setPower(0.1);
        backLeftMotor.setPower(-0.6);
        backRightMotor.setPower(0.1);
    }
    public void turnSmoothLeftAutonomous () {
        frontLeftMotor.setPower(-0.2);
        frontRightMotor.setPower(0.4);
        backLeftMotor.setPower(-0.2);
        backRightMotor.setPower(0.4);
    }
    public void turnSmoothLeft () {
        frontLeftMotor.setPower(-0.2);
        frontRightMotor.setPower(1);
        backLeftMotor.setPower(-0.2);
        backRightMotor.setPower(1);
    }
    public void turnSmoothLeftBack () {
        frontLeftMotor.setPower(0.2);
        frontRightMotor.setPower(-1);
        backLeftMotor.setPower(0.2);
        backRightMotor.setPower(-1);
    }

    private static final double TICKS_PER_INCHES = 1446 / 4.71;

    public void goForwardByInches(double distanceInches, double power) {
        double ticks =  distanceInches * TICKS_PER_INCHES;
        goForwardWithOdometer(ticks, ticks, power);
    }

    public void goForwardWithOdometer(double leftTargetTicks, double rightTargetTicks, double power) {
        // right - go forward will go negative
        // left - go forward will go positive
        // at beginning current = 0, righttarget = -1450 ==> go forward
        // at beginning current = 0, lefttarget = 1450 ==> go forward

        int frontLeftTicks = -1 * frontLeftMotor.getCurrentPosition(); // flip sign to make both sides same direction
        int frontRightTicks = frontRightMotor.getCurrentPosition();
        telemetry.addData("ticks", "target=" + leftTargetTicks
                + " frontLeftTicks=" + frontLeftTicks + " frontRightTicks=" + frontRightTicks);
        telemetry.update();

        if (frontLeftTicks <= leftTargetTicks && frontRightTicks <= rightTargetTicks)
        {
            if (frontRightTicks > frontLeftTicks + 100) { //if turn right too much
                telemetry.addData("Right > Left", "");
                frontLeftMotor.setPower(-power/2);
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(power/2);
                backRightMotor.setPower(power);
            } else if (frontLeftTicks > frontRightTicks + 100) { // if turn left too much
                telemetry.addData("Left > Right", "");
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power/2); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(power/2);
            } else {
                telemetry.addData("go stright", "");
                goForward((float) power);
            }
            telemetry.update();
        }
        else
        {
            stop();
        }

        /*
        if (leftOdometer <= frontLeftMotor.getCurrentPosition() && rightOdometer <= frontRightMotor.getCurrentPosition()) {

            if (rightOdometer > leftOdometer + 30) { //if turn right too much
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power - (.1)); // because motor is on the opposite side
                backLeftMotor.setPower(-power - (.1));
                backRightMotor.setPower(power);

            } else if (leftOdometer < rightOdometer - 30) { // if turn left too much
                frontLeftMotor.setPower(-power + (.1));
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(-power + (.1));
                backRightMotor.setPower(power);
            } else{
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power);
                backLeftMotor.setPower(-power);
                backRightMotor.setPower(power);
            }

            sleep(500);
        }
        stop();
         */
    }
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
