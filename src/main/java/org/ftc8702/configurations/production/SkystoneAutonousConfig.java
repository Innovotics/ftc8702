package org.ftc8702.configurations.production;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.opmodes.production.SkystoneFlexArm;
import org.ftc8702.opmodes.production.SkystoneIntake;
import org.ftc8702.opmodes.production.SkystoneJaJa;
import org.ftc8702.opmodes.production.SkystoneSlideAndBrickPicker;
import org.ftc8702.utils.InnovoticsRobotProperties;

import com.qualcomm.robotcore.hardware.DcMotor;

import ftcbootstrap.components.utils.TelemetryUtil;

public class SkystoneAutonousConfig extends AbstractRobotConfiguration {

    public ColorSensor colorSensor;
    public MecanumWheelDriveTrain driveTrain;

    public SkystoneJaJa jaja;
    public SkystoneFlexArm FlexArm;
    public BNO055IMU imu;

    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;

    private HardwareMap hardwareMap;


    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);

        ProdMecanumRobotConfiguration mecanumConfig = ProdMecanumRobotConfiguration.newConfig(hardwareMap, telemetryUtil);
        jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));

        motorFR = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_FR);
        motorFL = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_FL);
        motorBR = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_BR);
        motorBL = hardwareMap.get(DcMotor.class, InnovoticsRobotProperties.MOTOR_BL);

        FlexArm = new SkystoneFlexArm(mecanumConfig.SliderArmLeft, mecanumConfig.SliderArmRight);
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
        telemetryUtil.addData("Color Sensor", colorSensor+"");
        telemetryUtil.sendTelemetry();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
    }
}