package org.ftcTeam.configurations.test;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftcTeam.utils.RobotProperties;
import ftcbootstrap.RobotConfiguration;
import ftcbootstrap.components.utils.TelemetryUtil;


/**
 * FTCTeamRobot Saved Configuration
 * <p/>
 * It is assumed that there is a configuration on the phone running the Robot Controller App with the same name as this class and
 * that  configuration is the one that is marked 'activated' on the phone.
 * It is also assumed that the device names in the 'init()' method below are the same  as the devices named for the
 * saved configuration on the phone.
 */
public class Team8702ParkingSensor extends RobotConfiguration {

    public ColorSensor parkingColorSensor;

    /**
     * Factory method for this class
     *
     * @param hardwareMap
     * @param telemetryUtil
     * @return
     */
    public static Team8702ParkingSensor newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        Team8702ParkingSensor config = new Team8702ParkingSensor();
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
    protected void init(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        setTelemetry(telemetryUtil);

         parkingColorSensor = (ColorSensor) getHardwareOn(RobotProperties.COLOR_PARKING, hardwareMap.colorSensor);
        getTelemetryUtil().addData("Color component: ", parkingColorSensor.toString());


        getTelemetryUtil().sendTelemetry();

    }


}
