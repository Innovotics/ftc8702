package org.ftcTeam.utils;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.operations.motors.GamePadMotor;

/**
 * Operation to assist with basic tank drive using a gamepad
 */
public class GamePadFourWheelDrive extends OpModeComponent {

    private GamePadMotor leftMotorStick;
    private GamePadMotor rightMotorStick;
    private GamePadMotor rightMotorStick1;
    private GamePadMotor leftMotorStick1;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param leftMotor DcMotor
     * @param rightMotor DcMotor
     */
    public GamePadFourWheelDrive(ActiveOpMode opMode, Gamepad gamepad, DcMotor leftMotor, DcMotor rightMotor, DcMotor backRightMotor, DcMotor backLeftMotor) {

        super(opMode);
        this.leftMotorStick = new GamePadMotor(opMode,  gamepad, leftMotor, GamePadMotor.Control.LEFT_STICK_Y);
        this.rightMotorStick = new GamePadMotor(opMode,gamepad, rightMotor,  GamePadMotor.Control.RIGHT_STICK_Y);
        this.rightMotorStick1 = new GamePadMotor(opMode, gamepad, backRightMotor, GamePadMotor.Control.LEFT_STICK_X);
        this.leftMotorStick1 = new GamePadMotor(opMode, gamepad, backLeftMotor, GamePadMotor.Control.RIGHT_STICK_X);
    }

    /**
     * Update motors with current gamepad state
     */
    public void update() {
        leftMotorStick.update();
        rightMotorStick.update();
        leftMotorStick1.update();
        rightMotorStick1.update();
    }


    public void startRunMode(DcMotor.RunMode runMode)  throws InterruptedException {
        leftMotorStick.startRunMode( runMode);
        rightMotorStick.startRunMode( runMode);
        leftMotorStick1.startRunMode( runMode);
        rightMotorStick1.startRunMode( runMode);
    }

}
