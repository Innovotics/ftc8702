package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.ftcTeam.utils.ColorValue;


@Autonomous(name = "AutoModeSecondary", group = "Ops")
@Disabled
public class AutoModeSecondary extends AbstractAutoMode {
    @Override
    ColorValue getPanelColor() {
        return null;
    }

    @Override
    boolean park() throws InterruptedException {
        return false;
    }
}
