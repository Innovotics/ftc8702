package org.ftc8702.configurations.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;

import org.ftc8702.utils.InnovoticsRobotProperties;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.ServoComponent;
import org.ftcbootstrap.components.utils.TelemetryUtil;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class ProdManualRobot extends RobotConfiguration {
    //51.4 = 1 inch
    //motors
    public DcMotor motorR;
    public DcMotor motorL;
    public DcMotor hook;
    public CRServo intakeSystem;

    //Gyro Sensor
    public BNO055IMU gyroSensor;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static ProdManualRobot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        ProdManualRobot config = new ProdManualRobot();
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
            motorR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_RIGHT, hardwareMap.dcMotor);
            motorL = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_LEFT, hardwareMap.dcMotor);

            //gyro sensor
        gyroSensor = hardwareMap.get(BNO055IMU.class, "imu");

            //Attachments
            hook = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_HOOK, hardwareMap.dcMotor);
            intakeSystem = (CRServo) getHardwareOn(InnovoticsRobotProperties.INTAKE_SYSTEM, hardwareMap.crservo);


        getTelemetryUtil().sendTelemetry();
    }
}
