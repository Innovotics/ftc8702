package org.ftc8702.opmodes.ultimategoal;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class UltimateGoalShooter {

    public DcMotor shooter;
    public Servo pusher;
    public Servo lifterRight;
    public Servo lifterLeft;

    public UltimateGoalShooter(DcMotor shooter,  Servo pusher, Servo lifterRight, Servo lifterLeft) {
        this.shooter = shooter;
        this.pusher =  pusher;
        this.lifterRight = lifterRight;
        this.lifterLeft = lifterLeft;
    }

    public void push()
    {
        pusher.setPosition(0.7);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pusher.setPosition(0.57);
    }
    public void pushOut()
    {
        pusher.setPosition(0.57);
    }
    public void pushIn()
    {
        pusher.setPosition(0);
    }
    public void pushIn2()
    {
        pusher.setPosition(1);
    }

    public void liftRight1(){
        lifterRight.setPosition(0);
    }

    public void liftRight2(){
        lifterRight.setPosition(0.7);
    }

    public void liftLeft1(){
        lifterLeft.setPosition(0);
    }

    public void liftLeft2(){
        lifterLeft.setPosition(0.7);
    }

    public void setLiftRight(double scale){
        lifterRight.setPosition(scale);
    }

    public void setLiftLeft(double scale){
        lifterLeft.setPosition(scale);
    }
}
