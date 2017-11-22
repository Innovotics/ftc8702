package org.ftcTeam.configurations;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
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
public class Team8702Prod extends RobotConfiguration {
    //51.4 = 1 inch
    //motors
    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;

    //Servo
    public Servo clapperLeft;
    public Servo clapperRight;
    public DcMotor clapperMotor;
    public DigitalChannel digitalTouch;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static Team8702Prod newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        Team8702Prod config = new Team8702Prod();
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

        //Clapper Parts
        clapperMotor = (DcMotor) getHardwareOn(RobotProperties.CLAPPER_MOTOR, hardwareMap.dcMotor);
        clapperLeft = (Servo) getHardwareOn(RobotProperties.SERVO_LEFT, hardwareMap.servo);
        clapperRight = (Servo) getHardwareOn(RobotProperties.SERVO_RIGHT, hardwareMap.servo);
        clapperLeft.setPosition(0.5);
        clapperRight.setPosition(0.5);
        digitalTouch = hardwareMap.get(DigitalChannel.class, RobotProperties.CLAPPER_MOTOR_GUARD);
        //digitalTouch.setMode(DigitalChannel.Mode.INPUT);
        getTelemetryUtil().sendTelemetry();


    }


}
