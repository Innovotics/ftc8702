package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.utils.SleepUtils;

import ftcbootstrap.ActiveOpMode;

public class GoToSite {

    private MecanumWheelDriveTrain driveTrain;
    private UltimateGoalArm wobbleArm;
    private UltimateGoalShooter shooter;

    public GoToSite(MecanumWheelDriveTrain driveTrain, UltimateGoalArm wobbleArm, UltimateGoalShooter shooter){
        this.driveTrain = driveTrain;
        this.wobbleArm = wobbleArm;
        this.shooter = shooter;
    }

    public void shootRedSide()
    {
        driveTrain.rotateLeftWithGyro((float)0.2, (float)7.4);
        driveTrain.stop();
        shooter.shooter.setPower(1);
        shooter.push();
        driveTrain.rotateLeftWithGyro((float)0.2, (float)7.4);
        driveTrain.stop();
        shooter.push();
        driveTrain.rotateLeftWithGyro((float)0.2, (float)7.4);
        driveTrain.stop();
        shooter.push();
        driveTrain.rotateRightWithGyro((float)0.2, (float)22.2);//repositions robot
    }

    public void GoToASite()
    {
        driveTrain.goForwardByInches(78, 0.5);
    }

    public void GoToBSite()
    {
        driveTrain.goForwardByInches(102, 0.5);
        driveTrain.rotateRightWithGyro((float)0.5, 90);
    }

    public void GoToCSite()
    {
        driveTrain.goForwardWithPIDInches((float)0.5, 1000, 126, 500);
    }

    public void dropWobble(){
        wobbleArm.WobbleDown();
        SleepUtils.sleep(700);
        wobbleArm.Stop();

        wobbleArm.OpenClaw();
        SleepUtils.sleep(1000);

        wobbleArm.WobbleUp();
        SleepUtils.sleep(600);
        wobbleArm.Stop();

        wobbleArm.CloseClaw();
    }
}
