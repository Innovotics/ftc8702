package org.ftc8702.configurations.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftc8702.utils.InnovoticsRobotProperties;
import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.ServoComponent;
import ftcbootstrap.components.operations.motors.MotorToEncoder;
import ftcbootstrap.components.utils.TelemetryUtil;


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
    public DcMotor longArm;
    public DcMotor shortArm;

    public Servo clawA;
    public Servo clawB;



    //Sensors
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

            // Motors
            motorR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_BL, hardwareMap.dcMotor);
            hook.setDirection(DcMotorSimple.Direction.REVERSE);


            //Servo

            clawA = (Servo) getHardwareOn(InnovoticsRobotProperties.SERVO, hardwareMap.servo);

            //gyro sensor
            gyroSensor = hardwareMap.get(BNO055IMU.class, "imu");

        getTelemetryUtil().sendTelemetry();
    }
}
