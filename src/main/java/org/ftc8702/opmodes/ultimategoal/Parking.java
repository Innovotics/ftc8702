package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;

import ftcbootstrap.ActiveOpMode;

public class Parking{

    private UltimateGoalConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;

    public void Park(ColorSensor colorSensor)
    {
        driveTrain.goBackwardWithColor((float) 0.3, colorSensor);
    }
}
