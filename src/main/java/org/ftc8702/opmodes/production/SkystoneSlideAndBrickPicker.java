package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.Servo;

public class SkystoneSlideAndBrickPicker {

    // class variable, property, attribute
    public Servo brickPicker;
    public Servo linearSlide;

    // constructor - special method there is no return type, 2)
    public SkystoneSlideAndBrickPicker(Servo brickPicker, Servo linearSlide) {
        this.linearSlide = linearSlide;
        this.brickPicker = brickPicker;
    }

    // methods
    void LinearSliderOut(float power)
    {
        linearSlide.setDirection(Servo.Direction.FORWARD);
        linearSlide.setPosition(power);
    }

    void LinearSliderIn(float power)
    {
        linearSlide.setDirection(Servo.Direction.REVERSE);
        linearSlide.setPosition(power);
    }

    void BrickPickerPickUp(float power)
    {
        linearSlide.setDirection(Servo.Direction.FORWARD);
        linearSlide.setPosition(power);
    }

    void BrickPickerRelease(float power)
    {
        linearSlide.setDirection(Servo.Direction.REVERSE);
        linearSlide.setPosition(power);
    }
    void Stop(float power)
    {
        linearSlide.setDirection(Servo.Direction.REVERSE);
        brickPicker.setDirection(Servo.Direction.REVERSE);
        linearSlide.setPosition(0);
        brickPicker.setPosition(-power);
    }
}
