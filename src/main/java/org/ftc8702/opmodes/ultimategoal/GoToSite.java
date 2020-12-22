package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;

import ftcbootstrap.ActiveOpMode;

public class GoToSite {

    private UltimateGoalConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;
    private UltimateGoalArm wobbleArm;
    private UltimateGoalShooter shooter;


    public void shootRedSide()
    {
        driveTrain.rotateLeftWithGyro((float)0.2, 15);
        shooter.push();
        shooter.shooter.setPower(1);
        driveTrain.sleep(.5);
        driveTrain.rotateLeftWithGyro((float)0.2, 15);
        shooter.push();
        driveTrain.sleep(.5);
        driveTrain.rotateLeftWithGyro((float)0.2, 15);
        shooter.push();
        driveTrain.sleep(.5);
        driveTrain.rotateRightWithGyro((float)0.2, 45);//repositions robot
    }

    public void GoToASite()
    {
        driveTrain.goForwardByInches(78, 0.5);
        wobbleArm.WobbleDown();
        driveTrain.sleep(1);
        wobbleArm.OpenClaw();
        driveTrain.sleep(1);
        wobbleArm.WobbleUp();
        driveTrain.sleep(1.5);
    }

    public void GoToBSite()
    {
        driveTrain.goForwardByInches(102, 0.5);
        driveTrain.rotateRightWithGyro((float)0.5, 90);
        wobbleArm.WobbleDown();
        driveTrain.sleep(1);
        wobbleArm.OpenClaw();
        driveTrain.sleep(1);
        wobbleArm.WobbleUp();
        driveTrain.sleep(1.5);
    }

    public void GoToCSite()
    {
        driveTrain.goForwardByInches(126, 0.5);
        wobbleArm.WobbleDown();
        driveTrain.sleep(1);
        wobbleArm.OpenClaw();
        driveTrain.sleep(1);
        wobbleArm.WobbleUp();
        driveTrain.sleep(1.5);
    }
}
