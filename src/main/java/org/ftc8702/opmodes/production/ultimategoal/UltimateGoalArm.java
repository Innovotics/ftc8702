package org.ftc8702.opmodes.production.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class UltimateGoalArm {

    public DcMotor WobbleMotor;
    public Servo ClawLeft;
    public Servo ClawRight;

    public UltimateGoalArm(DcMotor WobbleMotor, Servo ClawLeft, Servo ClawRight) {
        this.WobbleMotor = WobbleMotor;
        this.ClawLeft = ClawLeft;
        this.ClawRight = ClawRight;
    }

    public void WobbleUp(float scaledpower){
       WobbleMotor.setPower(-1);
    }

    public void WobbleDown(float scaledpower){
        WobbleMotor.setPower(1);
    }

    public void OpenClaw ()
    {
        ClawLeft.setDirection(Servo.Direction.FORWARD);
        ClawLeft.setPosition(0.5);
        ClawRight.setDirection(Servo.Direction.FORWARD);
        ClawRight.setPosition(0.5);

    }
    public void CloseClaw ()
    {
        ClawLeft.setDirection(Servo.Direction.REVERSE);
        ClawLeft.setPosition(0);
        ClawRight.setDirection(Servo.Direction.REVERSE);
        ClawRight.setPosition(0);
    }


}
