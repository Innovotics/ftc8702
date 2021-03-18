package org.ftc8702.opmodes.ultimategoal;

import android.nfc.NdefRecord;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.utils.SleepUtils;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.utils.TelemetryUtil;

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
        driveTrain.goForwardOdometers(15500, 0.4f);
        driveTrain.stop();
        SleepUtils.sleep(700);
        shooter.shooter.setPower(-0.95);
        driveTrain.rotateRightWithGyro((float)0.3, (float)-12);
        driveTrain.stop();
        SleepUtils.sleep(3000);
        shooter.push();
        //driveTrain.rotateRightWithGyro((float)0.3, (float)-27);
        driveTrain.stop();
        shooter.shooter.setPower(-0.95);
        SleepUtils.sleep(1700);
        shooter.push();
        //driveTrain.rotateRightWithGyro((float)0.3, (float)-31);
        driveTrain.stop();
        SleepUtils.sleep(1700);
        shooter.shooter.setPower(-0.95);
        shooter.push();
        shooter.shooter.setPower(0);
        driveTrain.rotateLeftWithGyro((float)0.3, (float)0);//repositions robot
        SleepUtils.sleep(2000);
    }

    public void shootRedSide()
    {
        wobbleArm.CloseClaw();
        driveTrain.goForwardOdometers(16500, 0.4f);
        //driveTrain.rotateRightWithGyro(0.3f, 0);
        //driveTrain.stop();
        //SleepUtils.sleep(500);
        shooter.shooter.setPower(-0.95);
        //driveTrain.rotateLeftWithGyro((float)0.2, (float)(15));
        driveTrain.stop();
        SleepUtils.sleep(2000);
        shooter.push();
        shooter.shooter.setPower(-0.9);
        //driveTrain.rotateLeftWithGyro((float)0.2, (float)20);
        driveTrain.stop();
        SleepUtils.sleep(1500);
        shooter.push();
        //driveTrain.rotateLeftWithGyro((float)0.2, (float)(24));
        driveTrain.stop();
        shooter.shooter.setPower(-0.95);
        SleepUtils.sleep(1500);
        shooter.push();
        shooter.shooter.setPower(0);
        //driveTrain.rotateRightWithGyro((float)0.2, (float)0);//repositions robot
        SleepUtils.sleep(700);
    }

    public void shootRedSideSecond(){
        driveTrain.stop();
        SleepUtils.sleep(500);
        shooter.shooter.setPower(-1);
        //driveTrain.rotateLeftWithGyro((float)0.2, (float)(15));
        driveTrain.stop();
        SleepUtils.sleep(1500);
        shooter.push();
        shooter.shooter.setPower(-1);
        //driveTrain.rotateLeftWithGyro((float)0.2, (float)20);
        driveTrain.stop();
        SleepUtils.sleep(500);
        shooter.push();
        //driveTrain.rotateLeftWithGyro((float)0.2, (float)(24));
        driveTrain.stop();
        SleepUtils.sleep(500);
        shooter.push();
        shooter.shooter.setPower(0);
        //driveTrain.rotateRightWithGyro((float)0.2, (float)0);//repositions robot
        SleepUtils.sleep(700);
    }

    public void shootRedPark() {
        wobbleArm.CloseClaw();
        driveTrain.goForwardOdometers(15500, 0.4f);
        driveTrain.stop();
        SleepUtils.sleep(1000);
        shooter.shooter.setPower(-0.85f);
        SleepUtils.sleep(1500);
        shooter.push();
        shooter.shooter.setPower(-0.85f);
        SleepUtils.sleep(1500);
        shooter.push();
        shooter.shooter.setPower(-0.85f);
        SleepUtils.sleep(2000);
        shooter.push();
        shooter.shooter.setPower(0);
        driveTrain.strafeLeft(1f);
        SleepUtils.sleep(500);
        //driveTrain.goForwardOdometers(18300, 0.4f);
        //driveTrain.stop();
    }

    public void GoToASite()
    {
        resetEncoders();
        driveTrain.goForwardOdometers(5000, 0.4f);
    }

    public void GoToBSite()
    {
        resetEncoders();
        driveTrain.goForwardOdometers(11000, 0.4f);
        driveTrain.rotateRightWithGyro((float)0.4, -160);
    }

    public void GoToCSite()
    {
        resetEncoders();
        driveTrain.goForwardOdometers(15000, 0.4f);
        driveTrain.rotateRightWithGyro(0.3f, -45);
    }

    public void GoToASiteRed()
    {
        resetEncoders();
        driveTrain.goForwardOdometers(4000, 0.4f);
        driveTrain.rotateRightWithGyro(0.4f,-160);
    }

    public void GoToASiteSecond(){
        driveTrain.rotateLeftWithGyro(0.3f, 0);
        SleepUtils.sleep(500);
        driveTrain.goForwardOdometers(4937, 0.4f);
        wobbleArm.OpenClaw();
        wobbleArm.wobbleMotor.setPower(-0.7);
        SleepUtils.sleep(700);
        driveTrain.strafeLeft(0.3f);
        SleepUtils.sleep(1000);
        wobbleArm.CloseClaw();
        wobbleArm.wobbleMotor.setPower(0.7);
        SleepUtils.sleep(1000);
    }

    public void GoToBSiteRed()
    {
        resetEncoders();
        driveTrain.goForwardOdometers(10229, 0.3f);
        driveTrain.strafeRight(0.4f);
        SleepUtils.sleep(700);
    }

    public void GoToCSiteRed()
    {
        resetEncoders();
        driveTrain.goForwardOdometers(16000, 0.3f);
        driveTrain.rotateRightWithGyro(0.4f, -130);

        //driveTrain.goForwardPIDTime(0.5f, 1, 3000, 100);
    }

    public void goToCSiteRedSecond(){
        resetEncoders();
        driveTrain.goBackwardOdometers(-13000, 0.5f);
        SleepUtils.sleep(200);
        resetEncoders();
        driveTrain.goBackwardOdometers(-5000, 0.5f);
        SleepUtils.sleep(200);
    }

    public void goToCSiteRedThird(){
        resetEncoders();
        driveTrain.goForwardOdometers(11075, 0.4f);
    }

    public void resetEncoders(){
        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void dropWobble(){
        wobbleArm.WobbleDown();
        SleepUtils.sleep(1000);
        wobbleArm.Stop();

        wobbleArm.OpenClaw();
        SleepUtils.sleep(100);

        wobbleArm.WobbleUp();
        SleepUtils.sleep(700);
        wobbleArm.Stop();

        wobbleArm.CloseClaw();
    }

    public void dropWobbleSlow(){
        wobbleArm.wobbleDownSlow();
        SleepUtils.sleep(2000);
        wobbleArm.Stop();

        wobbleArm.OpenClaw();
        SleepUtils.sleep(100);

        wobbleArm.WobbleUp();
        SleepUtils.sleep(700);
        wobbleArm.Stop();

        wobbleArm.CloseClaw();
    }
}
