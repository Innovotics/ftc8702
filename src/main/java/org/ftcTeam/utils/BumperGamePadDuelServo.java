package org.ftcTeam.utils;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.ftcbootstrap.ActiveOpMode;
import org.ftcbootstrap.components.OpModeComponent;
import org.ftcbootstrap.components.ServoComponent;


/**
 * Operation to assist with operating a servo from a gamepad button
 */
public class BumperGamePadDuelServo extends OpModeComponent {

    // use the Y and A buttons for up and down and the  X and B buttons for left and right

    public  static enum Control {
        Y_A,
        X_B,
        LB_RB
    }

    private Control currentControl;

    // amount to change the servo position by
    private double servoDelta = 0.05;
    private ServoComponent servoComponent1;
    private ServoComponent servoComponent2;
    private Gamepad gamepad;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param gamepad Gamepad
     * @param servo1 Servo
     * @param servo2 Servo
     * @param control  use the Y and A buttons for up and down and the  X and B buttons for left and right
     */
    public BumperGamePadDuelServo(ActiveOpMode opMode, Gamepad gamepad, Servo servo1, Servo servo2, Control control, double initialPosition) {

        this(opMode,gamepad, servo1, servo2,control,initialPosition,false);

    }

    /**
     * Constructor for operation
     * @param opMode
     * @param gamepad Gamepad
     * @param servo1 Servo
     * @param servo2 Servo
     * @param control  use the Y and A buttons for up and down and the  X and B buttons for left and right
     * @param initialPosition must set the initial position of the servo before working with it
     * @param reverseOrientation  true if the servo is install in the reverse orientation
     */
    public BumperGamePadDuelServo(ActiveOpMode opMode, Gamepad gamepad, Servo servo1, Servo servo2, Control control, double initialPosition, boolean reverseOrientation) {

        super(opMode);
        //this.servoComponent1 = new ServoComponent(opMode,  servo1,   initialPosition , reverseOrientation);
        //this.servoComponent1 = new ServoComponent(opMode,  servo1,   initialPosition , reverseOrientation);
        this.servoComponent1 = new ServoComponent(opMode,  servo1 , 0.0);
        this.servoComponent2 = new ServoComponent(opMode,  servo2 , 0.0);
        this.gamepad = gamepad;
        this.currentControl = control;

    }

    /**
     * Update motors with latest gamepad state
     */
    public void update() {

        // update the position of the servo
        if (gamepad.right_bumper) {
            servoComponent1.incrementServoTargetPosition(servoDelta);
            servoComponent2.incrementServoTargetPosition(-servoDelta);
        }

        if (gamepad.left_bumper) {
            servoComponent1.incrementServoTargetPosition(-servoDelta);
            servoComponent2.incrementServoTargetPosition(servoDelta);
        }

    }


}
