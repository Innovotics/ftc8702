package org.ftc8702.components.motors;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.utils.ColorValue;
import org.ftc8702.utils.SleepUtils;

import java.util.Locale;

public class MecanumWheelDriveTrain {
    public Telemetry telemetry;
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public BNO055IMU imu;


    public MecanumWheelDriveTrain(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, Telemetry telemetry, BNO055IMU imu) {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;
        this.telemetry = telemetry;
        this.imu = imu;
    }

    public void goBackwardWithColor(float power, ColorSensor colorSensor) {
        boolean colorState = true;

        while (colorState == true) {
            //looking for white color
            if (colorSensor.red() + colorSensor.blue() + colorSensor.green() >= 750){
                colorState = false;
            } else {
                goBackward(power);
            }
        }
    }

    public void goForwardWithColor(float power, ColorSensor colorSensor) {
        boolean colorState = true;

        while (colorState == true) {
            //looking for white color
            if (colorSensor.red() + colorSensor.blue() + colorSensor.green() >= 750){
                colorState = false;
            } else {
                goForward(power);
            }
        }
    }

    public void goForwardPIDDistance(int targetInches){
        int targetMilliseconds = (int)(((targetInches/26.97))*1000);
        goForwardPIDTime((float)0.5, 1, targetMilliseconds, 100);
    }
    /*
    power: how fast the robot travels
    deviatingValue: the amount of degrees the robot is off before it starts to adjust
    timeInMillisecond: the amount of time that the robot travels
    coMill: the amount of time before the robot checks to see how off the angle is
     */
    public void goForwardPIDTime(float power, double deviatingValue, int timeInMillisecond, int coMill) {
        double adjustPower = 0.2;
        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);
        telemetry.addData("initial angle = ", yawInitialAngle);
        telemetry.addData("Time in millis = ", timeInMillisecond);
        telemetry.update();
        //if the imu degrees is correct
        for (int i = 0; i < timeInMillisecond; i = i + coMill) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(-power);
                backRightMotor.setPower(power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power - (adjustPower)); // because motor is on the opposite side
                backLeftMotor.setPower(-power - (adjustPower));
                backRightMotor.setPower(power);

            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much
                frontLeftMotor.setPower(-power + (adjustPower));
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(-power + (adjustPower));
                backRightMotor.setPower(power);
            }

            SleepUtils.sleep(coMill);
        }
        stop();
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

    public void strafeRight(float power) {
        // right wheels rotate inward, left wheels rotate outward
        frontLeftMotor.setPower(-power + (power * .1));
        frontRightMotor.setPower(-power + (power * .1));
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

    public void turnSmoothRightBack() {
        frontLeftMotor.setPower(1);
        frontRightMotor.setPower(-0.2);
        backLeftMotor.setPower(1);
        backRightMotor.setPower(-0.2);
    }

    public void turnSmoothRightAutonomous() {
        frontLeftMotor.setPower(-0.6);
        frontRightMotor.setPower(0.1);
        backLeftMotor.setPower(-0.6);
        backRightMotor.setPower(0.1);
    }

    public void turnSmoothLeftAutonomous() {
        frontLeftMotor.setPower(-0.2);
        frontRightMotor.setPower(0.4);
        backLeftMotor.setPower(-0.2);
        backRightMotor.setPower(0.4);
    }

    public void turnSmoothLeft() {
        frontLeftMotor.setPower(-0.2);
        frontRightMotor.setPower(1);
        backLeftMotor.setPower(-0.2);
        backRightMotor.setPower(1);
    }

    public void turnSmoothLeftBack() {
        frontLeftMotor.setPower(0.2);
        frontRightMotor.setPower(-1);
        backLeftMotor.setPower(0.2);
        backRightMotor.setPower(-1);
    }

    public void goForwardWithPIDInches(float power, double deviatingValue, double targetInches, double sleepTimeBeforeEachIteration) {

        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);
        int frontLeftTicks = -1 * frontLeftMotor.getCurrentPosition(); // flip sign to make both sides same direction
        int frontRightTicks = frontRightMotor.getCurrentPosition();
        double frontLeftInches = 0;
        double frontRightInches = 0;

        while (frontLeftInches <= targetInches && frontRightInches <= targetInches) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);
            frontLeftInches = frontLeftTicks/1446.0;
            frontRightInches = frontRightTicks/1446.0;
            telemetry.addData("Left ", frontLeftMotor.getCurrentPosition() + "power = " + frontLeftMotor.getPower());
            telemetry.addData("Right ", frontRightMotor.getCurrentPosition() + "power = " + frontRightMotor.getPower());
            telemetry.update();

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(-power);
                backRightMotor.setPower(power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power - (.1)); // because motor is on the opposite side
                backLeftMotor.setPower(-power - (.1));
                backRightMotor.setPower(power);
                telemetry.addData("Right ", "too much");
                telemetry.update();

            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much
                frontLeftMotor.setPower(-power + (.1));
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(-power + (.1));
                backRightMotor.setPower(power);
                telemetry.addData("Left ", "too much");
                telemetry.update();
            }
            telemetry.update();
        }
        stop();
    }

    private static final double TICKS_PER_INCHES = 1446 / 4.71;

    public void goForwardByInches(double distanceInches, double power) {
        double ticks = distanceInches * TICKS_PER_INCHES;
        goForwardWithOdometer(ticks, ticks, power);
    }

    public void goForwardWithOdometer(double leftTargetTicks, double rightTargetTicks, double power) {
        // right - go forward will go negative
        // left - go forward will go positive
        // at beginning current = 0, righttarget = -1450 ==> go forward
        // at beginning current = 0, lefttarget = 1450 ==> go forward

        int frontLeftTicks = frontLeftMotor.getCurrentPosition(); // flip sign to make both sides same direction
        int frontRightTicks = -1 * frontRightMotor.getCurrentPosition();
        telemetry.addData("ticks, ",  " frontLeftTicks=" + frontLeftTicks + " frontRightTicks=" + frontRightTicks);
        telemetry.update();

        if (frontLeftTicks >= leftTargetTicks || frontRightTicks >= rightTargetTicks) {
            stop();
        } else {
            goForward((float)(power));
        }

        /*
        if (frontLeftTicks <= leftTargetTicks && frontRightTicks <= rightTargetTicks) {
            goForward((float)(power));

         */
            /*
            if (frontRightTicks > frontLeftTicks + 100) { //if turn right too much
                telemetry.addData("Right > Left", "");
                frontLeftMotor.setPower(-power / 2);
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(power / 2);
                backRightMotor.setPower(power);
            } else if (frontLeftTicks > frontRightTicks + 100) { // if turn left too much
                telemetry.addData("Left > Right", "");
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(power / 2); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(power / 2);
            } else {
                telemetry.addData("go stright", "");
                goForward((float) power);
            }
            telemetry.update();
            */
            /*
        } else {
            stop();
        }
             */

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

    public void goForwardFullSpeedInFeet(double feet, boolean stopWhenFinished) {
        goForward(1);
        double time = feet / speedForwardInFtPerSecond;
        sleep(time);
        if (stopWhenFinished) {
            stop();
        }
    }

    public void sleep(double seconds) {
        try {
            Thread.sleep((long) seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        goForward(0);
    }

    public ColorValue getColor(ColorSensor colorSensor) {
        //Helping fix the red color sense correctly, 20 is to offset the color sensor bias toward red.
        int FixRed = 15;

        //Determine which is color to call
        if (colorSensor.red() - colorSensor.blue() > FixRed
                && (colorSensor.red() - colorSensor.green()) > FixRed) {
            return ColorValue.RED;
        }

        if (colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) {
            return ColorValue.BLUE;
        }

        if (colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue()) {
            return ColorValue.GREEN;
        }

        return ColorValue.ZILCH;
    }

    public void rotateLeftWithGyro(float power, float angleInDegrees) {
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);

        while(true) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if(yawAngle >= angleInDegrees) {
                stop();
                break;
            }
        }
        stop();
    }

    public void rotateRightWithGyro(float power, float angleInDegrees) {

        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(-power);

        while(true) {

            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if(yawAngle <= angleInDegrees) {
                stop();
                break;
            }
        }
        stop();
    }

    public Orientation readAngles()
    {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

    String formatAngle(AngleUnit angleUnit, double angle)
    {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees)
    {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}

