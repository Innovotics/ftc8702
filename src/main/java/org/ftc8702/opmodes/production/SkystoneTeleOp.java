package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.components.motors.MecanumWheelDriveTrain;
import org.ftc8702.components.servo.GamePadCRServo;
import org.ftc8702.configurations.production.ProdMecanumRobotConfiguration;

import ftcbootstrap.ActiveOpMode;
import org.ftc8702.opmodes.production.SkystoneJaJa;

@TeleOp(name = "SkystoneTeleOp", group = "production")
public class SkystoneTeleOp extends ActiveOpMode {

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
        flexArm = new SkystoneFlexArm(driveTrainConfig.SliderArmLeft, driveTrainConfig.SliderArmRight);
        Intake = new SkystoneIntake(driveTrainConfig.IntakeWheelLeft, driveTrainConfig.IntakeWheelRight);
        slideAndBrickPicker = new SkystoneSlideAndBrickPicker(hardwareMap.get(Servo.class, "linearSlide"), hardwareMap.get(Servo.class, "brickPicker"));
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        if (gamepad1.right_bumper)
        {
            float power = 1;
            getTelemetryUtil().addData("Right bumper power:", power);
            driveTrain.rotateRight(power);
        }
        else if (gamepad1.left_bumper)
        {
            float power = 1;
            getTelemetryUtil().addData("Left bumper power:", power);
            driveTrain.rotateLeft(power);
        }
        else if (gamepad1.left_stick_y != 0)
        {
            float scaledPower = scaleMotorPower(-gamepad1.left_stick_y);//negative because when the joystick goes up it gives a negative value
            getTelemetryUtil().addData("Left Joystick Y: ",
                    "value=" + gamepad1.left_stick_y + ", scaledPower=" + scaledPower);
            driveTrain.goForward(scaledPower);
        }
        else if (gamepad1.left_stick_x != 0)
        {
            float scaledPower = scaleMotorPower(gamepad1.left_stick_x);
            getTelemetryUtil().addData("Left Joystick X: ",
                    "value=" + gamepad1.left_stick_x + ", scaledPower=" + scaledPower);
            driveTrain.strafeRight(scaledPower);
        }
        else
        {
            driveTrain.stop();
        }
//for visual purposes
        if (gamepad2.a)
        {
            flexArm.ArmUp(1);
        }
        else if (gamepad2.y)
        {
            flexArm.ArmDown(1);
        }
        else
        {
            flexArm.stop(0);
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
        else
        {
            Intake.stop(0);
        }
        //For visual purposes
        if (gamepad1.a ) {
            jaja.foundationGrabberLeft.setDirection(Servo.Direction.REVERSE);
            jaja.foundationGrabberRight.setDirection(Servo.Direction.REVERSE);
            jaja.foundationGrabberRight.setPosition(0.5);
            jaja.foundationGrabberLeft.setPosition(0.5);
            //jaja.foundationGrabberLeft.setPosition(0);
            //jaja.foundationGrabberRight.setPosition(0);

            //jaja.JaJaDown(-1);
        }
        else if (gamepad1.y ) {
            jaja.foundationGrabberRight.setDirection(Servo.Direction.FORWARD);
            jaja.foundationGrabberLeft.setDirection(Servo.Direction.FORWARD);
            jaja.foundationGrabberRight.setPosition(1);
            jaja.foundationGrabberLeft.setPosition(1);
        }
        else
        {
            jaja.JaJaStop(0);
        }
        //For Visual Purposes
        if (gamepad2.dpad_up){
            slideAndBrickPicker.LinearSliderOut(1);
        }
        else if (gamepad2.dpad_down) {
            slideAndBrickPicker.LinearSliderIn(1);
        }
        else if (gamepad2.dpad_left) {
            slideAndBrickPicker.BrickPickerPickUp(1);
        }
        else if (gamepad2.dpad_right) {
            slideAndBrickPicker.BrickPickerRelease(1);
        }
        else
        {
            slideAndBrickPicker.Stop(0);
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
