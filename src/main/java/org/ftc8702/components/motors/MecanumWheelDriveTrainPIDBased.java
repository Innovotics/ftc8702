package org.ftc8702.components.motors;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.ftc8702.configurations.production.SkystoneAutoConfig;
import org.ftc8702.configurations.production.SkystoneAutonousConfig;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.ftc8702.utils.ColorValue;

import java.util.Locale;

import ftcbootstrap.components.utils.TelemetryUtil;


public class MecanumWheelDriveTrainPIDBased {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    private BNO055IMU imu;
    private Telemetry telemetry;
    private SkystoneAutoConfig robot;


    public MecanumWheelDriveTrainPIDBased(DcMotor frontLeftMotor, DcMotor frontRightMotor, DcMotor backLeftMotor, DcMotor backRightMotor, BNO055IMU imu) {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;
        this.imu = imu;

    }
    public void goBackwardWithoutPID(float power){
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(-power);
    }

    public void goForwardWithoutPID(float power){
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(power);
    }

    public void strafeRightWithoutPID(float power){
        frontLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backLeftMotor.setPower(-power + (power *.1));
        backRightMotor.setPower(-power + (power *.1));
    }

    public void goForward(float power, double deviatingValue, int timeInMillisecond, int coMill) {

        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);

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
                frontRightMotor.setPower(power - (.1)); // because motor is on the opposite side
                backLeftMotor.setPower(-power - (.1));
                backRightMotor.setPower(power);

            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much
                frontLeftMotor.setPower(-power + (.1));
                frontRightMotor.setPower(power); // because motor is on the opposite side
                backLeftMotor.setPower(-power + (.1));
                backRightMotor.setPower(power);
            }

            sleep(coMill);
        }
        stop();
    }


    public void goBackward(float power, double deviatingValue, int timeInMilliseconds, int coMill) {

        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);

        //if the imu degrees is correct
        for (int i = 0; i < timeInMilliseconds; i = i + coMill) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(-power); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(-power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much

                frontLeftMotor.setPower(power - (.1));
                frontRightMotor.setPower(-power); // because motor is on the opposite side
                backLeftMotor.setPower(power - (.1));
                backRightMotor.setPower(-power);


            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much

                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(-power + (.1)); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(-power + (.1));

            }

            sleep(coMill);
        }
        stop();

    }


    public void strafeRight(float power, double deviatingValue, int timeInMilliseconds, long coMill) {

        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);

        //if the imu degrees is correct
        for (long i = 0; i < timeInMilliseconds; i = i + coMill) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(-power);
                backLeftMotor.setPower(power);
                backRightMotor.setPower(power);

            } else if (yawAngle < yawInitialAngle - deviatingValue) { //if turn right too much

                frontLeftMotor.setPower(-power + (.1));
                frontRightMotor.setPower(-power + (.1));
                backLeftMotor.setPower(power);
                backRightMotor.setPower(power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { // if turn left too much

                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(-power);
                backLeftMotor.setPower(power - (.1));
                backRightMotor.setPower(power - (.1));
            }

            sleep(coMill);
        }
        stop();
    }

    public void strafeLeftWithoutPid(float power){
        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(power);
        backRightMotor.setPower(power);

    }


    public void strafeLeft(float power, double deviatingValue, int timeInMilliseconds, int coMill) {

        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);

        //if the imu degrees is correct
        for (int i = 0; i < timeInMilliseconds; i = i + coMill) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(-power);
                backLeftMotor.setPower(power);
                backRightMotor.setPower(power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much
                frontLeftMotor.setPower(power - (.1));
                frontRightMotor.setPower(power - (.1));
                backLeftMotor.setPower(-power);
                backRightMotor.setPower(-power);

            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much
                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(power);
                backLeftMotor.setPower(-power + (.1));
                backRightMotor.setPower(-power + (.1));
            }

            sleep(coMill);
        }

    }

    //go backwards with ultrasonic sensor
    public void goBackwardWithUltrasonic(float power, double deviatingValue, DistanceSensor ultrasonicSensor, float distance) {
        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);


        //if imu degree is correct
        while (true) {
            //if the imu degrees is correct

            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(-power); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(-power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much

                frontLeftMotor.setPower(power - (.1));
                frontRightMotor.setPower(-power); // because motor is on the opposite side
                backLeftMotor.setPower(power - (.1));
                backRightMotor.setPower(-power);


            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much

                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(-power + (.1)); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(-power + (.1));

            }

            if(ultrasonicSensor.getDistance(DistanceUnit.CM) < distance) {
                stop();
                break;
            }

        }

    }


    //go backwards with ultrasonic sensor
    public void strafeRightWithUltrasonic(float power, double deviatingValue, DistanceSensor ultrasonicSensor, float distance) {
        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);


        //if imu degree is correct
        while (true) {
            //if the imu degrees is correct

            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(-power);
                frontRightMotor.setPower(-power);
                backLeftMotor.setPower(power);
                backRightMotor.setPower(power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much

                frontLeftMotor.setPower(-power - (.1));
                frontRightMotor.setPower(-power - (.1));
                backLeftMotor.setPower(power - (.1));
                backRightMotor.setPower(power - (.1));

            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much


                frontLeftMotor.setPower(-power + (.1));
                frontRightMotor.setPower(-power + (.1));
                backLeftMotor.setPower(power + (.1));
                backRightMotor.setPower(power + (.1));
            }

            if(ultrasonicSensor.getDistance(DistanceUnit.CM) < distance) {
                stop();
                break;
            }

        }

    }


    public void strafeLeftWithSensor(float power, double deviatingValue, ColorSensor colorSensor) {

        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);

        boolean colorDetected = false;

        //if the imu degrees is correct
        while (!colorDetected) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(power);
                backLeftMotor.setPower(-power);
                backRightMotor.setPower(-power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much
                frontLeftMotor.setPower(power - (.1));
                frontRightMotor.setPower(power - (.1));
                backLeftMotor.setPower(-power);
                backRightMotor.setPower(-power);


            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much
                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(power);
                backLeftMotor.setPower(-power + (.1));
                backRightMotor.setPower(-power + (.1));
            }
            ColorValue colorValue = getColor(colorSensor);
            if (colorValue == ColorValue.RED || colorValue == ColorValue.BLUE) {
                colorDetected = true;
            }
        }

        stop();

    }

    public void goBackwardWithSensor(float power, double deviatingValue, ColorSensor colorSensor) {

        Orientation initialAngle = readAngles();
        String rawInitialYawAngle = formatAngle(AngleUnit.DEGREES, initialAngle.firstAngle);
        float yawInitialAngle = Float.parseFloat(rawInitialYawAngle);

        boolean colorDetected = false;

        //if the imu degrees is correct
        while (!colorDetected) {
            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);

            if (yawAngle >= yawInitialAngle - deviatingValue && yawAngle <= yawInitialAngle + deviatingValue) {
                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(-power); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(-power);

            } else if (yawAngle > yawInitialAngle + deviatingValue) { //if turn right too much

                frontLeftMotor.setPower(power - (.1));
                frontRightMotor.setPower(-power); // because motor is on the opposite side
                backLeftMotor.setPower(power - (.1));
                backRightMotor.setPower(-power);

            } else if (yawAngle < yawInitialAngle - deviatingValue) { // if turn left too much
                frontLeftMotor.setPower(power);
                frontRightMotor.setPower(-power + (.1)); // because motor is on the opposite side
                backLeftMotor.setPower(power);
                backRightMotor.setPower(-power + (.1));

            }
        }

        stop();

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



    public void rotateRightWithGyro(float power, float angleInDegrees) {

        frontLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        backRightMotor.setPower(-power);

        while(true) {

            Orientation angle = readAngles();
            String rawYawAngle = formatAngle(AngleUnit.DEGREES, angle.firstAngle);
            float yawAngle = Float.parseFloat(rawYawAngle);


            telemetry.addData("Yaw Angle: ", yawAngle);
            telemetry.update();

            if(yawAngle <= angleInDegrees) {
                stop();
                break;

            }

        }

        stop();

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


            telemetry.addData("Yaw Angle: ", yawAngle);
            telemetry.update();

            if(yawAngle >= angleInDegrees) {
                stop();
                break;

            }

        }

        stop();
    }


    // when power = 1
    private double speedForwardInFtPerSecond = (1.3 * 2) / 0.968;// speed = distance / time

    private void sleep(double seconds) {
        try {
            Thread.sleep((long) seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }

    public void goForwardFullSpeedInFeet(double feet, boolean stopWhenFinished) {
        robot.driveTrain.goForward(1);
        double time = feet / speedForwardInFtPerSecond;
        sleep(time);
        if (stopWhenFinished) {
            stop();
        }
    }

    public Orientation readAngles() {

        return imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
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
}