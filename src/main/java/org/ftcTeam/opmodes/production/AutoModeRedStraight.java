package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;

@Autonomous(name = "RedStraight", group = "Ops")
public class AutoModeRedStraight extends AutoModeRed {
    @Override
    boolean park() {
    return true;
    }
}
