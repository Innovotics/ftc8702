package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.EncoderBasedAutonomousUtil;


@Autonomous(name = "BlueStraight", group = "Ops")
public class AutoModeBlueStraight extends AutoModeBlue {
    private boolean isStrafDone = false;

    @Override
    void setGlyphPosition() throws InterruptedException {
        //Move robot out of the platform
        getRobot().motorFL.setPower(.5 * (1));
        getRobot().motorFR.setPower(.5 * (1));
        getRobot().motorBL.setPower(.5 * (-1));
        getRobot().motorBR.setPower(.5 * (-1));
        Thread.sleep(2000);

        //Stop the robot
        getRobot().motorFL.setPower(.5 * (0));
        getRobot().motorFR.setPower(.5 * (0));
        getRobot().motorBL.setPower(.5 * (0));
        getRobot().motorBR.setPower(.5 * (0));
        Thread.sleep(1000);

        //Rotate 180
        getRobot().motorFL.setPower(.5 * (1));
        getRobot().motorFR.setPower(.5 * (1));
        getRobot().motorBL.setPower(.5 * (1));
        getRobot().motorBR.setPower(.5 * (1));
        Thread.sleep(1750);

        //Stop the Robot
        getRobot().motorFL.setPower(.5 * (0));
        getRobot().motorFR.setPower(.5 * (0));
        getRobot().motorBL.setPower(.5 * (0));
        getRobot().motorBR.setPower(.5 * (0));
        Thread.sleep(1000);

    }


    boolean park() {
        return true;
    }


}
