package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;

import javax.xml.transform.sax.TemplatesHandler;


@Autonomous(name = "BlueAngle", group = "Ops")
public class AutoModeBlueAngle extends AutoModeBlue {

    @Override
    boolean park() throws InterruptedException {
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

        return true;
    }
}
