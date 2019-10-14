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

    void JaJaDown(float power)
    {
        foundationGrabberLeft.setDirection(Servo.Direction.FORWARD);
        foundationGrabberRight.setDirection (Servo.Direction.FORWARD);
        foundationGrabberLeft.setPosition(power);
        foundationGrabberRight.setPosition (power);

}
    void JaJaUp (float power)
    {
        foundationGrabberLeft.setDirection(Servo.Direction.REVERSE);
        foundationGrabberRight.setDirection (Servo.Direction.REVERSE);
        foundationGrabberLeft.setPosition (-power);
        foundationGrabberRight.setPosition (-power);

    }
}

