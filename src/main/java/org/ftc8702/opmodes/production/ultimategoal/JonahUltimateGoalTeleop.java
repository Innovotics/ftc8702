package org.ftc8702.opmodes.production.ultimategoal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.configurations.production.ProdMecanumRobotConfiguration;

import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "UltimateGoalTeleOp", group = "production")
public class JonahUltimateGoalTeleop extends ActiveOpMode {
    private UltimateGoalArm Claw;
    private ProdMecanumRobotConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        driveTrainConfig = ProdMecanumRobotConfiguration.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        driveTrain = new MecanumWheelDriveTrain(driveTrainConfig.motorFL, driveTrainConfig.motorFR, driveTrainConfig.motorBL, driveTrainConfig.motorBR);
        //jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));
        //flexArm = new SkystoneFlexArm(driveTrainConfig.SliderArmLeft, driveTrainConfig.SliderArmRight);
        //Intake = new SkystoneIntake(driveTrainConfig.IntakeWheelLeft, driveTrainConfig.IntakeWheelRight);
        //slideAndBrickPicker = new SkystoneSlideAndBrickPicker(hardwareMap.get(Servo.class, "brickPicker"), hardwareMap.get(CRServo.class, "linearSlide"));
        //slideAndBrickPicker.armPosition = 0.01;//slideAndBrickPicker.MAX_POSITION;
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        gamepad2Control();
        gamepad1Control();
    }

    private void gamepad1Control() {

        if (gamepad1.right_trigger != 0) {
            driveTrain.rotateRight(0.2f);
        } else if (gamepad1.left_trigger != 0) {
            driveTrain.rotateLeft(0.2f);
        } else if (gamepad1.right_bumper) {
            driveTrain.turnSmoothRightAutonomous();
        } else if (gamepad1.left_bumper) {
            driveTrain.turnSmoothLeftAutonomous();
        } else {
            //driveTrain.stop();
            smoothDrive();
        }

        getTelemetryUtil().sendTelemetry();
    }

    private void smoothDrive() {
        float throttle = -gamepad1.right_stick_x;
        //float throttle = 0;
       /* if (gamepad1.right_stick_x > 0)
        {
            throttle = gamepad1.right_stick_x + 0.2f;
        }
        else if (gamepad1.right_stick_x < 0)
        {
            throttle = gamepad1.right_stick_x - 0.2f;
        }
*/
        float direction = -gamepad1.left_stick_y;
        float strafe = gamepad1.left_stick_x;

        float FR = throttle + direction + strafe;
        float FL = throttle - direction + strafe;
        float BR = throttle + direction - strafe;
        float BL = throttle - direction - strafe;

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
        // }
        //else{
        //  driveTrain.stop();
        //}
    }

    private void gamepad2Control() {
        //For visual purposes
        if (gamepad2.x) {
            Claw.CloseClaw();
            getTelemetryUtil().addData("Button X", " Pressed");
        } else if (gamepad2.b) {
            Claw.OpenClaw();
            getTelemetryUtil().addData("Button Y", " Pressed");
        } else {
            Claw.CloseClaw();
        }
    }
}
