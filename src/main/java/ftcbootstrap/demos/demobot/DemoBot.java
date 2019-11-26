package ftcbootstrap.demos.demobot;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;

/**
 * DemoBot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class DemoBot extends RobotConfiguration {

    //sensors
    public OpticalDistanceSensor ods1;
    public TouchSensor touch1;
    public ColorSensor mrColor1;

    //motors
    public DcMotor motor1;
    public DcMotor motor2;
    public Servo servo1;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static DemoBot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        DemoBot config = new DemoBot();
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

        setTelemetry(telemetryUtil);

        ods1 = (OpticalDistanceSensor) getHardwareOn("ods1", hardwareMap.opticalDistanceSensor);
        touch1 = (TouchSensor) getHardwareOn("touch1", hardwareMap.touchSensor);
        mrColor1 = (ColorSensor) getHardwareOn("mrColor1", hardwareMap.colorSensor);
        // turn the LED on in the beginning, just so user will know that the sensor is active.
        mrColor1.enableLed(true);

        servo1 = (Servo) getHardwareOn("servo1", hardwareMap.servo);
        motor1 = (DcMotor) getHardwareOn("motor1", hardwareMap.dcMotor);
        motor2 = (DcMotor) getHardwareOn("motor2", hardwareMap.dcMotor);

        motor2.setDirection(DcMotor.Direction.REVERSE);


    }



}
