package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class UltimateGoalArm {

    public DcMotor wobbleMotor;
    public Servo clawLeft;
    public Servo clawRight;

    public UltimateGoalArm(DcMotor wobbleMotor, Servo clawLeft, Servo clawRight) {
        this.wobbleMotor = wobbleMotor;
        this.clawLeft = clawLeft;
        this.clawRight = clawRight;
    }

    public void WobbleUp()
    {
        wobbleMotor.setPower(-0.5);
    }

    public void WobbleDown()
    {
        wobbleMotor.setPower(0.5);
    }

    public void OpenClaw ()
    {
        clawLeft.setDirection(Servo.Direction.FORWARD);
        clawLeft.setPosition(0.5);
        clawRight.setDirection(Servo.Direction.FORWARD);
        clawRight.setPosition(0.5);

    }
    public void CloseClaw ()
    {
        clawLeft.setDirection(Servo.Direction.REVERSE);
        clawLeft.setPosition(0);
        clawRight.setDirection(Servo.Direction.REVERSE);
        clawRight.setPosition(0);
    }


}
