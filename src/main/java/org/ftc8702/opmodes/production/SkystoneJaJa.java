package org.ftc8702.opmodes.production;

import com.qualcomm.robotcore.hardware.CRServo;

import ftcbootstrap.ActiveOpMode;

public class SkystoneJaJa {
    public CRServo foundationGrabberLeft;
    public CRServo foundationGrabberRight;

    public SkystoneJaJa(CRServo foundationGrabberLeft, CRServo foundationGrabberRight) {
        this.foundationGrabberLeft = foundationGrabberLeft;
        this.foundationGrabberRight = foundationGrabberRight;
    }

    void JaJaDown(float power)
    {
    foundationGrabberLeft.setPower(power);
    foundationGrabberRight.setPower (power);
}
    void JaJaUp (float power)
    {
        foundationGrabberLeft.setPower (-power);
        foundationGrabberRight.setPower (power);
    }
}

