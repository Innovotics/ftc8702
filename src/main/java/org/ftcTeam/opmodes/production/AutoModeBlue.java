package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;


@Autonomous(name = "AutoModeBlue", group = "Ops")
public class AutoModeBlue extends AbstractAutoMode {

    @Override
    protected void onInit() {
    super.onInit();
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        super.activeLoop();
    }

    protected ColorValue getPanelColor() {
        return ColorValue.BLUE;
    }

}
