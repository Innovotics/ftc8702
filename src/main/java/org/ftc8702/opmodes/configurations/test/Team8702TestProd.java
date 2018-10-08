package org.ftc8702.opmodes.configurations.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.ftc8702.utils.RobotProperties;
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
public class Team8702TestProd extends RobotConfiguration {
    //51.4 = 1 inch
    //motors
    public DcMotor motorR;
    public DcMotor motorL;

    //Gyro Sensor
    public BNO055IMU gyroSensor;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static Team8702TestProd newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        Team8702TestProd config = new Team8702TestProd();
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
            motorR = (DcMotor) getHardwareOn(RobotProperties.MOTOR_RIGHT, hardwareMap.dcMotor);
            motorL = (DcMotor) getHardwareOn(RobotProperties.MOTOR_LEFT, hardwareMap.dcMotor);

            //gyro sensor
        gyroSensor = hardwareMap.get(BNO055IMU.class, "imu");


        getTelemetryUtil().sendTelemetry();
    }
}
