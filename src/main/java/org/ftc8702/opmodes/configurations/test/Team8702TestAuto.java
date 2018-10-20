package org.ftc8702.opmodes.configurations.test;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.ftcTeam.utils.RobotProperties;
import org.ftcbootstrap.RobotConfiguration;

import org.ftcbootstrap.components.utils.TelemetryUtil;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftcbootstrap.components.operations.motors.MotorToEncoder;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class Team8702TestAuto extends RobotConfiguration {
    //51.4 = 1 inch
    //motors
    public DcMotor motorR;
    public DcMotor motorL;

    public MotorToEncoder motorToEncoder;

    //Ultrasonic Sensor
//    public ModernRoboticsI2cRangeSensor rangeSensorL;
    public BNO055IMU imu;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static Team8702TestAuto newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        Team8702TestAuto config = new Team8702TestAuto();
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
            motorR = (DcMotor) getHardwareOn(RobotProperties.MOTOR_RIGHT_FRONT, hardwareMap.dcMotor);
            motorL = (DcMotor) getHardwareOn(RobotProperties.MOTOR_LEFT_FRONT, hardwareMap.dcMotor);

//            rangeSensorL = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, RobotProperties.ULTRASONIC_SENSOR);

            imu = hardwareMap.get(BNO055IMU.class, "imu");

        getTelemetryUtil().sendTelemetry();
    }
}