package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

public class SkystoneSlideAndBrickPicker {

    // class variable, property, attribute
    public Servo brickPicker;
    public Servo linearSlide;

    double  armPosition = 0;
    //        double gripPosition, contPower;
    double  MIN_POSITION = 0, MAX_POSITION = 1;

    // constructor - special method there is no return type, 2)
    public SkystoneSlideAndBrickPicker(Servo brickPicker, Servo linearSlide) {
        this.linearSlide = linearSlide;
        this.brickPicker = brickPicker;
    }

    // methods
    void LinearSliderOut()
    {
        //linearSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        linearSlide.setPosition(0.2);
    }

    void LinearSliderIn()
    {
        //linearSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        linearSlide.setPosition(-0.2);
    }

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

    void linearSliderStop()
    {
        linearSlide.setPosition(0.5);
        //linearSlide.setDirection(Direction.REVERSE);
        //brickPicker.setDirection(Servo.Direction.REVERSE);
        //brickPicker.setPosition(0);
    }
}
