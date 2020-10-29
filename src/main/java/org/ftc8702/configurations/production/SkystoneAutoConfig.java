package org.ftc8702.configurations.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.opmodes.roverruckus_skystone.SkystoneFlexArm;
import org.ftc8702.opmodes.roverruckus_skystone.SkystoneIntake;
import org.ftc8702.opmodes.roverruckus_skystone.SkystoneJaJa;
import org.ftc8702.opmodes.roverruckus_skystone.SkystoneSlideAndBrickPicker;

import ftcbootstrap.components.utils.TelemetryUtil;

public class SkystoneAutoConfig extends AbstractRobotConfiguration {

    public ColorSensor colorSensor;
    public MecanumWheelDriveTrain driveTrain;

    public SkystoneJaJa jaja;
    public SkystoneFlexArm FlexArm;
    public SkystoneSlideAndBrickPicker Slider;
    public SkystoneIntake Intake;
    public BNO055IMU imu;

    private HardwareMap hardwareMap;


    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);

        SkystoneRobotConfiguration mecanumConfig = SkystoneRobotConfiguration.newConfig(hardwareMap, telemetryUtil);
        driveTrain = new MecanumWheelDriveTrain(mecanumConfig.motorFL,mecanumConfig.motorFR,mecanumConfig.motorBL,mecanumConfig.motorBR, telemetryUtil.getTelemetry());
        jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));
        FlexArm = new SkystoneFlexArm(mecanumConfig.SliderArmLeft, mecanumConfig.SliderArmRight);
        Slider = new SkystoneSlideAndBrickPicker(hardwareMap.get(Servo.class, "brickPicker"),(hardwareMap.get(CRServo.class, "linearSlide")));
        Intake = new SkystoneIntake(mecanumConfig.IntakeWheelLeft, mecanumConfig.IntakeWheelRight);
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        telemetryUtil.addData("Color Sensor", colorSensor+"");
        telemetryUtil.sendTelemetry();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
    }
}