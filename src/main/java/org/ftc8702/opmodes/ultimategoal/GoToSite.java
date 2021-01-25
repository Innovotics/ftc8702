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
        driveTrain.goForwardPIDDistance(80);
        shooter.shooter.setPower(-1);
        driveTrain.rotateRightWithGyro((float)0.2, (float)-22);
        driveTrain.stop();
        SleepUtils.sleep(2000);
        shooter.push();
        driveTrain.rotateRightWithGyro((float)0.2, (float)-32);
        driveTrain.stop();
        shooter.shooter.setPower(-1);
        SleepUtils.sleep(2000);
        shooter.push();
        driveTrain.rotateRightWithGyro((float)0.2, (float)-35);
        driveTrain.stop();
        SleepUtils.sleep(2000);
        shooter.shooter.setPower(-1);
        shooter.push();
        shooter.shooter.setPower(0);
        driveTrain.rotateLeftWithGyro((float)0.2, (float)0);//repositions robot
        SleepUtils.sleep(2000);
    }

    public void shootRedSide()
    {
        wobbleArm.CloseClaw();
        driveTrain.goForwardPIDDistance(80);
        shooter.shooter.setPower(-1);
        driveTrain.rotateLeftWithGyro((float)0.2, (float)(6));
        driveTrain.stop();
        SleepUtils.sleep(2000);
        shooter.push();
        shooter.shooter.setPower(-1);
        driveTrain.rotateLeftWithGyro((float)0.2, (float)15);
        driveTrain.stop();
        SleepUtils.sleep(2000);
        shooter.push();
        driveTrain.rotateLeftWithGyro((float)0.2, (float)(20));
        driveTrain.stop();
        SleepUtils.sleep(2000);
        shooter.push();
        shooter.shooter.setPower(0);
        driveTrain.rotateRightWithGyro((float)0.2, (float)0);//repositions robot
        SleepUtils.sleep(2000);
    }

    public void shootRedPark() {
        wobbleArm.CloseClaw();
        driveTrain.stop();
        SleepUtils.sleep(15000);
        /*
        driveTrain.goForward(0.4f);
        SleepUtils.sleep(200);
        driveTrain.strafeRight(0.4f, 5, 2000, 100);
        driveTrain.stop();
        SleepUtils.sleep(200);
        shooter.shooter.setPower(-1);
        SleepUtils.sleep(1200);
        shooter.push();
        shooter.shooter.setPower(-1);
        SleepUtils.sleep(1200);
        shooter.push();
        shooter.shooter.setPower(-1);
        SleepUtils.sleep(1200);
        shooter.push();
        shooter.shooter.setPower(0);
        driveTrain.strafeLeft(0.5f);
        SleepUtils.sleep(1300);
         */
        driveTrain.goForwardPIDDistance(110);
        dropWobble();
        driveTrain.stop();
    }

    public void GoToASite()
    {
        driveTrain.goForwardPIDDistance(11);
    }

    public void GoToBSite()
    {
        driveTrain.goForwardPIDDistance(40);
        driveTrain.rotateRightWithGyro((float)0.4, -120);
    }

    public void GoToCSite()
    {
        driveTrain.goForwardPIDDistance(80);
    }

    public void GoToASiteRed()
    {
        driveTrain.goForwardPIDDistance(35);
        driveTrain.rotateLeftWithGyro(0.4f,135);
    }

    public void GoToBSiteRed()
    {
        //driveTrain.goForwardPIDDistance(103);
        driveTrain.goForwardPIDDistance(50);
    }

    public void GoToCSiteRed()
    {
        driveTrain.goForwardPIDDistance(84);
        driveTrain.rotateRightWithGyro(0.4f, -130);
        //driveTrain.goForwardPIDTime(0.5f, 1, 3000, 100);
    }

    public void dropWobble(){
        wobbleArm.wobbleDownSlow();
        SleepUtils.sleep(2000);
        wobbleArm.Stop();

        wobbleArm.OpenClaw();
        SleepUtils.sleep(1000);

        wobbleArm.WobbleUp();
        SleepUtils.sleep(800);
        wobbleArm.Stop();

        wobbleArm.CloseClaw();
    }
}
