package org.ftcTeam.configurations.test;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftcTeam.utils.RobotProperties;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class Team8702Clapper extends RobotConfiguration {

    //Servo
    public Servo clapperLeft;
    public Servo clapperRight;
    public DcMotor clapperMotor;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static Team8702Clapper newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        Team8702Clapper config = new Team8702Clapper();
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
        //Servos
        clapperLeft = (Servo) getHardwareOn(RobotProperties.SERVO_TEST, hardwareMap.servo);
        clapperLeft.setPosition(0.5);
        clapperRight.setPosition(0.5);
    }

}
