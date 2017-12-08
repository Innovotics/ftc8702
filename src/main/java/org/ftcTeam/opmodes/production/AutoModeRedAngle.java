package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;


@Autonomous(name = "RedAngle", group = "Ops")
public class AutoModeRedAngle extends AutoModeRed{

    //Set color to Red
    protected ColorValue getPanelColor() {

        return ColorValue.RED;
    }

}
