package org.ftc8702.components.motors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.OpModeComponent;


/**
 * Operation to assist with Gamepad actions on DCMotors
 * <p>
 * This class is modification of GamePadMotor
 */
public class DuelWheelMotors extends OpModeComponent {

    public enum Control {
        LEFT_STICK_X,
        LEFT_STICK_Y,
    }

    public DcMotor motorR;
    public DcMotor motorL;

    private Control control;
    private Gamepad gamepad;
    private static final float defaultButtonPower = 1.0f;

    private boolean isReverse;

    public DuelWheelMotors(ActiveOpMode opMode, Gamepad gamepad, DcMotor r, DcMotor l, Control control, boolean reverse) {
        super(opMode);
        this.motorR = r;
        this.motorL = l;
        this.gamepad = gamepad;
        this.control = control;;
    }

    /**
     * Update motors with latest gamepad state
     */
    public void update() {

        float power = 0;
        addTelemetry("setting power: " + control.toString(), power);

        if (isReverse) {
            power = power * (-1);
        }
    }

    public void startRunMode(DcMotor.RunMode runMode) throws InterruptedException {
        //motor.setMode(runMode);
        getOpMode().sleep(100);
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


    private void shift(float power) {
        motorR.setPower(power);
        motorL.setPower(power);
    }


}


