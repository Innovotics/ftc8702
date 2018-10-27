package org.ftc8702.opmodes.configurations.production;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftcTeam.configurations.production.Team8702RobotConfig;
import org.ftcTeam.utils.RobotProperties;
import org.ftcbootstrap.RobotConfiguration;

import org.ftcbootstrap.components.utils.TelemetryUtil;

import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class Team8702ProdAuto  {
    //51.4 = 1 inch
    //motors
    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;



    //Color Sensor
    public ColorSensor colorSensorBR;

    //Ultrasonic Sensor
    public ModernRoboticsI2cRangeSensor rangeSensorR;
    public ModernRoboticsI2cRangeSensor rangeSensorF;
    //FIGURE OUT HOW TO USE
    public BNO055IMU gyroSensor;


    /* local OpMode members. */
    HardwareMap hwMap           =  null;

    public Team8702ProdAuto() {

    }
    protected void init(HardwareMap hardwareMap) {
        hwMap = hardwareMap;

        motorFR = hwMap.get(DcMotor.class, RobotProperties.MOTOR_RIGHT_FRONT);
        motorFL = hwMap.get(DcMotor.class, RobotProperties.MOTOR_LEFT_FRONT);

//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_BACK_LEFT);
//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_BACK_RIGHT);
//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_FRONT_LEFT);
//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_FRONT_RIGHT);
//
//            rangeSensorR = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, RobotProperties.ULTRASONIC_SENSOR_RIGHT);
//           // rangeSensorR = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, RobotProperties.ULTRASONIC_SENSOR_R);

            gyroSensor = hwMap.get(BNO055IMU.class, "imu");
    }

    public void initColorSensor(HardwareMap hardwareMap, String sensorName) {
        ColorSensor colorSensor = hardwareMap.colorSensor.get(sensorName);

        if (colorSensor != null) {
            //getTelemetryUtil().addData("ColorSensor: ", colorSensorBR.toString());
        } else {
            //getTelemetryUtil().addData("ColorSensor: ", "is null");
        }

    }

    public void initUltrasonicSensor(HardwareMap hardwareMap, String sensorName) {
        UltrasonicSensor ultrasonicSensor = hardwareMap.ultrasonicSensor.get(sensorName);

        if (ultrasonicSensor != null) {
            //getTelemetryUtil().addData("UltrasonicSensor: ", rangeSensorF.toString());
        } else {
            //getTelemetryUtil().addData("UltraSonicSensor: ", "is null");
        }

    }
}
