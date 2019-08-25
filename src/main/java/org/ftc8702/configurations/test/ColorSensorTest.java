package org.ftc8702.configurations.test;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.utils.InnovoticsRobotProperties;
import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

public class ColorSensorTest extends RobotConfiguration {

    public ColorSensor colorSensorName;

    public DcMotor motorName;

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);

        colorSensorName = (ColorSensor) getHardwareOn(InnovoticsRobotProperties.COLOR_SENSOR, hardwareMap.colorSensor);
        getTelemetryUtil().addData("Left Back Color component: ", colorSensorName.toString());

        // Front Motors
        motorName = (DcMotor) getHardwareOn(InnovoticsRobotProperties.MOTOR_FL, hardwareMap.dcMotor);

        getTelemetryUtil().sendTelemetry();
    }

    public static ColorSensorTest newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        ColorSensorTest config = new ColorSensorTest();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }
}
