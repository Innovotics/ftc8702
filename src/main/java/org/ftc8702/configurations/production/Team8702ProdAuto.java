package org.ftc8702.configurations.production;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.opmodes.production.GyroAutoMode;
import org.ftc8702.utils.InnovoticsRobotProperties;


import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import java.util.concurrent.TimeUnit;

import ftcbootstrap.components.utils.TelemetryUtil;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class Team8702ProdAuto extends AbstractRobotConfiguration {

    public DcMotor wheelFR;
    public DcMotor wheelFL;
    public DcMotor wheelBR;
    public DcMotor wheelBL;

    protected GyroAutoMode gyroMode;

    public BNO055IMU imu;
    public ColorSensor colorSensor;
    public OpticalDistanceSensor ods;

    private HardwareMap hardwareMap;

    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);

        wheelFR = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_FR);
        wheelFL = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_FL);
        wheelBR = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_BR);
        wheelBL = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_BL);

        imu = hardwareMap.get(BNO055IMU.class, InnovoticsRobotProperties.GYRO_SENSOR);
       ods = hardwareMap.get(OpticalDistanceSensor.class, InnovoticsRobotProperties.OPTICAL_DISTANCE_SENSOR);


    }

    public HardwareMap getHardwareMap() {

        return hardwareMap;
    }

    public BNO055IMU getGyroSensor() {

        return imu;
    }


    public void stopRobot() {

        wheelFR.setPower(0);
        wheelFL.setPower(0);
        wheelBR.setPower(0);
        wheelBL.setPower(0);
    }


    public void setRunMode() {
        wheelFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wheelBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void forwardRobot(double speed)
    {
        wheelFR.setPower(speed);
        wheelFL.setPower(speed);
        wheelBR.setPower(-speed);
        wheelBL.setPower(-speed);
    }

    public void backwardRobot(double speed)
    {

        wheelBL.setPower(speed);
        wheelBR.setPower(speed);
        wheelFR.setPower(-speed);
        wheelFL.setPower(-speed);
    }

    public void turnRight(double speed, double angle) {

        if(gyroMode.getAngles().secondAngle > angle) {
            wheelFR.setPower(speed);
            wheelFL.setPower(speed);
            wheelBR.setPower(speed);
            wheelBL.setPower(speed);
        }

    }
    
    public void turnLeft(double speed, double angle) {

        if(gyroMode.getAngles().secondAngle < angle) {
            wheelFR.setPower(-speed);
            wheelFL.setPower(-speed);
            wheelBR.setPower(-speed);
            wheelBL.setPower(-speed);
        }
    }

    public void shiftRight(double speed) {
        wheelFR.setPower(speed);
        wheelFL.setPower(-speed);
        wheelBR.setPower(speed);
        wheelBL.setPower(-speed);
    }

    public void shiftLeft(double speed) {
        wheelFR.setPower(-speed);
        wheelFL.setPower(speed);
        wheelBR.setPower(-speed);
        wheelBL.setPower(speed);
    }

    public void sleep(long duration) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(duration);
    }
}
