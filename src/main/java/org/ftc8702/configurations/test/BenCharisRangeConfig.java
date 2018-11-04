package org.ftc8702.configurations.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.ftc8702.utils.InnovoticsRobotProperties;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

public class BenCharisRangeConfig extends RobotConfiguration {

    public ModernRoboticsI2cRangeSensor rangeSensor;

    public DcMotor motorL;
    public DcMotor motorR;

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);

        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, InnovoticsRobotProperties.ULTRA_SONIC_SENSOR);
        getTelemetryUtil().addData("Left Ultrasonic Component: ", rangeSensor.toString());

        // Front Motors
        motorR = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_RIGHT, hardwareMap.dcMotor);
        motorL = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_LEFT, hardwareMap.dcMotor);

        getTelemetryUtil().sendTelemetry();
    }

    public static BenCharisRangeConfig newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        BenCharisRangeConfig config = new BenCharisRangeConfig();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }
}

