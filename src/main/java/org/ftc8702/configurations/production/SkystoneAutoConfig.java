package org.ftc8702.configurations.production;

import android.graphics.Color;

import com.qualcomm.hardware.HardwareFactory;
import com.qualcomm.hardware.motors.TetrixMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.opmodes.production.SkystoneFlexArm;
import org.ftc8702.opmodes.production.SkystoneHugger;
import org.ftc8702.opmodes.production.SkystoneIntake;
import org.ftc8702.opmodes.production.SkystoneJaJa;
import org.ftc8702.opmodes.production.SkystoneSlideAndBrickPicker;

import ftcbootstrap.components.utils.TelemetryUtil;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImpl;
import com.qualcomm.robotcore.hardware.CRServoImplEx;

public class SkystoneAutoConfig extends AbstractRobotConfiguration {

    public ColorSensor colorSensor;
    public MecanumWheelDriveTrain driveTrain;
<<<<<<< HEAD
    public SkystoneJaJa jaja;
    public SkystoneHugger hugger;
    public SkystoneFlexArm FlexArm;
    public SkystoneSlideAndBrickPicker Slider;
    public SkystoneIntake Intake;
=======
//    public SkystoneJaJa jaja;
//    public SkystoneHugger hugger;
//    public SkystoneFlexArm FlexArm;
//    public SkystoneIntake Intake;
//    public TFObjectDetector tfod;

>>>>>>> 6a019c8e39ec933f3d332239b8b4778f3c4c757d
    private HardwareMap hardwareMap;


    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);

        ProdMecanumRobotConfiguration mecanumConfig = ProdMecanumRobotConfiguration.newConfig(hardwareMap, telemetryUtil);
        driveTrain = new MecanumWheelDriveTrain(mecanumConfig.motorFL,mecanumConfig.motorFR,mecanumConfig.motorBL,mecanumConfig.motorBR);
<<<<<<< HEAD
//        jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));
//        FlexArm = new SkystoneFlexArm(mecanumConfig.SliderArmLeft, mecanumConfig.SliderArmRight);
//
//        Intake = new SkystoneIntake(mecanumConfig.IntakeWheelLeft, mecanumConfig.IntakeWheelRight);
//        //colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
//
////        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
//
//        hugger = new SkystoneHugger(hardwareMap.get(Servo.class, "huggerTop"),hardwareMap.get(Servo.class, "huggerBottom"));
=======
        jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));
        FlexArm = new SkystoneFlexArm(mecanumConfig.SliderArmLeft, mecanumConfig.SliderArmRight);
<<<<<<< HEAD
        Slider = new SkystoneSlideAndBrickPicker(hardwareMap.get(Servo.class, "brickPicker"),(hardwareMap.get(Servo.class, "linearSlide")));
        Intake = new SkystoneIntake(mecanumConfig.IntakeWheelLeft, mecanumConfig.IntakeWheelRight);
    //    colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
//        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");63db1ff58aa9cedd61bf49c8d4ff08b002b92506
=======

        Intake = new SkystoneIntake(mecanumConfig.IntakeWheelLeft, mecanumConfig.IntakeWheelRight);
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

>>>>>>> 6a019c8e39ec933f3d332239b8b4778f3c4c757d
        hugger = new SkystoneHugger(hardwareMap.get(Servo.class, "huggerTop"),hardwareMap.get(Servo.class, "huggerBottom"));
>>>>>>> 4e5e57d00e201b2a7bbbf723dde11cbef6288f02
      //  telemetryUtil.addData("Color Sensor", colorSensor+"");
        telemetryUtil.sendTelemetry();
    }
}
