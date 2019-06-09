package org.ftc8702.configurations.production;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.components.ImuGyroSensor;
import org.ftc8702.utilities.TelemetryUtil;
import org.ftc8702.utils.InnovoticsRobotProperties;


import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import java.util.concurrent.TimeUnit;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class Team8702ProdAuto extends AbstractRobotConfiguration {

    public DcMotor motorName;
    public BNO055IMU imu;
    public ColorSensor colorSensorName;
    public OpticalDistanceSensor ods;
    public Servo servoName;

    private HardwareMap hardwareMap;

    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);
        initMotors(hardwareMap);
        initServos(hardwareMap);

        imu = hardwareMap.get(BNO055IMU.class, InnovoticsRobotProperties.GYRO_SENSOR);
       ods = hardwareMap.get(OpticalDistanceSensor.class, InnovoticsRobotProperties.OPTICAL_DISTANCE_SENSOR);


    }

    public HardwareMap getHardwareMap() {
        return hardwareMap;
    }

    private void initMotors(HardwareMap hardwareMap) {

        motorName = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_BL);
        motorName.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors

    }

    private void initServos(HardwareMap hardwareMap) {
        servoName = hardwareMap.get(Servo.class, InnovoticsRobotProperties.SERVO);
    }

    public BNO055IMU getGyroSensor() {
        return imu;
    }

    public void stopRobot() {
        motorName.setPower(0);
    }

    public void setRunMode() {
        motorName.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void moveClaw() {
        servoName.setPosition(.2);
    }

    public void turnLeft(double speed) {
        motorName.setPower(-speed);
    }

    public void turnRight(double speed) {
        motorName.setPower(speed);

    }

    public void forwardRobot(double speed)
    {
        motorName.setPower(-speed);
    }

    public void backwardRobot(double speed)
    {
        motorName.setPower(speed);
    }

    public void sleep(long duration) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(duration);
    }
}
