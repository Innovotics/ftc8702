package org.ftc8702.opmodes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftc8702.opmodes.ultimategoal.UltimateGoalConfiguration;

import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

public class
MecanumConfig extends RobotConfiguration {

    public static final String MOTOR_FR = "wheelFR";
    public static final String MOTOR_FL = "wheelFL";
    public static final String MOTOR_BR = "wheelBR";
    public static final String MOTOR_BL = "wheelBL";
    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;
    public BNO055IMU imu;

    public static final String LIFTER = "lifter";
    public static final String ARM_LEFT= "armLeft";
    public static final String ARM_RIGHT = "armRight";
    public Servo lifter;
    public Servo armLeft;
    public Servo armRight;


    protected static MecanumConfig newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        MecanumConfig config = new MecanumConfig();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }
    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        setTelemetry(telemetryUtil);

        motorFR = (DcMotor) getHardwareOn(MOTOR_FR, hardwareMap.dcMotor);
        motorFL = (DcMotor) getHardwareOn(MOTOR_FL, hardwareMap.dcMotor);
        motorBR = (DcMotor) getHardwareOn(MOTOR_BR, hardwareMap.dcMotor);
        motorBL = (DcMotor) getHardwareOn(MOTOR_BL, hardwareMap.dcMotor);

        armLeft = (Servo) getHardwareOn(ARM_LEFT, hardwareMap.servo);
        armRight = (Servo) getHardwareOn(ARM_RIGHT, hardwareMap.servo);
        armLeft = (Servo) getHardwareOn(LIFTER, hardwareMap.servo);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
    }
}
