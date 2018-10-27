package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.ftc8702.utils.ColorValue;

@Autonomous(name = "AutoModePrimary", group = "Ops")
@Disabled
public class AutoModePrimary extends AbstractAutoMode {
    @Override
    ColorValue getPanelColor() {
        return null;
    }

    @Override
    boolean park() throws InterruptedException {
        return false;
    }


}

