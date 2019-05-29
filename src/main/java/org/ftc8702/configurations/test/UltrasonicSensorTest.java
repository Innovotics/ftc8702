package org.ftc8702.configurations.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.utils.InnovoticsRobotProperties;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

public class UltrasonicSensorTest extends RobotConfiguration {

    public ModernRoboticsI2cRangeSensor rangeSensor;

    public DcMotor motorName;


    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);

        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, InnovoticsRobotProperties.ULTRASONIC_SENSOR);
        getTelemetryUtil().addData("Left Ultrasonic Component: ", rangeSensor.toString());


        motorName = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_EXAMPLE, hardwareMap.dcMotor);

        getTelemetryUtil().sendTelemetry();
    }

    public static UltrasonicSensorTest newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        UltrasonicSensorTest config = new UltrasonicSensorTest();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }
}

