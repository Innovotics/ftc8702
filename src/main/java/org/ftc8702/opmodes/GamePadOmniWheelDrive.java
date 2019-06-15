package org.ftc8702.opmodes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.ftc8702.components.motors.GamePadOmniWheelMotor;
import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.OpModeComponent;

/**
 * Operation to assist with basic tank drive using a gamepad
 */
public class GamePadOmniWheelDrive extends OpModeComponent {

    private GamePadOmniWheelMotor yleftMotorStick;
    private GamePadOmniWheelMotor yrightMotorStick;
    private GamePadOmniWheelMotor yrightMotorStickBack;
    private GamePadOmniWheelMotor yleftMotorStickBack;

    private GamePadOmniWheelMotor xleftMotorStick;
    private GamePadOmniWheelMotor xrightMotorStick;
    private GamePadOmniWheelMotor xrightMotorStickBack;
    private GamePadOmniWheelMotor xleftMotorStickBack;

    private GamePadOmniWheelMotor spin_leftMotorStick;
    private GamePadOmniWheelMotor spin_rightMotorStick;
    private GamePadOmniWheelMotor spin_leftMotorStickBack;
    private GamePadOmniWheelMotor spin_rightMotorStickBack;

    Gamepad gamePad;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param leftMotor DcMotor
     * @param rightMotor DcMotor
     */
    public GamePadOmniWheelDrive(ActiveOpMode opMode, Gamepad gamepad, DcMotor leftMotor, DcMotor rightMotor, DcMotor backRightMotor, DcMotor backLeftMotor) {

        super(opMode);
        this.gamePad = gamepad;

        yleftMotorStick = new GamePadOmniWheelMotor(opMode, gamepad, leftMotor, GamePadOmniWheelMotor.Control.LEFT_STICK_Y, false);
        yrightMotorStick = new GamePadOmniWheelMotor(opMode,gamepad, rightMotor,  GamePadOmniWheelMotor.Control.LEFT_STICK_Y, true);
        yrightMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backRightMotor, GamePadOmniWheelMotor.Control.LEFT_STICK_Y, false);
        yleftMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backLeftMotor, GamePadOmniWheelMotor.Control.LEFT_STICK_Y, true);

        xleftMotorStick = new GamePadOmniWheelMotor(opMode,  gamepad, leftMotor, GamePadOmniWheelMotor.Control.LEFT_STICK_X, false);
        xrightMotorStick = new GamePadOmniWheelMotor(opMode,gamepad, rightMotor,  GamePadOmniWheelMotor.Control.LEFT_STICK_X, true);
        xrightMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backRightMotor, GamePadOmniWheelMotor.Control.LEFT_STICK_X, true);
        xleftMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backLeftMotor, GamePadOmniWheelMotor.Control.LEFT_STICK_X, false);

        spin_leftMotorStick = new GamePadOmniWheelMotor(opMode, gamepad, leftMotor, GamePadOmniWheelMotor.Control.LB_RB_BUTTONS, true);
        spin_rightMotorStick = new GamePadOmniWheelMotor(opMode, gamepad, rightMotor, GamePadOmniWheelMotor.Control.LB_RB_BUTTONS, true);
        spin_leftMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backLeftMotor, GamePadOmniWheelMotor.Control.LB_RB_BUTTONS,true);
        spin_rightMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backRightMotor, GamePadOmniWheelMotor.Control.LB_RB_BUTTONS, true);

    }

    /**
     * Update motors with current gamepad state
     */
    public void update() {

            spin_rightMotorStickBack.update();
            spin_rightMotorStick.update();
            spin_leftMotorStickBack.update();
            spin_leftMotorStick.update();

            yleftMotorStick.update();
            yrightMotorStick.update();
            yleftMotorStickBack.update();
            yrightMotorStickBack.update();

            xleftMotorStick.update();
            xrightMotorStick.update();
            xleftMotorStickBack.update();
            xrightMotorStickBack.update();

    }


    public void startRunMode(DcMotor.RunMode runMode)  throws InterruptedException {
        yleftMotorStick.startRunMode( runMode);
        yrightMotorStick.startRunMode( runMode);
        yleftMotorStickBack.startRunMode( runMode);
        yrightMotorStickBack.startRunMode( runMode);

        xleftMotorStick.startRunMode( runMode);
        xrightMotorStick.startRunMode( runMode);
        xleftMotorStickBack.startRunMode( runMode);
        xrightMotorStickBack.startRunMode( runMode);

        spin_leftMotorStickBack.startRunMode( runMode);
        spin_leftMotorStick.startRunMode( runMode);
        spin_rightMotorStick.startRunMode( runMode);
        spin_rightMotorStickBack.startRunMode( runMode);

    }

}
