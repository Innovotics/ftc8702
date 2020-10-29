package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;

import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "UltimateGoalTeleOp", group = "production")
public class UltimateGoalTeleOp extends ActiveOpMode {

    private UltimateGoalConfiguration UltimateGoalConfig;
    private MecanumWheelDriveTrain driveTrain;
    private UltimateGoalArm wobbleArm;

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
        driveTrain = new MecanumWheelDriveTrain(UltimateGoalConfig.motorFL, UltimateGoalConfig.motorFR, UltimateGoalConfig.motorBL, UltimateGoalConfig.motorBR, telemetry);
        wobbleArm = new UltimateGoalArm(UltimateGoalConfig.wobbleMotor, UltimateGoalConfig.clawLeft, UltimateGoalConfig.clawRight);
    }

    @Override
    protected void activeLoop() throws InterruptedException {
        gamepad1Control();
    }

    public void gamepad1Control() {
        if (gamepad1.a) {
            wobbleArm.WobbleDown();
        } else if (gamepad1.y) {
            wobbleArm.WobbleUp();
        } else if (gamepad1.x) {
            wobbleArm.OpenClaw();
        } else if (gamepad1.b) {
            wobbleArm.CloseClaw();
        } else {
            mecanumDrive();
            wobbleArm.wobbleMotor.setPower(0);
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

        //if (Math.abs(throttle) > threshold && Math.abs(direction) > threshold)
        //{
        driveTrain.frontRightMotor.setPower(FR);
        driveTrain.frontLeftMotor.setPower(FL);
        driveTrain.backRightMotor.setPower(BR);
        driveTrain.backLeftMotor.setPower(BL);

        telemetry.addData("Enocoders",  "Starting at, " +
                driveTrain.frontRightMotor.getCurrentPosition() +"," +
                driveTrain.frontLeftMotor.getCurrentPosition() + "," +
                driveTrain.backRightMotor.getCurrentPosition() + "\n");


        telemetry.addData("motor powers", "at \n"+ "front right"+ FR +"\n front left"+ FL +"\n back right"+ BR +"\n back left"+ BL);
        telemetry.update();
    }
}
