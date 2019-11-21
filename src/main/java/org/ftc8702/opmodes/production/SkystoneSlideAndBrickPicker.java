package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

public class SkystoneSlideAndBrickPicker {

    // class variable, property, attribute
    public Servo brickPicker;
    public Servo linearSlide;

    double  armPosition = 1;
    double  MIN_POSITION = 0.54, MAX_POSITION = 1;

    // constructor - special method there is no return type, 2)
    public SkystoneSlideAndBrickPicker(Servo brickPicker, Servo linearSlide) {
        this.linearSlide = linearSlide;
        this.brickPicker = brickPicker;
    }

    // methods
    void slide(double position )
    {
        linearSlide.setPosition(position);
    }

    void BrickPickerPickUp(float power)
    {
        brickPicker.setDirection(Servo.Direction.FORWARD);
        brickPicker.setPosition(power);
    }

    void BrickPickerRelease(float power)
    {
        brickPicker.setDirection(Servo.Direction.REVERSE);
        brickPicker.setPosition(power);
    }
}
