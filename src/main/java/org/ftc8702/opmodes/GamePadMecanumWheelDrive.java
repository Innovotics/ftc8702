package org.ftc8702.opmodes;

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
public class GamePadMecanumWheelDrive extends OpModeComponent {

    public enum Control {
        LEFT_STICK_X,
        LEFT_STICK_Y,
        LEFT_BUMPER,
        RIGHT_BUMPER
    }

    public DcMotor motorFR;
    public DcMotor motorFL;
    public DcMotor motorBR;
    public DcMotor motorBL;

    private Control control;
    private Gamepad gamepad;
    private Gamepad leftStickY;
    private static final float defaultButtonPower = 1.0f;
    private float buttonPower;
    private boolean isReverse;

    public GamePadMecanumWheelDrive(ActiveOpMode opMode, Gamepad gamepad, DcMotor fr, DcMotor fl, DcMotor br, DcMotor bl, Control control, boolean reverse) {
        super(opMode);
        this.motorFR = fr;
        this.motorFL = fl;
        this.motorBR = br;
        this.motorBL = bl;
        this.gamepad = gamepad;
        this.control = control;
        this.buttonPower = defaultButtonPower;
    }


    /**
     * Update motors with latest gamepad state
     */
//    public void update() {

//        float power = 0;
//        if (gamepad.right_bumper || gamepad.left_bumper) {
//            shift(defaultButtonPower);
//        } else {
//            power = leftStickY.scaleMotorPower(-gamepad.left_stick_y);
//        }


//        addTelemetry("setting power: " + control.toString(), power);
//
//        if (isReverse) {
//            power = power * (-1);
//        }
//    }

//    public void startRunMode(DcMotor.RunMode runMode) throws InterruptedException {
//        //motor.setMode(runMode);
//        getOpMode().waitOneFullHardwareCycle();
//    }
//



    /**
     * Taken from FTC SDK PushBot example
     * The DC motors are scaled to make it easier to control them at slower speeds
     * Obtain the current values of the joystick controllers.
     * Note that x and y equal -1 when the joystick is pushed all of the way
     * forward (i.e. away from the human holder's body).
     * The clip method guarantees the value never exceeds the range +-1.
     */

    private void shift(float power){
            motorFR.setPower(power);
            motorFL.setPower(power * -1);
            motorBR.setPower(power);
            motorBL.setPower(power * -1);
    }
}


