package org.ftc8702.configurations.production;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;

import ftcbootstrap.components.utils.TelemetryUtil;

public class SkystoneAutoConfig extends AbstractRobotConfiguration {

    public ColorSensor colorSensor;
    public MecanumWheelDriveTrain driveTrain;
    private HardwareMap hardwareMap;

    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);

        ProdMecanumRobotConfiguration mecanumConfig = ProdMecanumRobotConfiguration.newConfig(hardwareMap, telemetryUtil);
        driveTrain = new MecanumWheelDriveTrain(mecanumConfig.motorFL,mecanumConfig.motorFR,mecanumConfig.motorBL,mecanumConfig.motorBR);
    }
}
