package ftcbootstrap.demos.navbot;


import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

/**
 * PushBot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * <p/>
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class NavBot extends RobotConfiguration {

    //motors
    public DcMotor leftDrive;
    public DcMotor rightDrive;

    public DcMotor arm;
    public Servo leftClaw;
    public Servo rightClaw;

    public OpticalDistanceSensor odsBumper;
    public OpticalDistanceSensor odsGround;

    public GyroSensor gyro;

    public TouchSensor touchSensorForArm;
    public ColorSensor mrColor;


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static NavBot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        NavBot config = new NavBot();
        config.init(hardwareMap, telemetryUtil);
        return config;

    }

    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     * @param hardwareMap
     * @param telemetryUtil
     */
    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        //MC = Motor Controlller SC = Servo Cotroller
        //SM = Sensor Module :  ,a =analog d = digital, i = i2c , p = pwm

        setTelemetry(telemetryUtil);

        //Motors
        leftDrive = (DcMotor) getHardwareOn("mc1_1_left_drive", hardwareMap.dcMotor);
        rightDrive = (DcMotor) getHardwareOn("mc1_2_right_drive", hardwareMap.dcMotor);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        arm = (DcMotor) getHardwareOn("mc2_1_arm", hardwareMap.dcMotor);

        //servos
        rightClaw = (Servo) getHardwareOn("sc1_1_right_claw", hardwareMap.servo);
        leftClaw = (Servo) getHardwareOn("sc1_2_left_claw", hardwareMap.servo);

        //sensors
        odsBumper = (OpticalDistanceSensor) getHardwareOn("sm1_a7_ods", hardwareMap.opticalDistanceSensor);
        odsGround = (OpticalDistanceSensor) getHardwareOn("sm1_a6_ods", hardwareMap.opticalDistanceSensor);

        touchSensorForArm = (TouchSensor) getHardwareOn("sm1_d7_touch", hardwareMap.touchSensor);

        mrColor = (ColorSensor) getHardwareOn("sm1_i5_mr_color", hardwareMap.colorSensor);
        gyro = (GyroSensor) getHardwareOn("sm1_i4_mr_gyro", hardwareMap.gyroSensor);


    }

}

