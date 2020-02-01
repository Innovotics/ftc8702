package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.configurations.production.ProdMecanumRobotConfiguration;

import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "SkystoneTeleOpSolo", group = "production")
public class SkystoneTeleOpSolo extends ActiveOpMode {

    private ProdMecanumRobotConfiguration driveTrainConfig;
    private MecanumWheelDriveTrain driveTrain;
    private SkystoneJaJa jaja;
    private SkystoneSlideAndBrickPicker slideAndBrickPicker;
    private SkystoneFlexArm flexArm;
    private SkystoneIntake Intake;
    private double threshold = 0.157;
    private float motorPower = 1;


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
        flexArm = new SkystoneFlexArm(driveTrainConfig.SliderArmLeft, driveTrainConfig.SliderArmRight);
        Intake = new SkystoneIntake(driveTrainConfig.IntakeWheelLeft, driveTrainConfig.IntakeWheelRight);
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
        gamepad1Control();
    }

    private void gamepad1Control()
    {
        if (gamepad1.right_bumper)
        {
            float power = 1.0f;
            getTelemetryUtil().addData("Right bumper power:", power);
            driveTrain.rotateRight(power);
        }
        else if (gamepad1.left_bumper)
        {
            float power = 1.0f;
            getTelemetryUtil().addData("Left bumper power:", power);
            driveTrain.rotateLeft(power);
        }
        else if (gamepad1.right_trigger !=0)
        {
            driveTrain.rotateRight(0.2f);
        }
        else if (gamepad1.left_trigger !=0)
        {
            driveTrain.rotateLeft(0.2f);
        }
        else{
            driveTrain.stop();
        }
        //Testing new driving gotten from TJ
        if(Math.abs(gamepad1.left_stick_y) > threshold || Math.abs(gamepad1.left_stick_x) > threshold || Math.abs(gamepad1.right_stick_x) > threshold)
        {
            driveTrain.frontRightMotor.setPower(motorPower * (((-gamepad1.left_stick_y) + (gamepad1.left_stick_x)) + -gamepad1.right_stick_x));
            driveTrain.backLeftMotor.setPower(motorPower * (((-gamepad1.left_stick_y) + (-gamepad1.left_stick_x)) + gamepad1.right_stick_x));
            driveTrain.frontLeftMotor.setPower(motorPower * (((-gamepad1.left_stick_y) + (gamepad1.left_stick_x)) + gamepad1.right_stick_x));
            driveTrain.backRightMotor.setPower(motorPower * (((-gamepad1.left_stick_y) + (-gamepad1.left_stick_x)) + -gamepad1.right_stick_x));
        }
        else
        {
            driveTrain.stop();
        }

        if (gamepad1.x ) {
            jaja.JaJaDown();
            getTelemetryUtil().addData("Button X", " Pressed");
        }
        else if (gamepad1.b ) {
            jaja.JaJaUp();
            getTelemetryUtil().addData("Button b", " Pressed");
        }

        if (gamepad1.y)
        {
            flexArm.ArmUp(1);
        }
        else if (gamepad1.a)
        {
            flexArm.ArmDown(1);
        }
        else
        {
            flexArm.stop();
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
        if (gamepad1.dpad_down)
        {
            slideAndBrickPicker.LinearSlideIn();
            getTelemetryUtil().addData("dpad down slideIn:", slideAndBrickPicker.armPosition);
        }
        else if (gamepad1.dpad_up) {
            slideAndBrickPicker.LinearSlideOut();
            getTelemetryUtil().addData("dpad up slideOut: ", slideAndBrickPicker.armPosition);
        }
        else {
            slideAndBrickPicker.LinearSlideStop();
        }

        //For Visual Purposes
        if (gamepad1.dpad_left) {
            getTelemetryUtil().addData("dpad left:", " pickerUp");
            slideAndBrickPicker.BrickPickerPickUp(1);
        }
        else if (gamepad1.dpad_right) {
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
