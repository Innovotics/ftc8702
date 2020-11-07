package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class UltimateGoalShooter {

    public DcMotor leftShooter;
    public DcMotor rightShooter;

    public UltimateGoalShooter(DcMotor leftShooter, DcMotor rightShooter) {
        this.leftShooter = leftShooter;
        this.rightShooter = rightShooter;
    }

}
