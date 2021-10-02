package org.ftc8702.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.opmodes.ultimategoal.UltimateGoalConfiguration;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.OpModeComponent;

@TeleOp(name = "MecanumDriving", group = "production")
public class GamePadMecanumWheelDrive extends ActiveOpMode {

    private MecanumConfig MecanumConfig;
    private MecanumWheelDriveTrain driveTrain;

    @Override
    protected void onInit() {
        MecanumConfig = MecanumConfig.newConfig(hardwareMap, getTelemetryUtil());
        driveTrain = new MecanumWheelDriveTrain(MecanumConfig.motorFL, MecanumConfig.motorFR, MecanumConfig.motorBL, MecanumConfig.motorBR, telemetry, MecanumConfig.imu);
        telemetry.addData("Mecanum drive", "ready ;-;");
        telemetry.update();
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        mecanumDrive();
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
            driveTrain.frontLeftMotor.setPower(-0.2);
            driveTrain.frontRightMotor.setPower(-0.2);
            driveTrain.backLeftMotor.setPower(-0.2);
            driveTrain.backRightMotor.setPower(-0.2);
        }else if(gamepad1.left_bumper){
            driveTrain.frontLeftMotor.setPower(0.2);
            driveTrain.frontRightMotor.setPower(0.2);
            driveTrain.backLeftMotor.setPower(0.2);
            driveTrain.backRightMotor.setPower(0.2);
        } else{
            driveTrain.frontLeftMotor.setPower(FL);
            driveTrain.frontRightMotor.setPower(FR);
            driveTrain.backLeftMotor.setPower(BL);
            driveTrain.backRightMotor.setPower(BR);
        }
    }
}


