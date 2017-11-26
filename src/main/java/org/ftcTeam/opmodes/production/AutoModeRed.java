package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;


@Autonomous(name = "AutoModeRed", group = "Ops")
public class AutoModeRed extends AbstractAutoMode{

    //Set color to Red
    protected ColorValue getPanelColor() {

        return ColorValue.RED;
    }

}
