package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.ftcTeam.utils.ColorValue;
import org.ftcTeam.utils.EncoderBasedAutonomousUtil;
import org.ftcTeam.utils.RobotAutonomousUtils;


@Autonomous(name = "BlueStraight", group = "Ops")
public class AutoModeBlueStraight extends AutoModeBlue{
    @Override


    boolean park() {
        return true;

    }

    @Override
     void setGlyphPosition()  throws InterruptedException{
        //slide 1 second
        //Move robot out of the platform
        getRobot().motorFL.setPower(.5 * (1));
        getRobot().motorFR.setPower(.5 * (1));
        getRobot().motorBL.setPower(.5 * (-1));
        getRobot().motorBR.setPower(.5 * (-1));
        Thread.sleep(2100);

        //Stop the robot
        getRobot().motorFL.setPower(.5 * (0));
        getRobot().motorFR.setPower(.5 * (0));
        getRobot().motorBL.setPower(.5 * (0));
        getRobot().motorBR.setPower(.5 * (0));
        Thread.sleep(1000);

        //180 robot
        EncoderBasedAutonomousUtil.rotateMotor180(getRobot().motorToEncoderFR, getRobot().motorToEncoderFL, getRobot().motorToEncoderBR, getRobot().motorToEncoderBL);
    }
}
