package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import ftcbootstrap.components.utils.TelemetryUtil;


@Disabled
public class AutoModeRedAngle extends AutoModeRed {

    @Override
    boolean park() throws InterruptedException {
        //Move robot out of the platform
        getRobot().motorFL.setPower(.5 * (-1));
        getRobot().motorFR.setPower(.5 * (-1));
        getRobot().motorBL.setPower(.5 * (1));
        getRobot().motorBR.setPower(.5 * (1));
        Thread.sleep(2000);

        //Stop the robot
        getRobot().motorFL.setPower(.5 * (0));
        getRobot().motorFR.setPower(.5 * (0));
        getRobot().motorBL.setPower(.5 * (0));
        getRobot().motorBR.setPower(.5 * (0));
        Thread.sleep(1000);

        //Move the robot forwards
        getRobot().motorFL.setPower(.25 * (-1));
        getRobot().motorFR.setPower(.25 * (1));
        getRobot().motorBL.setPower(.25 * (-1));
        getRobot().motorBR.setPower(.25 * (1));
        Thread.sleep(1000);

        //Stop the robot
        getRobot().motorFL.setPower(.5 * (0));
        getRobot().motorFR.setPower(.5 * (0));
        getRobot().motorBL.setPower(.5 * (0));
        getRobot().motorBR.setPower(.5 * (0));
        Thread.sleep(1000);

        return true;
    }

    @Override
    public TelemetryUtil getTelemetryUtil() {
        return null;
    }

    @Override
    void setGlyphPosition() {

    }
}
