package ftcbootstrap.demos.onemc;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
public class OneMCBot extends RobotConfiguration {

    //motors
    public DcMotor leftDrive;
    public DcMotor rightDrive;


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static OneMCBot newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        OneMCBot config = new OneMCBot();
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

        //MC = Motor Controlller

        setTelemetry(telemetryUtil);

        //Motors
        leftDrive = (DcMotor) getHardwareOn("mc1_1_left_drive", hardwareMap.dcMotor);
        rightDrive = (DcMotor) getHardwareOn("mc1_2_right_drive", hardwareMap.dcMotor);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);




    }



}
