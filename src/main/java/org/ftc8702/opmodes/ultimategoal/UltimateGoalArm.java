package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class UltimateGoalArm {

    public DcMotor wobbleMotor;
    public Servo claw;

    public UltimateGoalArm(DcMotor wobbleMotor, Servo claw) {
        this.wobbleMotor = wobbleMotor;
        this.claw = claw;
    }

    public void WobbleUp()
    {
        wobbleMotor.setPower(0.5);
    }

    public void WobbleDown()
    {
        wobbleMotor.setPower(-0.5);
    }

    public void wobbleDownSlow(){
        wobbleMotor.setPower(-0.3);
    }

    public void Stop()
    {
        wobbleMotor.setPower(0);
    }

    public void OpenClaw ()
    {
        claw.setDirection(Servo.Direction.FORWARD);
        claw.setPosition(0.5);

    }
    public void CloseClaw ()
    {
        claw.setDirection(Servo.Direction.REVERSE);
        claw.setPosition(0);
    }


}
