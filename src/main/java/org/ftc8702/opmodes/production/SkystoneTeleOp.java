package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.configurations.production.ProdMecanumRobotConfiguration;

import ftcbootstrap.ActiveOpMode;


public class  SkystoneTeleOp extends ActiveOpMode {

    private ProdMecanumRobotConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;
    private SkystoneJaJa jaja;
    private SkystoneSlideAndBrickPicker slideAndBrickPicker;
    private SkystoneFlexArm flexArm;
    private SkystoneIntake Intake;

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
        jaja = new SkystoneJaJa(hardwareMap.get(Servo.class, "foundationGrabberL"), hardwareMap.get(Servo.class, "foundationGrabberR"));
        //flexArm = new SkystoneFlexArm(driveTrainConfig.SliderArmLeft, driveTrainConfig.SliderArmRight);
        //Intake = new SkystoneIntake(driveTrainConfig.IntakeWheelLeft, driveTrainConfig.IntakeWheelRight);
        slideAndBrickPicker = new SkystoneSlideAndBrickPicker(hardwareMap.get(Servo.class, "brickPicker"), hardwareMap.get(CRServo.class, "linearSlide"));
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

    private void gamepad1Control()
    {

        if (gamepad1.right_trigger !=0)
        {
            driveTrain.rotateRight(0.2f);
        }
        else if (gamepad1.left_trigger !=0)
        {
            driveTrain.rotateLeft(0.2f);
        }
        else if (gamepad1.right_bumper) {
            driveTrain.turnSmoothRightAutonomous();
        }
        else if (gamepad1.left_bumper) {
            driveTrain.turnSmoothLeftAutonomous();
        }
        else
        {
            //driveTrain.stop();
            smoothDrive();
        }

        getTelemetryUtil().sendTelemetry();
    }

    private void smoothDrive()
    {
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

    private void gamepad2Control()
    {
        //For visual purposes
        if (gamepad2.x ) {
            jaja.JaJaDown();
            getTelemetryUtil().addData("Button X", " Pressed");
        }
        else if (gamepad2.b ) {
            jaja.JaJaUp();
            getTelemetryUtil().addData("Button Y", " Pressed");
        }

        if (gamepad2.y)
        {
            flexArm.ArmUp(1);
        }
        else if (gamepad2.a)
        {
            flexArm.ArmDown(1);
        }
        //TESTING CODE FORM HERE DOWN TO THE NEXT COMMENT
        else if (gamepad2.left_trigger !=0)
        {
            flexArm.ArmDown(0.5f);
        }
        else if (gamepad2.right_trigger !=0)
        {
            flexArm.ArmUp(0.5f);
        }
        else
        {
            flexArm.stop();
        }

        //For Visual Purposes
        if (gamepad2.right_bumper)
        {
            Intake.Intake(1);
        }
        else if (gamepad2.left_bumper)
        {
            Intake.Output(1);
        }
        else if (gamepad2.left_stick_x > 0) {
            Intake.pushFoundation();
        }
        else if (gamepad2.left_stick_x < 0) {
            Intake.pullFoundation();
        }
        else
        {
            Intake.stop(0);
        }

        //For Visual Purposes
 /*       if (gamepad2.dpad_down && slideAndBrickPicker.armPosition > slideAndBrickPicker.MIN_POSITION)
        {
            slideAndBrickPicker.armPosition -= 0.00025;
            getTelemetryUtil().addData("dpad up slideOut: ", slideAndBrickPicker.armPosition);
            slideAndBrickPicker.slide(slideAndBrickPicker.armPosition);
        }
        else if (gamepad2.dpad_up && slideAndBrickPicker.armPosition < slideAndBrickPicker.MAX_POSITION)
        {
            slideAndBrickPicker.armPosition += 0.00025;
            getTelemetryUtil().addData("dpad down slideIn:", slideAndBrickPicker.armPosition);
            slideAndBrickPicker.slide(slideAndBrickPicker.armPosition);
            //slideAndBrickPicker.LinearSliderIn();//slideAndBrickPicker.armPosition);
        }
*/
        if (gamepad2.dpad_down)
        {
            slideAndBrickPicker.LinearSlideIn();
            getTelemetryUtil().addData("dpad down slideIn:", slideAndBrickPicker.armPosition);
        }
        else if (gamepad2.dpad_up) {
            slideAndBrickPicker.LinearSlideOut();
            getTelemetryUtil().addData("dpad up slideOut: ", slideAndBrickPicker.armPosition);
        }
        else {
            slideAndBrickPicker.LinearSlideStop();
        }

        //For Visual Purposes
        if (gamepad2.dpad_left) {
            getTelemetryUtil().addData("dpad left:", " pickerUp");
            slideAndBrickPicker.BrickPickerPickUp(1);
        }
        else if (gamepad2.dpad_right) {
            getTelemetryUtil().addData("dpad right:", " pickerDown");
            slideAndBrickPicker.BrickPickerRelease(1);
        }
        getTelemetryUtil().sendTelemetry();
    }


    /**
     * Taken from FTC SDK PushBot example
     * The DC motors are scaled to make it easier to control them at slower speeds
     * Obtain the current values of the joystick controllers.
     * Note that x and y equal -1 when the joystick is pushed all of the way
     * forward (i.e. away from the human holder's body).
     * The clip method guarantees the value never exceeds the range +-1.
     */
    private float scaleMotorPower(float p_power) {

        // Assume no scaling.
        float l_scale = 0.0f;

        // Ensure the values are legal.
        float l_power = Range.clip(p_power, -1, 1);

        float[] l_array =
                {0.00f, 0.05f, 0.09f, 0.10f, 0.12f
                        , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
                        , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
                        , 1.00f, 1.00f
                };

        int l_index = (int) (l_power * 16.0);
        if (l_index < 0) {
            l_index = -l_index;
        } else if (l_index > 16) {
            l_index = 16;
        }

        if (l_power < 0) {
            l_scale = -l_array[l_index];
        } else {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }
}
