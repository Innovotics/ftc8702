package org.ftcTeam.configurations.production;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftcTeam.utils.RobotProperties;
import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class ElmoConfig  {
    //Servo
    public Servo elmoSpin;
    public Servo elmoReach;
    //Color Sensor
    public ColorSensor elmoColorSensor;


    protected void init(RobotConfiguration config, HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
//        // Elmo Servos
//        elmoSpin = (Servo) getHardwareOn(InnovoticsRobotProperties.SERVO_ELMO_SPIN, hardwareMap.servo);
//        elmoReach = (Servo) getHardwareOn(InnovoticsRobotProperties.SERVO_ELMO_REACH, hardwareMap.servo);
//        //Color Sensor
//        elmoColorSensor = (ColorSensor) getHardwareOn(InnovoticsRobotProperties.COLOR_ELMO, hardwareMap.colorSensor);
    }
}
