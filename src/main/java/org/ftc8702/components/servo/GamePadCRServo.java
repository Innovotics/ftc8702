package org.ftc8702.components.servo;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import ftcbootstrap.ActiveOpMode;
import ftcbootstrap.components.OpModeComponent;
import ftcbootstrap.components.ServoComponent;


/**
 * Operation to assist with operating a servo from a gamepad button
 */
public class GamePadCRServo extends OpModeComponent {

    // use the Y and A buttons for up and down and the  X and B buttons for left and right

    public  static enum Control {
        Y_A,
        X_B
    }

    private Control currentControl;

    // amount to change the servo position by
    private double servoDelta = 1.0;
    private CRServo servo;
    private Gamepad gamepad;

    /**
     * Constructor for operation
     * Telemetry enabled by default.
     * @param opMode
     * @param gamepad Gamepad
     * @param servo Servo
     * @param control  use the Y and A buttons for up and down and the  X and B buttons for left and right
     */
    public GamePadCRServo(ActiveOpMode opMode, Gamepad gamepad, CRServo servo, Control control, double initialPosition) {

        this(opMode,gamepad, servo,control,initialPosition,false);

    }

    /**
     * Constructor for operation
     * @param opMode
     * @param gamepad Gamepad
     * @param servo Servo
     * @param control  use the Y and A buttons for up and down and the  X and B buttons for left and right
     * @param initialPosition must set the initial position of the servo before working with it
     * @param reverseOrientation  true if the servo is install in the reverse orientation
     */
    public GamePadCRServo(ActiveOpMode opMode, Gamepad gamepad, CRServo servo, Control control, double initialPosition, boolean reverseOrientation) {

        super(opMode);
         this.servo = servo;
        this.gamepad = gamepad;
        this.currentControl = control;

    }

    /**
     * Update motors with latest gamepad state
     */
    public void update() {

        boolean rightVal =  currentControl == Control.Y_A  ?  gamepad.y : gamepad.b;
        boolean leftVal =   currentControl == Control.Y_A  ?  gamepad.a : gamepad.x;

        addTelemetry("rightVal", rightVal); ;
        addTelemetry("leftVal", leftVal);


        // update the position of the servo

        while (gamepad.x) {
            servo.setPower(-1.0);
        }

        while (gamepad.b) {
            servo.setPower(1.0);
        }

        servo.setPower(0.0);
//        if(!gamepad.y) {
//            servo.setPower(0.0);
//
//        } else if(gamepad.y) {
//            servo.setPower(1.0);
//        }
//
//        if(!gamepad.x) {
//            servo.setPower(0.0);
//        } else if(gamepad.x) {
//            servo.setPower(-1.0);
//        }


//        if (rightVal) {
//            servo.setPower(servoDelta);
//        }
//
//        if (leftVal) {
//            servo.setPower(-servoDelta);
//        }

    }


}
