package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

public class LinearActuatorConfig extends RobotConfiguration {

    //Linear Actuator
    public static final String LINEAR = "linearActuator";
    public CRServo linearActuator;

    public static LinearActuatorConfig newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        LinearActuatorConfig config = new LinearActuatorConfig();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        linearActuator = (CRServo) getHardwareOn(LINEAR, hardwareMap.crservo);
    }
}
