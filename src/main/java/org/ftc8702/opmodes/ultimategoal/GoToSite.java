package org.ftc8702.opmodes.ultimategoal;

import android.nfc.NdefRecord;

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

    public void shootBlueSide()
    {
        wobbleArm.CloseClaw();
        driveTrain.goForwardPIDDistance(9+9);
        shooter.shooter.setPower(1);
        driveTrain.rotateRightWithGyro((float)0.2, (float)-7.4);
        driveTrain.stop();
        SleepUtils.sleep(1000);
        shooter.push();
        driveTrain.rotateRightWithGyro((float)0.2, (float)-8.8);
        driveTrain.stop();
        SleepUtils.sleep(1000);
        shooter.push();
        driveTrain.rotateRightWithGyro((float)0.2, (float)-10.2);
        driveTrain.stop();
        SleepUtils.sleep(1000);
        shooter.push();
        shooter.shooter.setPower(0);
        driveTrain.rotateLeftWithGyro((float)0.2, (float)0);//repositions robot
        SleepUtils.sleep(5000);
    }

    public void shootRedSide()
    {
        wobbleArm.CloseClaw();
        driveTrain.goForwardPIDDistance(9+9);
        shooter.shooter.setPower(1);
        driveTrain.rotateLeftWithGyro((float)0.2, (float)7.4);
        driveTrain.stop();
        SleepUtils.sleep(1000);
        shooter.push();
        driveTrain.rotateLeftWithGyro((float)0.2, (float)8.8);
        driveTrain.stop();
        SleepUtils.sleep(1000);
        shooter.push();
        driveTrain.rotateLeftWithGyro((float)0.2, (float)10.2);
        driveTrain.stop();
        SleepUtils.sleep(1000);
        shooter.push();
        shooter.shooter.setPower(0);
        driveTrain.rotateRightWithGyro((float)0.2, (float)0);//repositions robot
        SleepUtils.sleep(5000);
    }

    public void GoToASite()
    {
        driveTrain.goForwardPIDDistance(74-9);
    }

    public void GoToBSite()
    {
        driveTrain.goForwardPIDDistance(80);
        driveTrain.rotateRightWithGyro((float)0.4, -105);
    }

    public void GoToCSite()
    {
        driveTrain.goForwardPIDDistance(120);
    }

    public void GoToASiteRed()
    {
        driveTrain.goForwardPIDDistance(89);
        driveTrain.rotateLeftWithGyro(0.4f,110);
    }

    public void GoToBSiteRed()
    {
        driveTrain.goForwardPIDDistance(89);
    }

    public void GoToCSiteRed()
    {
        driveTrain.goForwardPIDDistance(110);
        driveTrain.rotateRightWithGyro(0.4f, -110);
    }

    public void dropWobble(){
        wobbleArm.WobbleDown();
        SleepUtils.sleep(700);
        wobbleArm.Stop();

        wobbleArm.OpenClaw();
        SleepUtils.sleep(1000);

        wobbleArm.WobbleUp();
        SleepUtils.sleep(800);
        wobbleArm.Stop();

        wobbleArm.CloseClaw();
    }
}
