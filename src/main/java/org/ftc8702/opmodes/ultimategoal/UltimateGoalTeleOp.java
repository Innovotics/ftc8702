package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.utils.SleepUtils;

import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "UltimateGoalTeleOp", group = "production")
public class UltimateGoalTeleOp extends ActiveOpMode {

    private UltimateGoalConfiguration UltimateGoalConfig;
    private MecanumWheelDriveTrain driveTrain;
    private UltimateGoalArm wobbleArm;
    private UltimateGoalIntake intake;
    private UltimateGoalShooter shooter;
    private long lastPressed = 0;
    private int pressed = 1;

    @Override
    protected void onInit() {

        UltimateGoalConfig = UltimateGoalConfiguration.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");
    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        driveTrain = new MecanumWheelDriveTrain(UltimateGoalConfig.motorFL, UltimateGoalConfig.motorFR, UltimateGoalConfig.motorBL, UltimateGoalConfig.motorBR, telemetry, UltimateGoalConfig.imu);
        wobbleArm = new UltimateGoalArm(UltimateGoalConfig.wobbleMotor, UltimateGoalConfig.claw);
        intake = new UltimateGoalIntake(UltimateGoalConfig.intakeLeft, UltimateGoalConfig.intakeRight);
        shooter = new UltimateGoalShooter(UltimateGoalConfig.shooter, UltimateGoalConfig.pusher, UltimateGoalConfig.lifterRight, UltimateGoalConfig.lifterLeft);
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        gamepad1Control();
        gamePad2Control();
    }

    public void gamepad1Control() {
            mecanumDrive();
    }

    public void gamePad2Control(){
        if (gamepad2.dpad_down) {
            wobbleArm.WobbleDown();
        } else if (gamepad2.dpad_up) {
            wobbleArm.WobbleUp();
        } else if (gamepad2.a) {
            shooter.liftRight2();
            shooter.liftLeft1();
        } else if (gamepad2.y) {
            shooter.liftLeft2();
            shooter.liftRight1();
        } else if (gamepad2.right_bumper){
            intake.intake();
        } else if (gamepad2.left_bumper){
            intake.output();
        }else if (gamepad2.dpad_left && System.currentTimeMillis() - lastPressed > 1000){
            lastPressed = System.currentTimeMillis();
            shooter.push();
        }else if (gamepad2.dpad_right){
            shooter.pushOut();
        } else if (gamepad2.x){
            wobbleArm.OpenClaw();
        } else if(gamepad2.b){
            shooter.shooter.setPower(-1);
            SleepUtils.sleep(1500);
            shooter.pushOut();
            shooter.shooter.setPower(-1);
            SleepUtils.sleep(1000);
            shooter.pushOut();
            shooter.shooter.setPower(-1);
            SleepUtils.sleep(1000);
            shooter.pushOut();
        }
        else{
            wobbleArm.Stop();
            intake.stop();
            wobbleArm.CloseClaw();
            shoot();
        }
    }

    public void mecanumDrive()
    {
        float throttle = -gamepad1.right_stick_x;
        float direction = -gamepad1.left_stick_y;
        float strafe = gamepad1.left_stick_x;

        float FR = throttle + direction - strafe; //float FR = throttle + direction - strafe; Previous
        float FL = throttle - direction - strafe;
        float BR = throttle + direction + strafe; //float BR = throttle + direction + strafe; Previous
        float BL = throttle - direction + strafe; //float BL = throttle - direction + strafe; Previous

        FR = Range.clip(FR, -1, 1);
        FL = Range.clip(FL, -1, 1);
        BR = Range.clip(BR, -1, 1);
        BL = Range.clip(BL, -1, 1);

        if(gamepad1.right_bumper){
            driveTrain.rotateRight(0.2f);
        }else if(gamepad1.left_bumper){
            driveTrain.rotateLeft(0.2f);
        } else{
            driveTrain.frontRightMotor.setPower(FR);
            driveTrain.frontLeftMotor.setPower(FL);
            driveTrain.backRightMotor.setPower(BR);
            driveTrain.backLeftMotor.setPower(BL);
        }

        telemetry.addData("Enocoders",  "Starting at, " +
                driveTrain.frontRightMotor.getCurrentPosition() +"," +
                driveTrain.frontLeftMotor.getCurrentPosition() + "," +
                driveTrain.backRightMotor.getCurrentPosition() + "\n");

        telemetry.update();
    }

    public void shoot(){
        float direction = gamepad2.right_stick_y;

        float RSHOOTER = -direction;

        RSHOOTER = Range.clip(RSHOOTER, -1, 1);

        shooter.shooter.setPower(-RSHOOTER);
    }
}
