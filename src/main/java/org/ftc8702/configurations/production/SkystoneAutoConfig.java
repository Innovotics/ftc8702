package org.ftc8702.configurations.production;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.opmodes.production.SkystoneFlexArm;
import org.ftc8702.opmodes.production.SkystoneIntake;
import org.ftc8702.opmodes.production.SkystoneJaJa;
import org.ftc8702.opmodes.production.SkystoneSlideAndBrickPicker;

import ftcbootstrap.components.utils.TelemetryUtil;
import com.qualcomm.robotcore.hardware.Servo;

public class SkystoneAutoConfig extends AbstractRobotConfiguration {

    public ColorSensor colorSensor;
    public MecanumWheelDriveTrain driveTrain;

    public SkystoneJaJa jaja;
    public SkystoneFlexArm FlexArm;
    public SkystoneSlideAndBrickPicker Slider;
    public SkystoneIntake Intake;

    private HardwareMap hardwareMap;


    @Override
    public void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {
        this.hardwareMap = hardwareMap;
        setTelemetry(telemetryUtil);

        ProdMecanumRobotConfiguration mecanumConfig = ProdMecanumRobotConfiguration.newConfig(hardwareMap, telemetryUtil);
        driveTrain = new MecanumWheelDriveTrain(mecanumConfig.motorFL,mecanumConfig.motorFR,mecanumConfig.motorBL,mecanumConfig.motorBR);
<<<<<<< HEAD

=======
>>>>>>> d770b71bcaf7cbbb895b80ab59f2fdcc87e95f35
        jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));
        FlexArm = new SkystoneFlexArm(mecanumConfig.SliderArmLeft, mecanumConfig.SliderArmRight);
        Slider = new SkystoneSlideAndBrickPicker(hardwareMap.get(Servo.class, "brickPicker"),(hardwareMap.get(Servo.class, "linearSlide")));
        Intake = new SkystoneIntake(mecanumConfig.IntakeWheelLeft, mecanumConfig.IntakeWheelRight);
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
<<<<<<< HEAD

=======
        telemetryUtil.addData("Color Sensor", colorSensor+"");
>>>>>>> d770b71bcaf7cbbb895b80ab59f2fdcc87e95f35
        telemetryUtil.sendTelemetry();
    }
}
