package org.ftc8702.configurations.production;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.opmodes.production.SkystoneJaJa;

import ftcbootstrap.components.utils.TelemetryUtil;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

public class SkystoneAutoConfig extends AbstractRobotConfiguration {

    public ColorSensor colorSensor;
    public MecanumWheelDriveTrain driveTrain;
    public SkystoneJaJa jaja;
    private HardwareMap hardwareMap;

    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);

        ProdMecanumRobotConfiguration mecanumConfig = ProdMecanumRobotConfiguration.newConfig(hardwareMap, telemetryUtil);
        driveTrain = new MecanumWheelDriveTrain(mecanumConfig.motorFL,mecanumConfig.motorFR,mecanumConfig.motorBL,mecanumConfig.motorBR);
        jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        telemetryUtil.addData("Color Sensor", colorSensor+"");
        telemetryUtil.sendTelemetry();
    }
}
