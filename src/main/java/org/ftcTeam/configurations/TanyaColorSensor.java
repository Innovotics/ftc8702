package org.ftcTeam.configurations;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.ftcTeam.utils.RobotProperties;
import org.ftcbootstrap.RobotConfiguration;
import org.ftcbootstrap.components.utils.TelemetryUtil;

/**
 * Created by tanya_000 on 10/22/2017.
 */

public class TanyaColorSensor extends RobotConfiguration{

   public ColorSensor mColorSensor;


    public static TanyaColorSensor newConfig(HardwareMap hardwareMap, TelemetryUtil telemetryUtil) {

        TanyaColorSensor config = new TanyaColorSensor();
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

          mColorSensor = (ColorSensor) getHardwareOn(RobotProperties.FLOOR_RIGHT, hardwareMap.colorSensor);
          getTelemetryUtil().addData("Color component: ", mColorSensor.toString());


        getTelemetryUtil().sendTelemetry();

    }
}
