package org.ftc8702.configurations.production;

import com.qualcomm.hardware.HardwareDeviceManager;
import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.robotcore.util.Hardware;

import org.ftc8702.utils.InnovoticsRobotProperties;
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
public class ProdMecanumRobotConfiguration extends RobotConfiguration {
    //51.4 = 1 inch
    //mecanum motors
    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;
    public DcMotor SliderArmLeft;
    public DcMotor SliderArmRight;
    public DcMotor IntakeWheelLeft;
    public DcMotor IntakeWheelRight;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static ProdMecanumRobotConfiguration newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        ProdMecanumRobotConfiguration config = new ProdMecanumRobotConfiguration();
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

        //Mecanum Motors
        motorFR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_FR, hardwareMap.dcMotor);
        motorFL = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_FL, hardwareMap.dcMotor);
        motorBR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_BR, hardwareMap.dcMotor);
        motorBL = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_BL, hardwareMap.dcMotor);

        //Arm Motors
        SliderArmRight = (DcMotor) getHardwareOn(InnovoticsRobotProperties.SLIDER_ARM_RIGHT, hardwareMap.dcMotor);
        SliderArmLeft = (DcMotor) getHardwareOn(InnovoticsRobotProperties.SLIDER_ARM_LEFT, hardwareMap.dcMotor);

        //Intake Motors
        IntakeWheelLeft = (DcMotor) getHardwareOn(InnovoticsRobotProperties.INTAKE_WHEEL_LEFT, hardwareMap.dcMotor);
        IntakeWheelRight = (DcMotor) getHardwareOn(InnovoticsRobotProperties.INTAKE_WHEEL_RIGHT, hardwareMap.dcMotor);
    }
}
