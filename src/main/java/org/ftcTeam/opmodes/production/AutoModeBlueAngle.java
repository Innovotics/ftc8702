package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;


@Autonomous(name = "BlueAngle", group = "Ops")
public class AutoModeBlueAngle extends AutoModeBlue {

    protected ColorValue getPanelColor() {
        return ColorValue.BLUE;
    }

}
