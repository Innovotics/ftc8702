package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;


@Autonomous(name = "BlueAngle", group = "Ops")
public class AutoModeBlueAngle extends AutoModeBlue {

    @Override
    void park() throws InterruptedException{
        //Move robot out of the platform
        robot.motorFL.setPower(.2 * (1));
        robot.motorFR.setPower(.2 * (1));
        robot.motorBL.setPower(.2 * (-1));
        robot.motorBL.setPower(.2 * (-1));
        Thread.sleep(1000);
    }
}
