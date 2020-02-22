package org.ftc8702.configurations.test;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.utils.InnovoticsRobotProperties;
import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

public class BenTestConfig extends RobotConfiguration {

    public ColorSensor colorSensor;

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);

        colorSensor = (ColorSensor) getHardwareOn("rightColorSensor", hardwareMap.colorSensor);
        getTelemetryUtil().addData("Color component: ", colorSensor.toString());

        getTelemetryUtil().sendTelemetry();
    }

    public static BenTestConfig newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        BenTestConfig config = new BenTestConfig();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }
}
