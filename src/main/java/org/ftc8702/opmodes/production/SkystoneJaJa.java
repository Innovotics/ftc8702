package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import ftcbootstrap.ActiveOpMode;

public class SkystoneJaJa {
    public Servo foundationGrabberLeft;
    public Servo foundationGrabberRight;

    public SkystoneJaJa(Servo foundationGrabberLeft, Servo foundationGrabberRight) {
        this.foundationGrabberLeft = foundationGrabberLeft;
        this.foundationGrabberRight = foundationGrabberRight;
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

