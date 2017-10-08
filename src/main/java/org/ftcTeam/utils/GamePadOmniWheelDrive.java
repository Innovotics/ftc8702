package org.ftcTeam.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;
import org.ftcbootstrap.components.utils.MotorDirection;

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
    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param leftMotor DcMotor
     * @param rightMotor DcMotor
     */
    public GamePadOmniWheelDrive(ActiveOpMode opMode, Gamepad gamepad, DcMotor leftMotor, MotorDirection leftMotorDirection, DcMotor rightMotor, DcMotor backRightMotor, DcMotor backLeftMotor) {

        super(opMode);
        this.yleftMotorStick = new GamePadOmniWheelMotor(opMode, gamepad, leftMotor, GamePadOmniWheelMotor.Control.RIGHT_STICK_Y, true);
        this.yrightMotorStick = new GamePadOmniWheelMotor(opMode,gamepad, rightMotor,  GamePadOmniWheelMotor.Control.RIGHT_STICK_Y, true);
        this.yrightMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backRightMotor, GamePadOmniWheelMotor.Control.RIGHT_STICK_Y, false);
        this.yleftMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backLeftMotor, GamePadOmniWheelMotor.Control.RIGHT_STICK_Y, false);

        this.xleftMotorStick = new GamePadOmniWheelMotor(opMode,  gamepad, leftMotor, GamePadOmniWheelMotor.Control.RIGHT_STICK_X, true);
        this.xrightMotorStick = new GamePadOmniWheelMotor(opMode,gamepad, rightMotor,  GamePadOmniWheelMotor.Control.RIGHT_STICK_X, true);
        this.xrightMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backRightMotor, GamePadOmniWheelMotor.Control.RIGHT_STICK_X, false);
        this.xleftMotorStickBack = new GamePadOmniWheelMotor(opMode, gamepad, backLeftMotor, GamePadOmniWheelMotor.Control.RIGHT_STICK_X, false);
    }

    /**
     * Update motors with current gamepad state
     */
    public void update() {
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
    }

}
