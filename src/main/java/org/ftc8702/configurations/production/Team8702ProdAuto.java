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

    public DcMotor motorR;
    public DcMotor motorL;
    public DcMotor hook;

    public BNO055IMU imu;

    public ColorSensor colorSensorBackLeft;
    public ColorSensor colorSensorBackRight;

   // public ModernRoboticsI2cRangeSensor rangeSensor;

    public Servo markerDropper;

    private HardwareMap hardwareMap;

    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);
        initWheels(hardwareMap);
        initServo(hardwareMap);

        imu = hardwareMap.get(BNO055IMU.class, InnovoticsRobotProperties.GYRO_SENSOR);

        colorSensorBackRight = initColorSensor(hardwareMap, InnovoticsRobotProperties.COLOR_SENSOR_BACK_RIGHT);
        colorSensorBackLeft = initColorSensor(hardwareMap, InnovoticsRobotProperties.COLOR_SENSOR_BACK_LEFT);

        hook = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_HOOK);
        hook.setDirection(DcMotor.Direction.REVERSE );

        //  rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, InnovoticsRobotProperties.ULTRA_SONIC_SENSOR);
        // getTelemetryUtil().addData("Left Ultrasonic Component: ", rangeSensor.toString());

    }

    public HardwareMap getHardwareMap() {
        return hardwareMap;
    }

    private ColorSensor initColorSensor(HardwareMap hardwareMap, String name) {
        ColorSensor colorSensor = (ColorSensor) getHardwareOn(name, hardwareMap.colorSensor);
        getTelemetryUtil().addData(name + ": ", colorSensor == null ? "Not Found" : colorSensor.toString());
        getTelemetryUtil().sendTelemetry();
        return colorSensor;
    }

    private void initUltrasonicSensor(HardwareMap hardwareMap, String sensorName) {
        UltrasonicSensor ultrasonicSensor = hardwareMap.ultrasonicSensor.get(sensorName);

        if (ultrasonicSensor != null) {
            //getTelemetryUtil().addData("UltrasonicSensor: ", rangeSensorF.toString());
        } else {
            //getTelemetryUtil().addData("UltraSonicSensor: ", "is null");
        }

    }

    private void initWheels(HardwareMap hardwareMap) {

        motorR = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_RIGHT);
        motorL = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_LEFT);

        motorL.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        motorR.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
    }

    private void initServo(HardwareMap hardwareMap) {
        markerDropper = hardwareMap.get(Servo.class, InnovoticsRobotProperties.MARKER_DROPPER);
    }

    public BNO055IMU getGyroSensor() {
        return imu;
    }

    public void stopRobot() {
        motorR.setPower(0);
        motorL.setPower(0);
    }

    public void setRunMode() {
        motorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void turnLeft(double speed) {
        motorR.setPower(-speed);
        // TODO: TEST THIS
        motorL.setPower(speed);
    }

    public void turnRight(double speed) {
            // TODO: TEST THIS
        motorR.setPower(speed);
        motorL.setPower(-speed);
    }

    public void forwardRobot(double speed)
    {
        motorL.setPower(speed);
        motorR.setPower(speed);
    }

    public void backwardRobot(double speed)
    {
        motorL.setPower(-speed);
        motorR.setPower(-speed);
    }

    public void sleep(long duration) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(duration);
    }
}
