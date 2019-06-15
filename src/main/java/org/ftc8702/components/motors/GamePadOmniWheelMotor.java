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
public class GamePadOmniWheelMotor extends OpModeComponent {

    public enum Control {
        LEFT_STICK_X,
        LEFT_STICK_Y,
        RIGHT_STICK_X,
        RIGHT_STICK_Y,
        UP_DOWN_BUTTONS,
        LEFT_RIGHT_BUTTONS,
        LB_RB_BUTTONS,
        Y_BUTTON,
        A_BUTTON,
        X_BUTTON,
        B_BUTTON,
        LEFT_BUMPER,
        RIGHT_BUMPER
    }

    private Control control;
    private DcMotor motor;
    private Gamepad gamepad;
    private static final float defaultButtonPower = 1.0f;
    private float buttonPower;
    private boolean isReverse;

    /**
     * Constructor for operation.  Telemetry enabled by default.
     *
     * @param opMode
     * @param gamepad Gamepad
     * @param motor   DcMotor to operate on
     * @param control {@link GamePadOmniWheelMotor.Control}
     */
    public GamePadOmniWheelMotor(ActiveOpMode opMode, Gamepad gamepad, DcMotor motor, Control control, boolean reverse) {
        this(opMode, gamepad, motor, control, defaultButtonPower);
        isReverse = reverse;
    }

    /**
     * Constructor for operation.  Telemetry enabled by default.
     *
     * @param opMode
     * @param gamepad     Gamepad
     * @param motor       DcMotor to operate on
     * @param control     {@link GamePadOmniWheelMotor.Control}
     * @param buttonPower power to apply when using gamepad buttons
     */
    public GamePadOmniWheelMotor(ActiveOpMode opMode, Gamepad gamepad, DcMotor motor, Control control, float buttonPower) {
        super(opMode);
        this.gamepad = gamepad;
        this.motor = motor;
        this.control = control;
        this.buttonPower = buttonPower;
    }


    /**
     * Update motors with latest gamepad state
     */
    public void update() {

        float power = 0;

        //note that if y equal -1 then joystick is pushed all of the way forward.
        switch (control) {
            case LEFT_STICK_Y:
                power = scaleMotorPower(-gamepad.left_stick_y);
                break;
            case LEFT_STICK_X:
                power = scaleMotorPower(gamepad.left_stick_x);
                break;
            case RIGHT_STICK_Y:
                power = scaleMotorPower(gamepad.right_stick_y);
                break;
            case RIGHT_STICK_X:
                power = scaleMotorPower(gamepad.right_stick_x);
                break;
            default:
                power = motorPowerFromButtons();
                break;
        }


        addTelemetry("setting power: " + control.toString(), power);

        if (isReverse) {
            power = power * (-1);
        }
        motor.setPower(power);
    }

    public void startRunMode(DcMotor.RunMode runMode) throws InterruptedException {
        motor.setMode(runMode);
        getOpMode().waitOneFullHardwareCycle();
    }

    private float motorPowerFromButtons() {
        float powerToReturn = 0f;
        boolean lb = gamepad.left_bumper;
        boolean rb = gamepad.right_bumper;

        if (((control == GamePadOmniWheelMotor.Control.UP_DOWN_BUTTONS)) ||
                ((control == GamePadOmniWheelMotor.Control.LEFT_RIGHT_BUTTONS))) {
            powerToReturn = -buttonPower;
        }
        if ((control == GamePadOmniWheelMotor.Control.LB_RB_BUTTONS) && lb) {
            powerToReturn = -buttonPower;
        } else {
            switch (control) {

                case LB_RB_BUTTONS:
                    if (rb) powerToReturn = buttonPower;
                    break;
                case LEFT_BUMPER:
                    if (lb) powerToReturn = buttonPower;
                    break;
                case RIGHT_BUMPER:
                    if (rb) powerToReturn = buttonPower;
                    break;
                default:
                    powerToReturn = 0f;
                    break;
            }
        }

        return powerToReturn;
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


