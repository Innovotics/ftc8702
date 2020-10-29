package org.ftc8702.opmodes.ultimategoal;

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

    //Wobble arm
    public static final String WOBBLE_ARM = "wobbleArm";
    public static final String CLAW_LEFT = "clawLeft";
    public static final String CLAW_RIGHT = "clawRight";

    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;

    public DcMotor wobbleMotor;
    public Servo clawLeft;
    public Servo clawRight;

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
        clawLeft = (Servo) getHardwareOn(CLAW_LEFT, hardwareMap.servo);
        clawRight = (Servo) getHardwareOn(CLAW_RIGHT, hardwareMap.servo);
    }
}
