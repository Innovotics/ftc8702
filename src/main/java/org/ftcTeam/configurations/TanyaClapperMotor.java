package org.ftcTeam.configurations;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftcTeam.utils.RobotProperties;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

/**
 * Created by tanya_000 on 11/7/2017.
 */

public class TanyaClapperMotor extends RobotConfiguration{

    public DcMotor clapperMotor;


    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static TanyaClapperMotor newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        TanyaClapperMotor config = new TanyaClapperMotor();
        config.init(hardwareMap, telemetryUtil);
        return config;
    }

    /**
     * Assign your class instance variables to the saved device names in the hardware map
     *
     *
     * @param hardwareMap
     * @param telemetryUtil
     */
    @Override
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil)
    {
        setTelemetry(telemetryUtil);
        clapperMotor = (DcMotor) getHardwareOn(RobotProperties.CLAPPER_MOTOR, hardwareMap.dcMotor);
    }

}
