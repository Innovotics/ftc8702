package org.ftc8702.opmodes.configurations.test;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.utils.RobotProperties;
import org.ftcTeam.configurations.test.Team8702AdafruitSensor;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

public class BenTestConfig extends RobotConfiguration {

    public ColorSensor colorSensor;

    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        setTelemetry(telemetryUtil);

        colorSensor = (ColorSensor) getHardwareOn(RobotProperties.COLOR_SENSOR_FRONT_LEFT, hardwareMap.colorSensor);
        getTelemetryUtil().addData("Color component: ", colorSensor.toString());

        getTelemetryUtil().sendTelemetry();
    }

    public static BenTestConfig newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        BenTestConfig config = new BenTestConfig();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }
}
