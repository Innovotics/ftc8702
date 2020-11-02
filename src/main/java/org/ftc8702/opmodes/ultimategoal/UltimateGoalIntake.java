package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class UltimateGoalIntake {

    public DcMotor intake;

    public UltimateGoalIntake(DcMotor intake) {
        this.intake = intake;
    }

    public void intake()
    {
        intake.setPower(-1);
    }

    public void output()
    {
        intake.setPower(1);
    }

}
