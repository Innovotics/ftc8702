package org.ftc8702.configurations.production;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.components.ImuGyroSensor;
import org.ftc8702.utilities.TelemetryUtil;
import org.ftc8702.utils.InnovoticsRobotProperties;


import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.hardware.UltrasonicSensor;


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
    public BNO055IMU gyroSensor;

    //Ultrasonic Sensor
//    public ModernRoboticsI2cRangeSensor rangeSensorR;
//    public ModernRoboticsI2cRangeSensor rangeSensorF;
    //FIGURE OUT HOW TO USE

    public Team8702ProdAuto() {

    }

    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);
        gyroSensor = hardwareMap.get(BNO055IMU.class, "imu");
        gyroSensor.initialize(ImuGyroSensor.getParameters());
    }

    private void initColorSensor(HardwareMap hardwareMap, String sensorName) {
        ColorSensor colorSensor = hardwareMap.colorSensor.get(sensorName);

        if (colorSensor != null) {
            //getTelemetryUtil().addData("ColorSensor: ", colorSensorBR.toString());
        } else {
            //getTelemetryUtil().addData("ColorSensor: ", "is null");
        }

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

        motorR = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_RIGHT_FRONT);
        motorL = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_LEFT_FRONT);
    }

    public BNO055IMU getGyroSensor() {
        return gyroSensor;
    }
}
