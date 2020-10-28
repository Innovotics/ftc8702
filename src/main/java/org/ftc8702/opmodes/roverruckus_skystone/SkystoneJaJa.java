package org.ftc8702.opmodes.roverruckus_skystone;

import com.qualcomm.robotcore.hardware.Servo;

public class SkystoneJaJa {
    public Servo foundationGrabberLeft;
    public Servo foundationGrabberRight;
    public Servo skystoneGrabberLeft;
    public Servo skystoneGrabberRight;

    public SkystoneJaJa(Servo foundationGrabberLeft, Servo foundationGrabberRight) {
        this.foundationGrabberLeft = foundationGrabberLeft;
        this.foundationGrabberRight = foundationGrabberRight;
        this.skystoneGrabberLeft = skystoneGrabberLeft;
        this.skystoneGrabberRight = skystoneGrabberRight;
    }

    void JaJaLeftDown()
    {
        foundationGrabberLeft.setDirection(Servo.Direction.REVERSE);
        foundationGrabberLeft.setPosition(-0.5);
    }

    void JaJaRightDown()
    {
        foundationGrabberRight.setDirection (Servo.Direction.FORWARD);
        foundationGrabberRight.setPosition (-0.5);
    }

    void GrabSkystoneLeft(){
        skystoneGrabberLeft.setDirection(Servo.Direction.REVERSE);
        skystoneGrabberLeft.setPosition(0);
    }

    void releaseSkystoneLeft(){
        skystoneGrabberLeft.setDirection(Servo.Direction.FORWARD);
        skystoneGrabberLeft.setPosition(1);
    }
    void GrabSkystoneRight(){
        skystoneGrabberRight.setDirection(Servo.Direction.REVERSE);
        skystoneGrabberRight.setPosition(0);
    }
    void ReleaseSkystoneRight(){
        skystoneGrabberRight.setDirection(Servo.Direction.FORWARD);
        skystoneGrabberRight.setPosition(0);
    }
    void JaJaDown()
    {
        JaJaLeftDown();
        JaJaRightDown();
        /*
        foundationGrabberLeft.setDirection(Servo.Direction.REVERSE);
        foundationGrabberRight.setDirection (Servo.Direction.FORWARD);
        foundationGrabberLeft.setPosition(-0.5);
        foundationGrabberRight.setPosition (-0.5);
        */
    }

    void JaJaUp()
    {
        foundationGrabberLeft.setDirection(Servo.Direction.FORWARD);
        foundationGrabberRight.setDirection (Servo.Direction.REVERSE);
        foundationGrabberLeft.setPosition (0);
        foundationGrabberRight.setPosition (0);
    }

}

