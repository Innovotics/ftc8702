package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;

import ftcbootstrap.ActiveOpMode;

public class GoToSite {

    private UltimateGoalConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;
    private UltimateGoalArm wobbleArm;


    public void GoToASite()
    {
        driveTrain.goForwardByInches(78, 0.5);
        wobbleArm.WobbleDown();
        driveTrain.sleep(1000);
        wobbleArm.OpenClaw();
        driveTrain.sleep(1000);
        wobbleArm.WobbleUp();
        driveTrain.sleep(1500);
    }

    public void GoToBSite()
    {
        driveTrain.goForwardByInches(102, 0.5);
        //TODO Add rotation for dropping the wobble
        wobbleArm.WobbleDown();
        driveTrain.sleep(1000);
        wobbleArm.OpenClaw();
        driveTrain.sleep(1000);
        wobbleArm.WobbleUp();
        driveTrain.sleep(1500);
    }

    public void GoToCSite()
    {
        driveTrain.goForwardByInches(126, 0.5);
        wobbleArm.WobbleDown();
        driveTrain.sleep(1000);
        wobbleArm.OpenClaw();
        driveTrain.sleep(1000);
        wobbleArm.WobbleUp();
        driveTrain.sleep(1500);
    }
}
