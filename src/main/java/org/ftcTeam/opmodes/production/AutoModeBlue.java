package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;


abstract class AutoModeBlue extends AbstractAutoMode {

    protected ColorValue getPanelColor() {
        return ColorValue.BLUE;
    }

}
