package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftcTeam.utils.ColorValue;

/**
 * Created by dkim on 11/24/17.
 */

public class ParkingColorSensor {

    private ColorSensor colorSensor;

    public ParkingColorSensor(ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
    }

    public boolean parkingOn(ColorValue baseColor) {

        return true;
    }
}
