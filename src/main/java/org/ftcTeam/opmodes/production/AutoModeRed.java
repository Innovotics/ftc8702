package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.ftcTeam.utils.ColorValue;



@Disabled
abstract class AutoModeRed extends AbstractAutoMode{

    //Set color to Red
    protected ColorValue getPanelColor() {

        return ColorValue.RED;
    }

}
