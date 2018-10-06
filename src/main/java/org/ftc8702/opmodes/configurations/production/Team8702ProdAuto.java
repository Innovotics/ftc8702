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
public class Team8702ProdAuto extends RobotConfiguration {
    //51.4 = 1 inch
    //motors
    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;

//    public MotorToEncoder motorToEncoderFR;
//    public MotorToEncoder motorToEncoderFL;
//    public MotorToEncoder motorToEncoderBR;
//    public MotorToEncoder motorToEncoder


    //Color Sensor
    public ColorSensor colorSensorBR;

    //Ultrasonic Sensor
    public ModernRoboticsI2cRangeSensor rangeSensorR;
    public ModernRoboticsI2cRangeSensor rangeSensorF;
    //FIGURE OUT HOW TO USE
    public BNO055IMU gyroSensor;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static Team8702ProdAuto newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        Team8702ProdAuto config = new Team8702ProdAuto();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }

    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetryUtil
     */
    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);

            // Front Motors
            motorFR = (DcMotor) getHardwareOn(RobotProperties.MOTOR_RIGHT_FRONT, hardwareMap.dcMotor);
            motorFL = (DcMotor) getHardwareOn(RobotProperties.MOTOR_LEFT_FRONT, hardwareMap.dcMotor);
            // Back Motors
            motorBR = (DcMotor) getHardwareOn(RobotProperties.MOTOR_RIGHT_BACK, hardwareMap.dcMotor);
            motorBL = (DcMotor) getHardwareOn(RobotProperties.MOTOR_LEFT_BACK, hardwareMap.dcMotor);

//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_BACK_LEFT);
//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_BACK_RIGHT);
//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_FRONT_LEFT);
//        initColorSensor(hardwareMap, RobotProperties.COLOR_SENSOR_FRONT_RIGHT);
//
//            rangeSensorR = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, RobotProperties.ULTRASONIC_SENSOR_RIGHT);
//           // rangeSensorR = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, RobotProperties.ULTRASONIC_SENSOR_R);

            gyroSensor = hardwareMap.get(BNO055IMU.class, "imu");
        getTelemetryUtil().sendTelemetry();
    }

    public void initColorSensor(HardwareMap hardwareMap, String sensorName) {
        ColorSensor colorSensor = hardwareMap.colorSensor.get(sensorName);

        if (colorSensor != null) {
            getTelemetryUtil().addData("ColorSensor: ", colorSensorBR.toString());
        } else {
            getTelemetryUtil().addData("ColorSensor: ", "is null");
        }

    }

    public void initUltrasonicSensor(HardwareMap hardwareMap, String sensorName) {
        UltrasonicSensor ultrasonicSensor = hardwareMap.ultrasonicSensor.get(sensorName);

        if (ultrasonicSensor != null) {
            getTelemetryUtil().addData("UltrasonicSensor: ", rangeSensorF.toString());
        } else {
            getTelemetryUtil().addData("UltraSonicSensor: ", "is null");
        }

    }
}
