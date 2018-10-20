package org.ftcTeam.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.ftcTeam.utils.EncoderBasedAutonomousUtil;
import org.ftcTeam.utils.RobotAutonomousUtils;


@Autonomous(name = "BlueStraight", group = "Ops")
@Disabled
public class AutoModeBlueStraight extends AutoModeBlue {
    private boolean isStrafDone = false;

    @Override
    void setGlyphPosition() throws InterruptedException {
        //Move robot out of the platform
//        RobotAutonomousUtils.strafAdjustLeft(getRobot().motorFL, getRobot().motorFR,getRobot().motorBR, getRobot().motorBL );
      //  RobotAutonomousUtils.rotateMotor180(getRobot().motorFL, getRobot().motorFR,getRobot().motorBR, getRobot().motorBL );
    }

    boolean park() {
        return true;
    }


}
