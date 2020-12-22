package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class UltimateGoalShooter {

    public DcMotor shooter;
    public Servo pusher;

    public UltimateGoalShooter(DcMotor shooter,  Servo pusher) {
        this.shooter = shooter;
        this.pusher =  pusher;
    }

    public void push()
    {
        pusher.setPosition(0.5);
        pusher.setPosition(0);
    }
}
