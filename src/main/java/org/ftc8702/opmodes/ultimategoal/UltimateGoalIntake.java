package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class UltimateGoalIntake {

    public DcMotor intakeLeft;
    public DcMotor intakeRight;

    public UltimateGoalIntake(DcMotor intakeLeft, DcMotor intakeRight) {
        this.intakeRight = intakeRight;
        this.intakeLeft = intakeLeft;
    }

    public void intake()
    {
        intakeRight.setPower(1);
        intakeLeft.setPower(1);
    }

    public void output()
    {
        intakeLeft.setPower(-1);
        intakeRight.setPower(-1);
    }

    public void stop()
    {
        intakeRight.setPower(0);
        intakeLeft.setPower(0);
    }
}
