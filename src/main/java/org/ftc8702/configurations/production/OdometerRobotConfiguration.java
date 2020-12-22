package org.ftc8702.configurations.production;

import com.qualcomm.hardware.HardwareDeviceManager;
import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.robotcore.util.Hardware;

import org.ftc8702.utils.InnovoticsRobotProperties;
import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

public class OdometerRobotConfiguration extends RobotConfiguration {

    public static final String MOTOR_FR = "wheelFR";
    public static final String MOTOR_FL = "wheelFL";
    public static final String MOTOR_BR = "wheelBR";
    public static final String MOTOR_BL = "wheelBL";
    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;

    public ColorSensor colorSensor;

    public BNO055IMU imu;

    public static OdometerRobotConfiguration newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        OdometerRobotConfiguration config = new OdometerRobotConfiguration();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        setTelemetry(telemetryUtil);

        //Mecanum Motors
        motorFR = (DcMotor) getHardwareOn(MOTOR_FR, hardwareMap.dcMotor);
        motorFL = (DcMotor) getHardwareOn(MOTOR_FL, hardwareMap.dcMotor);
        motorBR = (DcMotor) getHardwareOn(MOTOR_BR, hardwareMap.dcMotor);
        motorBL = (DcMotor) getHardwareOn(MOTOR_BL, hardwareMap.dcMotor);

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    }
}
