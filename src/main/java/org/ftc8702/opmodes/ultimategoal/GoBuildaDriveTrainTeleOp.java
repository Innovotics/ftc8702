package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.configurations.production.OdometerRobotConfiguration;

import ftcbootstrap.ActiveOpMode;


@TeleOp(name = "GoBuildaDriveTrain", group = "production")
public class GoBuildaDriveTrainTeleOp extends ActiveOpMode {

    private UltimateGoalConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    public void onInit() {

        driveTrainConfig = UltimateGoalConfiguration.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    public void onStart() throws InterruptedException {
        super.onStart();
        driveTrain = new MecanumWheelDriveTrain(driveTrainConfig.motorFL, driveTrainConfig.motorFR, driveTrainConfig.motorBL, driveTrainConfig.motorBR, telemetry, driveTrainConfig.imu);

        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveTrain.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driveTrain.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Enocoders",  "Starting at " + driveTrain.frontRightMotor.getCurrentPosition() + "," +
                driveTrain.frontLeftMotor.getCurrentPosition() + "," +
                driveTrain.backRightMotor.getCurrentPosition());
        telemetry.update();


    }


    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws
     */
    @Override
    public void activeLoop() throws InterruptedException {
        gamepad1Control();
    }

    //public double threshold = 0.157;

    public void gamepad1Control()
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
