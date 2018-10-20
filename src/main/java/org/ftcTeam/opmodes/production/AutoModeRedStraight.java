package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.ftcTeam.utils.ColorValue;

@Autonomous(name = "RedStraight", group = "Ops")
@Disabled
public class AutoModeRedStraight extends AutoModeRed {
    @Override
    boolean park() {
    return true;
    }

    @Override
    void setGlyphPosition() {

    }
}
