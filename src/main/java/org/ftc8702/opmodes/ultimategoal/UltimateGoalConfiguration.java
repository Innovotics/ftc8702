package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftc8702.utils.InnovoticsRobotProperties;

import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

public class UltimateGoalConfiguration extends RobotConfiguration {

    //Motor
    public static final String MOTOR_FR = "wheelFR";
    public static final String MOTOR_FL = "wheelFL";
    public static final String MOTOR_BR = "wheelBR";
    public static final String MOTOR_BL = "wheelBL";
    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;

    //Wobble arm
    public static final String WOBBLE_ARM = "wobbleArm";
    public static final String CLAW = "claw";
    public DcMotor wobbleMotor;
    public Servo claw;

    //Intake
    public static final String INTAKE_LEFT = "intakeLeft";
    public static final String INTAKE_RIGHT = "intakeRight";
    public DcMotor intakeLeft;
    public DcMotor intakeRight;

    //Shooter
    public static final String SHOOTER = "shooter";
    public static final String PUSHER = "pusher";
    public static final String LIFTER_RIGHT = "lifterRight";
    public static final String LIFTER_LEFT = "lifterLeft";
    public DcMotor shooter;
    public Servo pusher;
    public Servo lifterRight;
    public Servo lifterLeft;

    //Linear Actuator
    //public static final String LINEAR = "linearActuator";
    //public Servo linearActuator;

    public ColorSensor colorSensor;

    public BNO055IMU imu;

    public static UltimateGoalConfiguration newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        UltimateGoalConfiguration config = new UltimateGoalConfiguration();
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

        wobbleMotor = (DcMotor) getHardwareOn(WOBBLE_ARM, hardwareMap.dcMotor);
        claw = (Servo) getHardwareOn(CLAW, hardwareMap.servo);

        intakeLeft = (DcMotor) getHardwareOn(INTAKE_LEFT, hardwareMap.dcMotor);
        intakeRight = (DcMotor) getHardwareOn(INTAKE_RIGHT, hardwareMap.dcMotor);

        shooter = (DcMotor) getHardwareOn(SHOOTER, hardwareMap.dcMotor);
        pusher = (Servo) getHardwareOn(PUSHER, hardwareMap.servo);
        lifterRight = (Servo) getHardwareOn(LIFTER_RIGHT, hardwareMap.servo);
        lifterLeft = (Servo) getHardwareOn(LIFTER_LEFT, hardwareMap.servo);

        //linearActuator = (Servo) getHardwareOn(LINEAR, hardwareMap.servo);

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);

        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }
}
