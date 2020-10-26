package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

public class SkystoneSlideAndBrickPicker {

    // class variable, property, attribute
    public Servo brickPicker;
    public CRServo linearSlide;

    double  armPosition = 1;
    double  MIN_POSITION = 0.54, MAX_POSITION = 1;

    // constructor - special method there is no return type, 2)
    public SkystoneSlideAndBrickPicker(Servo brickPicker, CRServo linearSlide) {
        this.linearSlide = linearSlide;
        this.brickPicker = brickPicker;
    }

    // methods
    void slide(double position )
    {
        linearSlide.setPower(1);
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

    void LinearSlideOut() { linearSlide.setPower(1); }

    void LinearSlideIn()
    {
        linearSlide.setPower(-1);
    }

    void LinearSlideStop()
    {
        linearSlide.setPower(0);
    }
}
