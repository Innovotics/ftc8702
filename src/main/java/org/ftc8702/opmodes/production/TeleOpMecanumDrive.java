package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.ftc8702.configurations.production.ProdMecanumRobotConfiguration;
import org.ftc8702.opmodes.GamePadOmniWheelDrive;

import ftcbootstrap.ActiveOpMode;

@TeleOp(name = "TeleOpMecanumDrive", group = "production")
public class TeleOpMecanumDrive extends ActiveOpMode {

    private ProdMecanumRobotConfiguration robot;
    private MecanumWheelDriveTrain driveTrain;

    /**
     * Implement this method to define the code to run when the Init button is pressed on the Driver station.
     */
    @Override
    protected void onInit() {

        robot = ProdMecanumRobotConfiguration.newConfig(hardwareMap, getTelemetryUtil());

        //Note The Telemetry Utility is designed to let you organize all telemetry data before sending it to
        //the Driver station via the sendTelemetry command
        getTelemetryUtil().addData("Init", getClass().getSimpleName() + " initialized.");

    }

    @Override
    protected void onStart() throws InterruptedException {
        super.onStart();
        driveTrain = new MecanumWheelDriveTrain(robot.motorFL, robot.motorFR, robot.motorBL, robot.motorBR);
    }

    private Gamepad getGamePad() {
        return this.gamepad1;
    }

    /**
     * Implement this method to define the code to run when the Start button is pressed on the Driver station.
     * This method will be called on each hardware cycle just as the loop() method is called for event based Opmodes
     *
     * @throws
     */
    @Override
    protected void activeLoop() throws InterruptedException {
        if (getGamePad().left_bumper || getGamePad().right_bumper) {
            // rotate

            if (getGamePad().right_bumper){
                driveTrain.rotateRight(-1);
            }
            if (getGamePad().left_bumper){
                driveTrain.rotateRight(1);
            }

        } else if (getGamePad().left_stick_y != 0) {
            float scaledPower = scaleMotorPower(getGamePad().left_stick_y);
            driveTrain.goForward(scaledPower);
        } else if (getGamePad().left_stick_x != 0) {
            float scaledPower = scaleMotorPower(getGamePad().left_stick_x);
            driveTrain.strafeRight(scaledPower);
        } else {
            // stop
            driveTrain.stop();
        }

        getTelemetryUtil().addData("Joystick Power: ", gamepad2.right_stick_y);
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
