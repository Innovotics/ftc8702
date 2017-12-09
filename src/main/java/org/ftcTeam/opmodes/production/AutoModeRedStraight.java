package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;

@Autonomous(name = "RedStraight", group = "Ops")
public class AutoModeRedStraight extends AutoModeRed {

    protected ColorValue getPanelColor() {
        return ColorValue.RED;
    }

}
