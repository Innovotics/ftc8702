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
}
