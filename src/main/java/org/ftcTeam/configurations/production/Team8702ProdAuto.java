package org.ftcTeam.configurations.production;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftcTeam.utils.RobotProperties;
import org.ftcbootstrap.RobotConfiguration;

import org.ftcbootstrap.components.utils.TelemetryUtil;

import com.qualcomm.robotcore.hardware.ColorSensor;

import com.qualcomm.robotcore.hardware.Servo;


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

    //Servo
    public Servo elmoSpin;
    public Servo elmoReach;

    //Color Sensor
    public ColorSensor elmoColorSensor;


    //Ultrasonic Sensor
    public ModernRoboticsI2cRangeSensor rangeSensorL;
    public ModernRoboticsI2cRangeSensor rangeSensorR;
    public BNO055IMU imu;

    public Servo clapperLeftB;
    public Servo clapperRightB;
    public DcMotor clapperMotor;


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

        if (Team8702RobotConfig.MOTOR_ON) {
            // Front Motors
            motorFR = (DcMotor) getHardwareOn(RobotProperties.MOTOR_RIGHT_FRONT, hardwareMap.dcMotor);
            motorFL = (DcMotor) getHardwareOn(RobotProperties.MOTOR_RIGHT_FRONT, hardwareMap.dcMotor);
        }

        if (Team8702RobotConfig.ELMO_ON) {
            // Elmo Servos
            elmoColorSensor = hardwareMap.colorSensor.get(RobotProperties.COLOR_ELMO);

            if (elmoColorSensor != null) {
                getTelemetryUtil().addData("ElmoColorSensor: ", elmoColorSensor.toString());
            } else {
                getTelemetryUtil().addData("ElmoColorSensor: ", "is null");
            }

        }

        if (Team8702RobotConfig.CLAPPER_ON) {
            rangeSensorL = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, RobotProperties.ULTRASONIC_SENSOR);
           // rangeSensorR = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, InnovoticsRobotProperties.ULTRASONIC_SENSOR_R);

            imu = hardwareMap.get(BNO055IMU.class, "imu");
        }

        getTelemetryUtil().sendTelemetry();
    }


}
